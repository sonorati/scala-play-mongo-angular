package controllers

import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.Future
import reactivemongo.api.Cursor
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import org.slf4j.{LoggerFactory, Logger}
import javax.inject.Singleton
import play.api.mvc._
import play.api.libs.json._

@Singleton
class PlayersController extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[PlayersController])

  def collection: JSONCollection = db.collection[JSONCollection]("players")
  import models._
  import models.JsonFormats._

  def createPlayer = Action.async(parse.json) {
    request =>
      request.body.validate[Player].map {
        player =>
          collection.insert(player).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"Player Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findPlayers = Action.async {
    val cursor: Cursor[Player] = collection.
      find(Json.obj("active" -> true)).
      sort(Json.obj("created" -> -1)).
      cursor[Player]

    val futurePlayersList: Future[List[Player]] = cursor.collect[List]()

    val futurePersonsJsonArray: Future[JsArray] = futurePlayersList.map { players =>
      Json.arr(players)
    }
    futurePersonsJsonArray.map {
      players =>
        Ok(players(0))
    }
  }

}
