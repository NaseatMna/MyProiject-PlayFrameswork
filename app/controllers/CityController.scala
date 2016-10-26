package controllers

import javax.inject._
import models.City
import models.repositories.ICity
import play.api.i18n.MessagesApi
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Simple controller that directly stores and retrieves [models.City] instances into a MongoDB Collection
 * Input is first converted into a city and then the city is converted to JsObject to be stored in MongoDB
 */
@Singleton
class CityController @Inject() (val service: ICity, val messagesApi: MessagesApi) extends api.ApiController {

  def createNewCity = Action.async(parse.json) { implicit request =>
    val jsonBody: JsValue = request.body
    val city: City = jsonBody.as[City]
    service.insertCity(city).map {
      case true => Created("City has been inserted.")
      case _ => BadRequest("Wrong json format.")
    }.fallbackTo(Future(InternalServerError("There was an error at the server")))
  }

  def updateExistedCity(id: String) = Action.async(parse.json) { implicit request =>
    val jsonBody: JsValue = request.body
    val city: City = jsonBody.as[City]
    service.updateCity(id, city).map {
      case true => Created("City has been updated.")
      case _ => BadRequest("Wrong json format.")
    }.fallbackTo(Future(InternalServerError("There was an error at the server")))
  }

  def findCityByName(name: String) = Action.async {
    service.findByName(name).map { cities =>
      Ok(Json.toJson(cities))
    }
  }

  def findAllCity() = Action.async { implicit request =>
    service.findAll().map {
      case data if data != null => Ok(Json.toJson(data))
      case _ => NotFound("Data nof found.")
    }.fallbackTo(Future(InternalServerError("There was an error at the server")))
  }
}

