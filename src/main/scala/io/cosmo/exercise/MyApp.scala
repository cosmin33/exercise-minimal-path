package io.cosmo.exercise

import io.cosmo.exercise.MyApp.Environment
import zio.*
import zio.Console.*
import zio.stream.*

import scala.collection.immutable.Stream

object MyApp extends ZIOAppDefault {

  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = myAppLogic

  val myAppLogic: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    for {
      fileOpt <- ZIOAppArgs.getArgs.map(_.headOption)
      _       <- streamToArr(readFile(fileOpt.getOrElse("./data/data.txt")))
        .map(SparseMatrix.apply)
        .map(_.getMinPaths)
        .flatMap(s => printLine(s"minimal path is ${s._1.mkString(" + ")} = ${s._2}"))
    } yield ()

//  val myAppLogic: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
//    for {
//      fileOpt <- ZIOAppArgs.getArgs.map(_.headOption)
//      _       <- streamToArr(readFile(fileOpt.getOrElse("./data/data.txt")))
//        .map(SparseMatrix.apply)
//        .map(sm => NodeLib.fromSparseMatrix(sm))
//        .map(_.getMinPath(Position(0, 0)))
//        .flatMap(s => printLine(s"minimal path is ${s._1.mkString(" + ")} = ${s._2}"))
//    } yield ()
//

  def readFile(path: String): zio.stream.Stream[Throwable, String] =
    zio.stream.ZStream.fromFileName(path)
      .via(ZPipeline.utfDecode)
      .via(ZPipeline.splitLines)
      .map(_.trim)
      .filterNot(_.isEmpty)

  def streamToArr(stream: zio.stream.Stream[Throwable, String]): ZIO[Any, Throwable, Array[Array[Int]]] =
    stream
      .map(_.split(" +").map(_.toInt))
      .run(ZSink.collectAll)
      .map(_.toArray)


}