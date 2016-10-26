
package models.repositories.impl

import javax.inject.Inject
import api.{ FilterData, Pagination }
import models.ServiceCategory
import models.repositories.IServicesRepo
import play.api.libs.json.{ JsObject, Json }
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.bson.BSONDocument
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.play.json.collection.JSONCollection
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.ReactiveMongoApi

/**
 * Created by Naseat on 10/14/2016.
 */

class ServicesRepo @Inject() (reactiveMongoApi: ReactiveMongoApi) extends IServicesRepo {

  /* Connection to DataBase */
  def collection = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("tblServiceCategory"))

  /* Insert Service Category  */
  override def insertService(service: ServiceCategory): Future[WriteResult] = {

    collection.flatMap(_.insert(service))

  }

  /* Delete Service Category  */
  override def deleteService(id: BSONDocument): Future[WriteResult] = {

    collection.flatMap(_.remove(id))

  }

  /* Count All Service Category */
  override def countService(): Future[Int] = {

    collection.flatMap(_.count())
  }

  /* Filter Service by It ID */
  override def filterServiceById(id: BSONDocument): Future[Option[JsObject]] = {

    collection.flatMap(_.find(id).one[JsObject])
  }

  /* List All Service Category  */
  override def listAllService(pagination: Pagination, sort: FilterData): Future[List[JsObject]] = ???

  //  {
  //    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.key -> sort.value)))
  //    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
  //    cursor.flatMap(_.collect[List](pagination.Size))
  //  }

  /* Filter Service Category by it Name */
  override def filterServiceByName(name: String): Future[List[JsObject]] = {

    val genericQueryBuilder = collection.map(_.find(Json.obj("NAME" -> name)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List]())
  }

  /* Update Service Category */
  override def updateService(id: BSONDocument, service: ServiceCategory): Future[WriteResult] = {

    collection.flatMap(_.update(id, service))
  }
}

