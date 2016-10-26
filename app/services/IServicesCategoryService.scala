
package services

/**
 * Created by Naseat on 10/14/2016.
 */

import javafx.scene.control.Pagination

import api.{ FilterData, Page }
import com.google.inject.ImplementedBy
import models.ServiceCategory
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import services.impl.SevicesCategoryService
import scala.concurrent.Future

@ImplementedBy(classOf[SevicesCategoryService])
trait IServicesCategoryService {
  def insertService(service: ServiceCategory): Future[WriteResult]
  def updateService(id: BSONDocument, service: ServiceCategory): Future[WriteResult]
  def deleteServices(id: BSONDocument): Future[WriteResult]
  def listAllServices(pagination: Pagination, sort: FilterData): Future[List[JsObject]]
  def filterServiceById(id: BSONDocument): Future[Option[JsObject]]
  def filterServiceByName(name: String): Future[List[JsObject]]
  def countServices(): Future[Int]
}

