import akka._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent._
import scala.util._

object Graph extends App {

  implicit val system: ActorSystem = ActorSystem("demo")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()

  val graph = RunnableGraph.fromGraph {
    GraphDSL.create() { implicit builder =>

      import GraphDSL.Implicits._

      val in = Source(1 to 10)
      val out = Sink.foreach{ x: Int => println(x) }.async
      
      val broadcast = builder.add(Broadcast[Int](2))
      val mergePoint = builder.add(Merge[Int](2))

      val f1, f3, f4 = Flow[Int].map(_ + 10)
      val f2 = Flow[Int].map(_ * 2)

      in ~> f1 ~> broadcast ~> f2 ~> mergePoint ~> out
      broadcast ~> f4 ~> mergePoint // mergerPoint has already been connected

      ClosedShape
    }
  }

  graph.run()

  sys.addShutdownHook {
    system.terminate()
  }

}
