package io.cosmo.exercise

trait NodeLib[Id] {
  
  def get(id: Id): Option[Int]
  
  def leftChild(id: Id): Id
  def rightChild(id: Id): Id
  
  def getMinPath(startingId: Id): (List[Int], Int) = {
    def getMinPathRec(id: Id, acc: (List[Int], Int)): (List[Int], Int) =
      get(id) match {
        case None => acc
        case Some(value) =>
          val leftId = leftChild(id)
          val rightId = rightChild(id)
          val leftAcc = getMinPathRec(leftId, acc)
          val rightAcc = getMinPathRec(rightId, acc)
          if (leftAcc._2 < rightAcc._2) (value :: leftAcc._1, value + leftAcc._2)
          else (value :: rightAcc._1, value + rightAcc._2)
      }
      
    getMinPathRec(startingId, (Nil, 0))
  }
  
}

case class Position(row: Int, col: Int)

object NodeLib {
  def fromSparseMatrix(data: SparseMatrix[Int]): NodeLib[Position] = new NodeLib[Position] {
    def get(id: Position): Option[Int] = data.get(id.row, id.col)
    def leftChild(id: Position): Position = Position(id.row + 1, id.col)
    def rightChild(id: Position): Position = Position(id.row + 1, id.col + 1)
  }
}
