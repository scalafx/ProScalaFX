package proscalafx.ch06

import scalafx.collections.ObservableSet
import scalafx.collections.ObservableSet.*

object SetChangeEventExample extends App {

  def prettyChange(change: Change[?]): String = {
    val sb = new StringBuffer("\tChange event data:\n")

    change match {
      case Add(added) =>
        sb.append("\t\tWas added\n")
        sb.append("\t\tValue added  : %s\n".format(added))
      case Remove(removed) =>
        sb.append("\t\tWas removed\n")
        sb.append("\t\tValue removed: %s\n".format(removed))
    }

    sb.toString
  }

  def onChange[T](set: ObservableSet[T], change: Change[T]): Unit = {
    println("\tset = " + set)
    println(prettyChange(change))
  }

  val set = ObservableSet.empty[String]
  set.onChange(onChange(_, _))

  println("Calling set += \"First\": ")
  set += "First"

  println("Calling set ++= Seq(\"Second\", \"Third\"): ")
  set ++= Seq("Second", "Third")

  println("Calling set -= \"Second\": ")
  set -= "Second"

  println("Calling set --= Seq(\"First\", \"Third\"): ")
  set --= Seq("First", "Third")

}
