package user_test.services


import com.google.inject.ImplementedBy
import models.UserModel


import scala.concurrent.Future

@ImplementedBy(classOf[Service])
trait IService {

  def insert(user: UserModel): Future[Int]
  def update(id: Long, user: UserModel): Future[Int]
  def updateActive(token: String, user: UserModel): Future[Int]
  def delete(id: Long): Future[Int]
  def list(): Future[Seq[UserModel]]
  def findById(id: Long): Future[UserModel]
  def findByEmail(email: String): Future[Option[UserModel]]
  def count(): Future[Int]
  def findByToken(token: String): Future[Option[UserModel]]
  def findByEmailAndPwd(email:String,password:String):Future[Option[UserModel]]

}
