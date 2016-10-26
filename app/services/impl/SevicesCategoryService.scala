
package services.impl

import javafx.scene.control.Pagination

import api.Api.Sorting._
import api.{ FilterData, Page }
import com.google.inject.Inject
import models.ServiceCategory
import models.repositories.IServicesRepo
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import services.IServicesCategoryService

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
/**
 * Created by Naseat on 10/14/2016.
 */
class SevicesCategoryService @Inject() (repoService: IServicesRepo) extends IServicesCategoryService {
  override def insertService(service: ServiceCategory): Future[WriteResult] = repoService.insertService(service)

  override def deleteServices(id: BSONDocument): Future[WriteResult] = repoService.deleteService(id)

  override def countServices(): Future[Int] = repoService.countService()

  override def filterServiceById(id: BSONDocument): Future[Option[JsObject]] = repoService.filterServiceById(id)

  override def listAllServices(pagination: Pagination, sort: FilterData): Future[List[JsObject]] = ???

  override def updateService(id: BSONDocument, service: ServiceCategory): Future[WriteResult] = ???

  override def filterServiceByName(name: String): Future[List[JsObject]] = repoService.filterServiceByName(name)
}

