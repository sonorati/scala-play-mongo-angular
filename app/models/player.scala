package models

case class Player( age: Int,
                 firstName: String,
                 lastName: String,
                 nicName: String,
                 active: Boolean)

object JsonFormats {
  import play.api.libs.json.Json

  // Generates Writes and Reads for Feed and Player thanks to Json Macros
  implicit val playerFormat = Json.format[Player]
}
