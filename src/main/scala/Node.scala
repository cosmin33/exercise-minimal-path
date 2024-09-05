sealed trait Node[+A] {

  def flatMap[B](f: A => Node[B]): Node[B] =
    this match
      case EmptyNode => EmptyNode
      case LeafNode(value, left, right) => f(value) match
        case EmptyNode => EmptyNode
        case LeafNode(v, l, r) => LeafNode(v, left.flatMap(f), right.flatMap(f))

}

case object EmptyNode extends Node[Nothing]

case class LeafNode[A](value: A, left: Node[A], right: Node[A]) extends Node[A]
