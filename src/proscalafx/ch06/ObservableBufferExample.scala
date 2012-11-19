package proscalafx.ch06

import scalafx.collections.ObservableBuffer


/** This example corresponds to JavaFX example `ObservableListExample`.
  *
  * In ScalaFX `ObservableBuffer` is a wrapper for JavaFX `ObservableList`.
  */
object ObservableBufferExample extends App {

  val strings = new ObservableBuffer[String]()
  strings.onInvalidate(println("\tlist invalidated"))
  strings.onChange((source, change) => println("\tstrings = " + source.mkString("[", ", ", "]")))

  println( """Calling += "First": """)
  strings += "First"

  println( """Calling insert(0, "Zeroth"): """)
  strings.insert(0, "Zeroth")

  println( """"Calling +=("Second", "Third"): """)
  strings +=("Second", "Third")

  println( """Calling (1) = "New First"): """)
  strings(1) = "New First"

  val list = List("Second_1", "Second_2")
  println("Calling addAll(3, list): ")
  strings.insertAll(3, list)

  // There is a difference between meaning of the second argument of `remove` in JavaFX and ScalaFX.
  // ScalaFX `remove` corresponds to `scala.collection.mutable.Buffer.remove(n:Int, count:Int)`.
  // Equivalent of JavaFX `remove` is renamed in ScalaFX to `removeRange`.
  println("Calling removeRange(2, 4): ")
  strings.removeRange(2, 4)

  println( """Remove elements that contain letter "t"""")
  strings --= strings.filter(_.contains("t"))

  println( """Calling -=("Third", "Fourth"): """)
  strings -=("Third", "Fourth")
}
