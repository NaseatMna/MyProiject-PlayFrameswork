package models

import play.api.libs.json.{ JsObject, OWrites }
import play.api.libs.json._

case class City(name: String, population: Int)

object City {
  implicit val cityFormat = Json.format[City]

  /* //TODO Implicit Write Object
  implicit object CityWrites extends OWrites[City] {
    def writes(city: City): JsObject = Json.obj(
      "name" -> city.name,
      "population" -> city.population
    )
  }
  //TODO Implicit Read Object
  implicit object CityReads extends Reads[City] {
    def reads(json: JsValue): JsResult[City] = json match {
      case obj: JsObject => try {
        val name = (obj \ "name").as[String]
        val population = (obj \ "population").as[Int]
        JsSuccess(City(name, population))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.JsObject")
    }
  }*/
}
