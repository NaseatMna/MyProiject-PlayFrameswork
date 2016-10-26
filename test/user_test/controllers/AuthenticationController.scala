
package controllers


import java.util.UUID

import models.UserModel
import models.manage_products.Product
import org.joda.time.DateTime
import play.api.libs.functional.syntax._


import akka.actor.ActorSystem
import api.ApiController
import api.ApiError._

import play.api.Environment
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json._
import play.api.libs.mailer.{Email, MailerClient}


import javax.inject.Inject
import play.api.mvc.{BodyParsers, Action}
import user_test.services.IService
import scala.Product
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class AuthenticationController @Inject()(val messagesApi: MessagesApi, system: ActorSystem, userService: IService, mailer: MailerClient, environment: Environment) extends ApiController {


  implicit val loginIfoReads: Reads[Tuple2[String, String]] = (
    (__ \ "user_email").read[String](Reads.email) and
      (__ \ "user_password").read[String] tupled
    )


  def login = Action.async(BodyParsers.parse.json) { implicit request =>
  {
    val jsonBody: JsValue = request.body
    //converting json to case class
    val user: UserModel = jsonBody.as[UserModel]
    println(s"case class $user")

    Future(userService.findByEmailAndPwd(user.user_email,user.user_password)).map(result => Created("You login successfully! " + result)).recoverWith {
      case _: Throwable =>
        Future(BadRequest("Wrong json format"))
    }.fallbackTo(Future(InternalServerError("There was an error at the server")))
  }
  }




  def register() = Action.async(BodyParsers.parse.json) { implicit request =>
  {
    val jsonBody: JsValue = request.body
    //converting json to case class
    val user: UserModel = jsonBody.as[UserModel]
    println(s"case class $user")

    Future(userService.insert(user)).map(result => Created("User has already regist: " + result)).recoverWith {
      case _: Throwable =>
        Future(BadRequest("Wrong json format"))

    }. fallbackTo(Future(InternalServerError("There was an error at the server")))
  }
  }









}
