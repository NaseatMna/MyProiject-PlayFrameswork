package user_test.repositories

import com.google.inject.ImplementedBy
import models.UserModel
import models.repositories.impl.UserRepo

import scala.concurrent.Future

@ImplementedBy(classOf[Repo])
trait IRepo {

  def insert(user: UserModel): Future[Int]
  def update(user: UserModel): Future[Int]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[UserModel]
  def findByEmail(email: String): Future[Option[UserModel]]
  def list(): Future[Seq[UserModel]]
  def count: Future[Int]
  def updateActive(user: UserModel): Future[Int]
  def findByToken(token: String): Future[Option[UserModel]]
  def findByEmailAndPwd(email:String,password:String):Future[Option[UserModel]]

}
