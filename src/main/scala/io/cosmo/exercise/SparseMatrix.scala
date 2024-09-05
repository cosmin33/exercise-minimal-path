package io.cosmo.exercise

trait SparseMatrix {
  protected def data: Array[Array[Int]]

  def get(row: Int, col: Int): Option[Int]=
    if (row < data.length && col < data(row).length) Some(data(row)(col))
    else None

  def rowCount: Int = data.length

  def getRowMinPaths(row: Int, previousPaths: Vector[(List[Int], Int)]): Vector[(List[Int], Int)] = {
    val currentRow = (0 until row + 1).toVector
    val currentPaths = currentRow.map(col => get(row, col).get)
    val minPaths = currentPaths.zipWithIndex.map { case (value, col) =>
      val leftPath = previousPaths(col)._2
      val rightPath = previousPaths(col + 1)._2
      if (leftPath < rightPath) (value :: previousPaths(col)._1, value + leftPath)
      else (value :: previousPaths(col + 1)._1, value + rightPath)
    }
    minPaths
  }

  def getMinPaths: (List[Int], Int) = {
    val lastRow = rowCount - 1
    val lastRowPaths = (0 to lastRow).map(col => get(lastRow, col).get).map(value => (List(value), value)).toVector
    (0 until lastRow).reverse.foldLeft(lastRowPaths) { case (previousPaths, row) =>
      getRowMinPaths(row, previousPaths)
    }.head
  }

}

object SparseMatrix {
  def apply(d: Array[Array[Int]]): SparseMatrix = new SparseMatrix {
    override protected val data: Array[Array[Int]] = d
    assert {
      data.indices.forall { row =>
        data(row).length == row + 1
      }
    }
  }

}
