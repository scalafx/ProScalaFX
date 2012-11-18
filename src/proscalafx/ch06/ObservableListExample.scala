package proscalafx.ch06

import scalafx.collections.ObservableBuffer


/**
 * @author Jarek Sacha
 */
object ObservableListExample extends App {

  // `println` statements show JavaFX API, for easier comparison to ScalaFX API used in the code.

  val strings = new ObservableBuffer[String]()
  strings.onInvalidate(println("\tlist invalidated"))
  strings.onChange((source, change) => println("\tstrings = " + source.mkString("[", ", ", "]")))

  println( """Calling add("First"): """)
  strings += "First"

  println( """Calling add(0, "Zeroth"): """)
  strings.insert(0, "Zeroth")

  println( """"Calling addAll("Second", "Third"): """)
  strings ++= List("Second", "Third")

  println("Calling set(1, \"New First\"): ")
  strings(1) = "New First"

  val list = List("Second_1", "Second_2")
  println("Calling addAll(3, list): ")
  strings.insertAll(3, list)

  // There is a difference between meaning of the second argument of `remove` in JavaFX and ScalaFX.
  // ScalaFX `remove` corresponds to `scala.collection.mutable.Buffer.remove(n:Int, count:Int)`.
  // Equivalent of JavaFX `remove` is renamed in ScalaFX to `removeRange`.
  println("Calling remove(2, 4): ")
  strings.removeRange(2, 4)

  scala.collection.mutable.Buffer

  println( """Remove elements that contain letter "t"""")
  strings --= strings.filter(_.contains("t"))

  println( """Calling removeAll("Third", "Fourth"): """)
  strings --= List("Third", "Fourth")
}
