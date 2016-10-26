package user_test.services


import com.google.inject.Inject
import models.UserModel
import user_test.repositories.IRepo
import scala.concurrent. Future

class Service @Inject()(userRepo: IRepo) extends IService {

  override def insert(user: UserModel): Future[Int] = userRepo.insert(user)

  override def count(): Future[Int] = userRepo.count

  override def update(id: Long, user: UserModel): Future[Int] = {
    var user1 = UserModel(Option(id), user.user_email, user.user_first_name, user.user_last_name, user.user_password, user.user_confirm_password, user.user_token, user.user_active, user.user_role, user.create_time)
    userRepo.update(user1)
  }

  override def findById(id: Long): Future[UserModel] = userRepo.findById(id)

  override def delete(id: Long): Future[Int] = userRepo.delete(id)

  override def list(): Future[Seq[UserModel]] = userRepo.list()

override def findByEmail(email: String): Future[Option[UserModel]] = userRepo.findByEmail(email)

  override def updateActive(token: String, user: UserModel): Future[Int] = {
    var user1 = UserModel(user.user_id, user.user_email, user.user_first_name, user.user_last_name, user.user_password, user.user_confirm_password, user.user_token, true, user.user_role, user.create_time)
    userRepo.updateActive(user1)
  }

  override def findByToken(token: String): Future[Option[UserModel]] = userRepo.findByToken(token)

  override def findByEmailAndPwd(email: String, password: String): Future[Option[UserModel]] =userRepo.findByEmailAndPwd(email,password)
}
