package proscalafx.ch06

import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableBuffer._


/** Example of processing of "change" notifications from ScalaFX `ObservableBuffer`, wrapper for JavaFX `ObservableList`.
  *
  * This example corresponds to JavaFX example `ListChangeEventExample`.
  *
  * ScalaFX is using a different way of passing information about modification to `ObservableBuffer`.
  * Each modification is represented by a [[scalafx.collections.ObservableBuffer.Change]] object.
  */
object BufferChangeEventExample extends App {

  val strings = new ObservableBuffer[String]
  strings.onChange((buffer, changes) => {
    println("\tbuffer = " + buffer.mkString("[", ", ", "]"))
    val log = changes
      .zipWithIndex
      .foldLeft("\tChange event data:\n")({case (string, (change, index)) => string + prettyPrint(index, change)})
    println(log)
  })

  println("""Calling strings ++= Seq("Zero", "One", "Two", "Three"): """)
  strings ++= Seq("Zero", "One", "Two", "Three")

  println("Calling strings.sort: ")
  strings.sort()

  println( """Calling strings(1) = "Three_1": """)
  strings(1) = "Three_1"

  println( """Calling strings.setAll("One_1", "Three_1", "Two_1", "Zero_1"): """)
  strings.setAll("One_1", "Three_1", "Two_1", "Zero_1")

  println("""Calling strings --= Seq("One_1", "Two_1", "Zero_1"): """)
  strings --= Seq("One_1", "Two_1", "Zero_1")


  private def prettyPrint(index: Int, change: Change[String]): String = {
    val sb = new StringBuffer("\t\tcursor = " + index + "\n")
    sb.append("\t\tKind of change: ")

    change match {
      case Add(position, added) =>
        sb.append("added\n")
        sb.append("\t\tPosition: " + position + "\n")
        sb.append("\t\tElement : " + added + "\n")
      case Remove(position, removed) =>
        sb.append("removed\n")
        sb.append("\t\tPosition: " + position + "\n")
        sb.append("\t\tElement : " + removed + "\n")
      case Reorder(start, end, permutation) =>
        sb.append("reordered\n")
        sb.append("\t\tAffected Range: [%d, %d]\n".format(start, end))
        val strLog = (start until end)
          .map(i => "%d->%s".format(i, permutation(i)))
          .mkString("\t\tPermutation: [", ", ", "]\n")
        sb.append(strLog)
      case Update(from, to) =>
        sb.append("updated\n")
        sb.append("\t\tfrom: " + from + "\n")
        sb.append("\t\tto  : " + to + "\n")
    }

    sb.toString
  }
}