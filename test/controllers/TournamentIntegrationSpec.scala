package controllers

import org.joda.time.DateTime

import scala.concurrent._
import duration._
import org.specs2.mutable._

import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._
import java.util.concurrent.TimeUnit

import scala.concurrent.Await

class TournamentIntegrationSpec extends Specification{

    val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

    "Tournament" should {

        "insert a valid json" in {
            running(FakeApplication()) {
                val request = FakeRequest.apply(POST, "/tournament").withJsonBody(Json.obj(
                    "name" -> "Super Squash",
                    "start" -> DateTime.now(),
                    "active" -> true))
                val response = route(request)
                response.isDefined mustEqual true
                val result = Await.result(response.get, timeout)
                result.header.status must equalTo(CREATED)
            }
        }
    }
}
