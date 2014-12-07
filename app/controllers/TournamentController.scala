package controllers

import javax.inject.Singleton

import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor

import scala.concurrent.Future

@Singleton
class TournamentController extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[TournamentController])

  def collection: JSONCollection = db.collection[JSONCollection]("tournaments")
  import models.TournamentJsonFormats._
  import models._

  def createTournament = Action.async(parse.json) {
    request =>
      logger.info(s"Body of the request: $request.body")
      request.body.validate[Tournament].map {
        tournament =>
          collection.insert(tournament).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"Tournament Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findTournaments = Action.async {
    val cursor: Cursor[Tournament] = collection.
      find(Json.obj("active" -> true)).
      sort(Json.obj("created" -> -1)).
      cursor[Tournament]

    val futureTournamentsList: Future[List[Tournament]] = cursor.collect[List]()

    val futureTournamentsJsonArray: Future[JsArray] = futureTournamentsList.map { tournaments =>
      Json.arr(tournaments)
    }
      futureTournamentsJsonArray.map {
      tournaments =>
        Ok(tournaments(0))
    }
  }

}
