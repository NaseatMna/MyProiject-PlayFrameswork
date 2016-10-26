
package models.repositories

//import javafx.scene.control.Pagination

import com.google.inject.ImplementedBy
import models.ServiceCategory
import api.FilterData
import models.repositories.impl.ServicesRepo
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import scala.concurrent.Future
import api.Pagination
/**
 * Created by Naseat on 10/14/2016.
 */
@ImplementedBy(classOf[ServicesRepo])
trait IServicesRepo {
  def insertService(service: ServiceCategory): Future[WriteResult]
  def updateService(id: BSONDocument, service: ServiceCategory): Future[WriteResult]
  def deleteService(id: BSONDocument): Future[WriteResult]
  def listAllService(pagination: Pagination, sort: FilterData): Future[List[JsObject]]
  def filterServiceById(id: BSONDocument): Future[Option[JsObject]]
  def filterServiceByName(name: String): Future[List[JsObject]]
  def countService(): Future[Int]

}

