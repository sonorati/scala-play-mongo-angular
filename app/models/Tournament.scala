package models

import org.joda.time.DateTime

case class Tournament(name: String,
                      start: DateTime,
                      active: Boolean)

object TournamentJsonFormats {
    import play.api.libs.json.Json

    // Generates Writes and Reads for Feed and Player thanks to Json Macros
    implicit val tournamentFormat = Json.format[Tournament]
}
