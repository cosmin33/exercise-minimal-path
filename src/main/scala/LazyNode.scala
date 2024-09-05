sealed trait LazyNode[+A] {

  def flatMap[B](f: A => LazyNode[B]): LazyNode[B] =
    this match
      case LazyEmpty => LazyEmpty
      case LazyLeaf(value, left, right) => 
        f(value) match
          case LazyEmpty => LazyEmpty
          case LazyLeaf(v, l, r) => LazyLeaf(v, () => left().flatMap(f), () => right().flatMap(f))
  
}

case object LazyEmpty extends LazyNode[Nothing]

case class LazyLeaf[A](value: A, left: () => LazyNode[A], right: () => LazyNode[A]) extends LazyNode[A]

object LazyNode {
  
  /** returns an infinite LazyNode that contains matrix positions
   * The first positions are (row: 1, col: 1), then the left and right jump the row and square the column
   * so that you can get the precise positions in a (sparce) matrix with the triangle shape */
  def getInfinitePositions(row: Int, col: Int): LazyNode[(Int, Int)] =
    LazyLeaf((row, col), () => getInfinitePositions(row + 1, col * 2 - 1), () => getInfinitePositions(row + 1, col * 2))
    
}
