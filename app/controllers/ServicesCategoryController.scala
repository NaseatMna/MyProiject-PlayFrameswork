
package controllers
import io.swagger.annotations._
import scala.concurrent._

import models.ServiceCategory
import play.api.i18n.MessagesApi
import models.repositories.IServicesRepo
import scala.util.Try
import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.bson.{ BSONDocument, BSONObjectID }
import scala.concurrent.duration._

/**
 * Created by Naseat on 10/14/2016.
 */
@Api(value = "/Category Of Service")
class ServicesCategoryController @Inject() (val servCategoryService: IServicesRepo, val messagesApi: MessagesApi) extends api.ApiController {

  /* Add New service category */
  @ApiOperation(
    value = "Add new service category",
    notes = "Post Method",
    response = classOf[models.ServiceCategory],
    httpMethod = "POST"
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Data for add store", required = true, dataType = "String", paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 201, message = "Success"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error")
  ))
  def addNewService = Action.async(BodyParsers.parse.json) {
    implicit request =>
      val service = (request.body).as[ServiceCategory]
      servCategoryService.insertService(service).map {
        case service => Created("Service has been Inserted :")
        case _ => BadRequest("Wrong Json Format")
      }.fallbackTo(Future(InternalServerError(" internal server error")))
  }
  /* Find Service by Service id */
  @ApiOperation(
    value = "Get service category by id",
    notes = "Returns exist service category by id",
    response = classOf[models.ServiceCategory],
    httpMethod = "GET"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error.")
  ))
  def searchServiceID(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    servCategoryService.filterServiceById(BSONDocument("_id" -> OId.get)).map(service => Ok(Json.toJson(service)))
  }

  /* search service category by category name */
  @ApiOperation(
    value = "Get service service category by name",
    notes = "Returns exist service category by name",
    response = classOf[models.ServiceCategory],
    httpMethod = "GET"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error.")
  ))
  def searchServiceName(name: String) = Action.async {

    servCategoryService.filterServiceByName(name).map(
      service => Ok(Json.toJson(service))
    )
  }

  /* Update Service category  */
  @ApiOperation(
    value = "Update  service category by id",
    notes = "Returns exist service id ",
    response = classOf[models.ServiceCategory],
    httpMethod = "PUT"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error.")
  ))
  def updateService(id: String) = Action.async(parse.json) { implicit request =>
    val service = (request.body).as[ServiceCategory]
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    val selector = BSONDocument("_id" -> OId.get)
    servCategoryService.updateService(selector, service).map {
      result => Accepted("Data has been Updated ")
    }
  }

  /*  Delete Service Category by ID */
  @ApiOperation(
    value = "delete  service category by id",
    notes = "Returns exist service id ",
    response = classOf[models.ServiceCategory],
    httpMethod = "DELETE"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error.")
  ))
  def deleteService(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    servCategoryService.deleteService(BSONDocument("_id" -> OId.get))
      .map(result => Accepted("Data has been Deleted :"))
  }
  /* List all Service Category */
  /*
  @ApiOperation(
    value = "Get all Service category",
    notes = "Returns list of all service category",
    response = classOf[models.ServiceCategory],
    httpMethod = "GET"
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Ok"),
    new ApiResponse(code = 400, message = "Bad Request"),
    new ApiResponse(code = 500, message = "Internal server error.")
  ))*/
  /*def searchAll(sort: String, page: Int, limit: Int) = SecuredApiAction { implicit request =>
    val sortData = new FilterData(sort)
    val getCount = Await.result(servCategoryService.countService(), 10 seconds)
    val pagination = new Pagination(page, limit, getCount)
    servCategoryService.listAllService(pagination, sortData).flatMap(hospital => ok(Json.toJson(WrappJson(hospital, pagination))))
  }*/

}

