
package models
import play.api.libs.json._
import play.api.libs.json.Reads._
/**
 * Created by Naseat on 10/14/2016.
 */
case class ServiceCategory(
  service_category_name: String,
  service_category_description: String,
  service_category_image: String
)

object ServiceCategory {
  implicit val formatServiceCategory = Json.format[ServiceCategory]
}

/* writes object*/
/*
  implicit object ServiceCategoryWrites extends OWrites[ServiceCategory] {
    def writes(category: ServiceCategory): JsObject = Json.obj(
      "service_category_name" -> category.service_category_name,
      "service_category_description" -> category.service_category_description,
      "service_category_image" -> category.service_category_image
    )
  }

  /*  reads object  */
  implicit object ServiceCategoryReads extends Reads[ServiceCategory] {
    def reads(json: JsValue): JsResult[ServiceCategory] = json match {
      case obj: JsObject => try {
        val name = (obj \ "service_category_name").as[String]
        val des = (obj \ "service_category_description").as[String]
        val img = (obj \ "service_category_image").as[String]
        JsSuccess(ServiceCategory(name, des, img))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }
}*/

