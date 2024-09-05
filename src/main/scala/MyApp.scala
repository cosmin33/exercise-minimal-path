import zio._
import zio.Console.*
import zio.stream.*

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic =
    for {
      _    <- printLine("Here it is: ")
      _    <- readFile("./data/data.txt").foreach(s => printLine("> " + s))
      _    <- printLine("=================================================")
      _    <- streamToMap(readFile("./data/data.txt"))
        .map(SparseMatrix.apply)
        .tap(s => printLine(s))
        .map(sm => NodeLib.fromSparseMatrix(sm))
        .map(_.getMinPath(Position(0, 0)))
        .flatMap(s => printLine(s))
    } yield ()


  def readFile(path: String): Stream[Throwable, String] =
    zio.stream.ZStream.fromFileName(path)
      .via(ZPipeline.utfDecode)
      .via(ZPipeline.splitLines)
      .map(_.trim)
      .filterNot(_.isEmpty)

  def streamToMap(stream: Stream[Throwable, String]): ZIO[Any, Throwable, Map[Int, Map[Int, Int]]] =
    stream
      .map(_.split(" +").toList.map(_.toInt).zipWithIndex.map(_.swap).toMap)
      .zipWithIndex
      .map(_.swap)
      .map(p => (p._1.toInt, p._2))
      .run(ZSink.collectAll)
      .map(_.toMap)



}