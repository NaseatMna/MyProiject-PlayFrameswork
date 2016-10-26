import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

/**
  * Created by Acer on 10/25/2016.
  */
class TestMongoConnection @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  def conn = reactiveMongoApi.database.map(_.collection[JSONCollection]("macrocoupon"))



}
