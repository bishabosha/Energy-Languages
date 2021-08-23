import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

final case class Tree(left: Tree, right: Tree) {
  def check: Int =
    left match {
      case null => 1
      case tl   => 1 + tl.check + right.check
    }
}

object Tree {
  final val EmptyTree = Tree(null, null)

  def apply(depth: Int): Tree =
    if (depth > 0) Tree(Tree(depth - 1), Tree(depth - 1))
    else EmptyTree

  def apply(depth: Int, futureDepth: Int = 0): Future[Tree] =
    if (futureDepth >= 4)
      Future.successful(Tree(depth))
    else if (depth > 0)
      Tree(depth - 1, futureDepth + 1) zip
        Tree(depth - 1, futureDepth + 1) map { case (left, right) =>
          Tree(left, right)
        }
    else Future.successful(EmptyTree)
}

object BinaryTrees {
  def main(args: Array[String]): Unit = {
    val n = Integer.parseInt(args.head)
    val minDepth = 4
    val maxDepth = n max (minDepth + 2)

    def print(name: String, depth: Int, check: Int) =
      println(name + " of depth " + depth + "\t check: " + check)

    print(
      "stretch tree",
      maxDepth + 1,
      Await.result(Tree(maxDepth + 1), Duration.Inf).check
    )

    val longLivedTree = Await.result(Tree(maxDepth), Duration.Inf)

    val tasks = Future.sequence {
      for {
        depth <- minDepth to maxDepth by 2
      } yield Future {
        val iterationsLimit = 1 << (maxDepth - depth + minDepth)
        val sum = 0
          .until(iterationsLimit)
          .foldLeft(0) { (sum, _) => sum + Tree(depth).check }
        (depth, iterationsLimit, sum)
      }
    }

    for {
      (depth, iterations, check) <- Await.result(tasks, Duration.Inf)
    } {
      print(s"$iterations\t trees", depth, check)
    }
    print("long lived tree", maxDepth, longLivedTree.check)

  }
}
