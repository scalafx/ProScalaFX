package proscalafx.ch06

import scalafx.collections.ObservableMap
import scalafx.collections.ObservableMap._


/** Example of processing of "change" notifications from ScalaFX `ObservableMap`, wrapper for JavaFX `ObservableMap`.
  *
  * ScalaFX used a different way of passing information about modification to `ObservableMap`.
  * Each modification is represented by a [[scalafx.collections.ObservableMap.Change]] object.
  */
object MapChangeEventExample extends App {

  val map = ObservableMap.empty[String, Int]
  map.onChange((map, change) => {
    println("\tmap = " + map.mkString("[", ", ", "]"))
    println(prettyChange(change))
  })

  println( """Calling map("First") = 1: """)
  map("First") = 1

  println( """Calling map(First") = 100: """)
  map("First") = 100

  val anotherMap = Map("Second" -> 2, "Third" -> 3)
  println( """Calling map ++= anotherMap: """)
  map ++= anotherMap

  println( """Removing by key: Calling map -= "Second"""")
  map -= "Second"

  println("Removing by value: Calling map retain({case (k, v) => v != 3})")
  map retain {case (k, v) => v != 3}


  def prettyChange(change: Change[_, _]): String = {
    val sb = new StringBuffer("\tChange event data:\n")

    change match {
      case Add(key, added) =>
        sb.append("\t\tWas added\n")
        sb.append("\t\tKey          : %s\n".format(key))
        sb.append("\t\tValue added  : %s\n".format(added))
      case Remove(key, removed) =>
        sb.append("\t\tWas removed\n")
        sb.append("\t\tKey          : %s\n".format(key))
        sb.append("\t\tValue removed: %s\n".format(removed))
      case Replace(key, added, removed) =>
        sb.append("\t\tWas replaced\n")
        sb.append("\t\tKey          : %s\n".format(key))
        sb.append("\t\tValue added  : %s\n".format(added))
        sb.append("\t\tValue removed: %s\n".format(removed))
    }

    sb.toString
  }
}