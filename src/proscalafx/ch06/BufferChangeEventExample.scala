package proscalafx.ch06

import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableBuffer.{Reorder, Remove, Add, Change}


/** Example of processing of "change" notifications from ScalaFX `ObservableBuffer`, wrapper for JavaFX `ObservableList`.
  *
  * This example corresponds to JavaFX example `ListChangeEventExample`.
  *
  * ScalaFX is using a different way of passing information about modification to `ObservableBuffer`.
  * Each modification is represented by a [[scalafx.collections.ObservableBuffer.Change]] object.
  *
  * @author Jarek Sacha
  */
object BufferChangeEventExample extends App {

  // `println` statements show JavaFX API, for easier comparison to ScalaFX API used in the code.


  val strings = new ObservableBuffer[String]()
  strings.onChange(
    (source, changes) => {
      println("\tlist = " + source.mkString("[", ", ", "]"))
      changes.foreach(prettyPrint)
      println()
    }
  )

  println( """Calling addAll("Zero", "One", "Two", "Three"): """)
  strings ++= List("Zero", "One", "Two", "Three")

  println("Calling sort: ")
  strings.sort()

  println( """Calling set(1, "Three_1"): """)
  strings.update(1, "Three_1")

  println( """Calling setAll("One_1", "Three_1", "Two_1", "Zero_1"): """)
  strings.setAll("One_1", "Three_1", "Two_1", "Zero_1")

  println( """Calling removeAll("One_1", "Two_1", "Zero_1"): """)
  strings.removeAll("One_1", "Two_1", "Zero_1")


  def prettyPrint(change: Change) {
    change match {
      case Add(position, added)             => {
        println("\t\tKind of change: added")
        println("\t\tAdded size    : " + added.size)
        println("\t\tAdded sublist : " + mkString(added))
      }
      case Remove(position, removed)        => {
        println("\t\tKind of change: removed")
        println("\t\tRemoved size  : " + removed.size)
        println("\t\tRemoved       : " + mkString(removed))
      }
      case Reorder(start, end, permutation) => {
        val permutations = for (i <- start until end) yield (i + "->" + permutation(i))
        println("\t\tKind of change: permuted")
        println("\t\tPermutation   : " + mkString(permutations))
      }
    }
  }


  def mkString[T](seq: TraversableOnce[T]): String = if (seq.isEmpty) "[]" else seq.mkString("[", ", ", "]")
}
