case class SparseMatrix[A](data: Map[Int, Map[Int, A]]) {
  def get(row: Int, col: Int): Option[A] = data.get(row).flatMap(_.get(col))
}
