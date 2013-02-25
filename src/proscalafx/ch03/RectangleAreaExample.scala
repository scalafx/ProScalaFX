package proscalafx.ch03

import scalafx.beans.property.DoubleProperty


object RectangleAreaExample extends App {
  println("Constructing x with initial value of 2.0.")
  val x = new DoubleProperty(null, "x", 2.0)
  println("Constructing y with initial value of 3.0.")
  val y = new DoubleProperty(null, "y", 3.0)
  println("Creating binding area with dependencies x and y.")
  val area = x * y
  println("area = " + area())
  println("Setting x to 5")
  x() = 5
  println("Setting y to 7")
  y() = 7
  println("area = " + area())
}