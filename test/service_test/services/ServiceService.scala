package service_test.services

import api.Page
import com.google.inject.Inject
import models.Service
import service_test.repositories.IServiceTest

import scala.concurrent.Future

/**
 * Created by Acer on 10/20/2016.
 */
class ServiceService @Inject()(repo: IServiceTest) extends IServService {
  override def insertNewService(service: Service): Future[Int] = repo.insertNewService(service)

  override def sum(x: Int, y: Int): Int = repo.sum(x,y)

  override def getServiceId(): Future[Int] = repo.getServiceId()
}
