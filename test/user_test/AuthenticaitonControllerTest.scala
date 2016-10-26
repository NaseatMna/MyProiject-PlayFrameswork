package user_test


import java.util.{UUID, Date}

import _root_.controllers.AuthenticationController
import akka.actor.ActorSystem
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.AsyncFunSuite
import org.scalatest.mockito.MockitoSugar
import play.api.i18n.{Messages, Lang, MessagesApi}
import play.api.libs.json.Json

import play.api.libs.mailer.MailerClient
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.{Environment, mvc}
import play.api.mvc.{Result, Results}
import user_test.repositories.IRepo
import user_test.services.IService

import scala.concurrent.Future


class AuthenticaitonControllerTest extends AsyncFunSuite with MockitoSugar with Results{

  val userServiveMock=mock[IService]
  val mailer=mock[MailerClient]
   val enviroment=mock[Environment]
  val messageApi=mock[MessagesApi]
  val system=mock[ActorSystem]
  val userRepoMock=mock[IRepo]

  val controller=new AuthenticationController(messageApi,system ,userServiveMock,mailer,enviroment)

  test("user login"){
    val request = FakeRequest().withBody(Json.obj(
     "user_email"->"xueping@2ntkh.com",
      "user_first_name"->"Guo",
      "user_last_name"->"Xueping",
      "user_password"->"123456",
      "user_confirm_password"->"123456",
      "user_token"->UUID.randomUUID().toString,
      "user_active"->false,
      "user_role"->4,
      "create_time"->new Date()
    ))

    val result: Future[Result] = controller.login().apply(request.withHeaders(CONTENT_TYPE -> "application/json"))
    val bodyText: String = contentAsString(result)
    assert(status(result) == 201)
 // assert(bodyText == "You login successfully!")

  }


test("user register"){
  val request = FakeRequest().withBody(Json.obj(
    "user_email"->"xueping@2ntkh.com",
    "user_first_name"->"Guo",
    "user_last_name"->"Xueping",
    "user_password"->"123456",
    "user_confirm_password"->"123456",
    "user_token"->UUID.randomUUID().toString,
    "user_active"->false,
    "user_role"->4,
    "create_time"->new Date()
  ))
  val result: Future[Result] = controller.register().apply(request.withHeaders(CONTENT_TYPE -> "application/json"))
  val bodyText: String = contentAsString(result)
  assert(status(result) == 201)
}


}
