package proscalafx.ch06

import java.{util => ju}
import javafx.{collections => jfxc}

import scala.collection.JavaConversions
import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableBuffer._


/** ScalaFX version of `FXCollectionsExample` from "Pro JavaFX 2" book.
  *
  * ScalaFX does not have a direct equivalent of JavaFX `FXCollections`.
  * Some of static methods from `FXCollections` are members of relevant observable collection in ScalaFX,
  * for instance `replaceAll`. Some are in companion objects, for instance, `shuffle`.
  * Some are not implemented and require use of `FXCollections`.
  *
  * @author Jarek Sacha
  */
object FXCollectionsExample extends App {

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
  strings.addAll("Zero", "One", "Two", "Three")

  println("Calling copy: ")
  jfxc.FXCollections.copy(strings, JavaConversions.seqAsJavaList(List("Four", "Five")))

  println("Calling replaceAll: ")
  strings.replaceAll("Two", "Two_1")

  println("Calling reverse: ")
  ObservableBuffer.revertBuffer(strings)

  println("Calling rotate(strings, 2: ")
  jfxc.FXCollections.rotate(strings.delegate, 2)

  println("Calling shuffle(strings): ")
  ObservableBuffer.shuffle(strings)

  println("Calling shuffle(strings, new Random(0L)): ")
  ObservableBuffer.shuffle(strings, new ju.Random(0L))

  println("Calling sort(strings): ")
  strings.sort()

  println("Calling sort(strings, c) with custom comparator: ")
  strings.sort((lhs, rhs) => lhs > rhs)

  println( """Calling fill(strings, "Ten"): """)
  ObservableBuffer.fillAll(strings, "Ten")


  def prettyPrint(change: Change) {
    change match {
      case Add(position, added)             =>
        println("\t\tKind of change: added")
        println("\t\tAdded size    : " + added.size)
        println("\t\tAdded sublist : " + mkString(added))
      case Remove(position, removed)        =>
        println("\t\tKind of change: removed")
        println("\t\tRemoved size  : " + removed.size)
        println("\t\tRemoved       : " + mkString(removed))
      case Reorder(start, end, permutation) =>
        val permutations = for (i <- start until end) yield i + "->" + permutation(i)
        println("\t\tKind of change: permuted")
        println("\t\tPermutation   : " + mkString(permutations))
      case Update(from, to) =>
        println("\t\tKind of change: updated\n")
        println("\t\t\tfrom: " + from + "\n")
        println("\t\t\tto  : " + to + "\n")

    }
  }


  def mkString[T](seq: TraversableOnce[T]): String = if (seq.isEmpty) "[]" else seq.mkString("[", ", ", "]")

}
