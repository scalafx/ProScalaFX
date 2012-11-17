package proscalafx.ch06

import scalafx.collections.ObservableBuffer._
import scalafx.collections.ObservableBuffer

object BufferChangeEventExample extends App {

  private def prettyPrint(index: Int, change: Change): String = {
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
    }

    sb.toString
  }

  def onChange[T](buffer: ObservableBuffer[T], changes: Seq[Change]) {
    println("\tbuffer = " + buffer)
    val log = changes
      .zipWithIndex
      .foldLeft("\tChange event data:\n")({ case (string, (change, index)) => string + prettyPrint(index, change) })
    println(log)
  }

  val strings = new ObservableBuffer[String]
  strings.onChange(onChange(_, _))

  println("Calling strings += (\"Zero\", \"One\", \"Two\", \"Three\"): ");
  strings += ("Zero", "One", "Two", "Three")

  println("Calling strings.sort: ");
  strings.sort

  println("Calling strings(1) = \"Three_1\": ");
  strings(1) = "Three_1"

  println("Calling strings.setAll(\"One_1\", \"Three_1\", \"Two_1\", \"Zero_1\"): ");
  strings.setAll("One_1", "Three_1", "Two_1", "Zero_1");

  println("Calling strings -= (\"One_1\", \"Two_1\", \"Zero_1\"): ");
  strings -= ("One_1", "Two_1", "Zero_1");

}