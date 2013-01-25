package proscalafx.ch06

import scalafx.collections.ObservableSet
import scalafx.collections.ObservableSet._

object SetChangeEventExample extends App {

  def prettyChange(change: Change[_]): String = {
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

  def onChange[T](set: ObservableSet[T], change: Change[T]) {
    println("\tset = " + set)
    println(prettyChange(change))
  }

  val set = ObservableSet.empty[String]
  set.onChange(onChange(_, _))

  println("Calling set += \"First\": ")
  set += "First"

  println("Calling set += (\"Second\", \"Third\"): ")
  set +=("Second", "Third")

  println("Calling set -= \"Second\": ")
  set -= "Second"

  println("Calling set -= (\"First\", \"Third\"): ")
  set -=("First", "Third")

}