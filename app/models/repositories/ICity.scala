package models.repositories

import javax.inject.Inject
import com.google.inject.ImplementedBy
import models.City
import play.api.libs.json.Json
import reactivemongo.api.{ Cursor, ReadPreference }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.play.json.collection.JSONCollection
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.ReactiveMongoApi

/**
 * Created by Oudam on 10/25/2016.
 */
@ImplementedBy(classOf[CityImpl])
trait ICity {

  def insertCity(city: City): Future[Boolean]
  def updateCity(id: String, city: City): Future[Boolean]
  def findByName(name: String): Future[List[City]]
  def findAll(): Future[List[City]]

}
class CityImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends ICity {

  def cities: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("city"))

  override def insertCity(city: City): Future[Boolean] = {
    try {
      cities.flatMap(_.insert(city)).map(_ => true)
    } catch {
      case _: Throwable => Future(false)
    }

  }

  override def updateCity(id: String, city: City): Future[Boolean] = {
    try {
      cities.flatMap(_.update(Json.obj("_id" -> id), city)).map(_ => true)
    } catch {
      case _: Throwable => Future(false)
    }
  }

  override def findByName(name: String): Future[List[City]] = {
    try {
      cities.flatMap {
        _.find(Json.obj("name" -> name)).
          cursor[City](ReadPreference.primary).
          collect[List](Int.MaxValue, Cursor.FailOnError[List[City]]())
      }
    } catch {
      case _: Throwable => Future(null)
    }
  }

  override def findAll(): Future[List[City]] = {
    try {
      val genericQueryBuilder = cities.map(_.find(Json.obj()))
      val cursor = genericQueryBuilder.map(_.cursor[City](ReadPreference.primary))
      cursor.flatMap(_.collect[List](Int.MaxValue, Cursor.FailOnError[List[City]]()))
    } catch {
      case _: Throwable => Future(null)
    }
  }
}