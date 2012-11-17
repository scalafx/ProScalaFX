package proscalafx.ch06

import scalafx.collections.ObservableBuffer

object ObservableBufferExample extends App {
  val strings = new ObservableBuffer[String]

  strings.onInvalidate(println("\tlist invalidated"))

  strings.onChange((buffer, changes) => {
    println("\tstrings = " + buffer)
  })

  println("Calling += \"First\": ");
  strings += "First"

  println("Calling insert(0, \"Zeroth\"): ");
  strings.insert(0, "Zeroth")

  println("Calling += (\"Second\", \"Third\"): ")
  strings += ("Second", "Third")

  println("Calling (1) = \"New First\": ")
  strings(1) = "New First"

  val list = List("Second_1", "Second_2")
  println("Calling insertAll(3, list): ")
  strings.insertAll(3, list)

  println("Calling removeRange(2, 4): ")
  strings.removeRange(2, 4)

  val filter = (str: String) => str.contains("t")
  while(strings.find(filter).isDefined) {
    println("Calling -= on iteraction: ");
    strings -= strings.find(filter).get
  }

  println("Calling -= (\"Third\", \"Fourth\"): ")
  strings -= ("Third", "Fourth")
}