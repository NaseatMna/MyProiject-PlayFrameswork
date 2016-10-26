package api

import play.api.libs.json._
import play.libs.Json._

/**
 * Created by Acer on 10/26/2016.
 */
case class WrappJson(
  data: List[JsObject],
  pagination: Pagination
)
object WrappJson {
  implicit object jsonWrites extends OWrites[WrappJson] {
    def writes(wrapJson: WrappJson): JsObject = Json.obj(
      "DATA" -> wrapJson.data,
      "PAGINATION" -> wrapJson.pagination,
      "MESSAGE" -> "OK"
    )
  }
}
