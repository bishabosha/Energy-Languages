import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

final case class Tree(left: Tree, right: Tree) {
  def checkSum: Int =
    left match {
      case null => 1
      case tl   => 1 + tl.checkSum + right.checkSum
    }
}

object Tree {
  final val EmptyTree = Tree(null, null)

  def sync(depth: Int): Tree = {
    def innerBranch() = Tree.sync(depth - 1)
    if (depth > 0) Tree(innerBranch(), innerBranch())
    else EmptyTree
  }

  def async(depth: Int, futureDepth: Int = 0): Future[Tree] = {
    def asyncBranch() = Tree.async(depth - 1, futureDepth + 1)

    if (depth == 0) Future.successful(EmptyTree)
    else if (futureDepth >= 4) Future.successful(Tree.sync(depth))
    else
      asyncBranch()
        .zipWith(asyncBranch()) {
          Tree(_, _)
        }
  }
}

object BinaryTrees {
  def main(args: Array[String]): Unit = {
    val n = Integer.parseInt(args.head)
    val minDepth = 4
    val maxDepth = n max (minDepth + 2)

    def print(name: String, depth: Int, check: Int) =
      println(s"$name of depth $depth\t check: $check")

    print(
      "stretch tree",
      maxDepth + 1,
      Await.result(Tree.async(maxDepth + 1), Duration.Inf).checkSum
    )

    val longLivedTree = Await.result(Tree.async(maxDepth), Duration.Inf)

    def runTasks() = {
      for {
        depth <- minDepth to maxDepth by 2
        iterationsLimit = 1 << (maxDepth - depth + minDepth)
      } yield Future {
        val checkSum =
          Iterator
          .continually(Tree.sync(depth))
          .take(iterationsLimit)
          .foldLeft(0)(_ + _.checkSum)
        (iterationsLimit, depth, checkSum)
      }
    }

    val task = Future
      .sequence(runTasks())
      .map(_.foreach { case (iterations, depth, check) =>
        print(s"$iterations\t trees", depth, check)
      })
    Await.ready(task, Duration.Inf)

    print("long lived tree", maxDepth, longLivedTree.checkSum)
  }
}
