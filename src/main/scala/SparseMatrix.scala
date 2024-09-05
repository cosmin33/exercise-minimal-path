trait SparseMatrix[A] {
  def get(row: Int, col: Int): Option[A]
}

object SparseMatrix {
  def apply[A](data: Map[Int, Map[Int, Int]]): SparseMatrix[Int] = 
    (row: Int, col: Int) => data.get(row).flatMap(_.get(col))
}