import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait Tree {
  def checkSum: Int
}

case class NonEmptyTree(left: Tree, right: Tree) extends Tree {
  def checkSum: Int = 1 + left.checkSum + right.checkSum
}

case object EmptyTree extends Tree {
  def checkSum: Int = 1
}

object Tree {

  def ofDepth(depth: Int): Tree = {
    if (depth > 0) NonEmptyTree(Tree.ofDepth(depth - 1), Tree.ofDepth(depth - 1))
    else EmptyTree
  }
}

object BinaryTrees {
  def main(args: Array[String]): Unit = {
    val n = Integer.parseInt(args.head)
    run(n)
  }

  def run(n: Int): Unit = {
    val minDepth = 4
    val maxDepth = n max (minDepth + 2)

    def print(name: String, depth: Int, check: Int) =
      println(s"$name of depth $depth\t check: $check")

    print(
      "stretch tree",
      maxDepth + 1,
      Tree.ofDepth(maxDepth + 1).checkSum
    )

    val longLivedTree = Tree.ofDepth(maxDepth)

    def runTask(depth: Int) = Future {
      val iterations = 1 << (maxDepth - depth + minDepth)
      val checkSum =
        (1 to iterations)
          .foldLeft(0) { case (checkSum, _) =>
            checkSum + Tree.ofDepth(depth).checkSum
          }
      (iterations, depth, checkSum)
    }

    val iterations = minDepth to maxDepth by 2

    val task = Future
      .traverse(iterations)(runTask)
      .map(_.foreach { case (iterations, depth, check) =>
        print(s"$iterations\t trees", depth, check)
      })
    Await.ready(task, Duration.Inf)

    print("long lived tree", maxDepth, longLivedTree.checkSum)
  }
}
