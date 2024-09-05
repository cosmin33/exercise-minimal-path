package io.cosmo.exercise

trait SparseMatrix[A]() {
  def get(row: Int, col: Int): Option[A]
}

object SparseMatrix {
  def apply[A](data: Array[Array[A]]): SparseMatrix[A] =
    (row: Int, col: Int) =>
      if (row < data.length && col < data(row).length) Some(data(row)(col))
      else None
}

//case class SparseMatrix[A](data: Map[Int, Map[Int, A]]) {
//  def get(row: Int, col: Int): Option[A] = data.get(row).flatMap(_.get(col))
//}
