package proscalafx.ch03

import scalafx.beans.binding.Bindings
import scalafx.beans.property.IntegerProperty

object TriangleAreaFluentExample extends App {
  val x1 = IntegerProperty(0)
  val y1 = IntegerProperty(0)
  val x2 = IntegerProperty(0)
  val y2 = IntegerProperty(0)
  val x3 = IntegerProperty(0)
  val y3 = IntegerProperty(0)

  val area = ((x1 * y2) + (x2 * y3) + (x3 * y1) - (x1 * y3) - (x2 * y1) - (x3 * y2)) / 2.0

  val output = Bindings.createStringBinding(
    () =>
      f"For A(${x1()},${y1()}), B(${x2()},${y2()}), C(${x3()},${y3()}), the area of triangle ABC is ${area().doubleValue()}%.1f",
    x1,
    y1,
    x2,
    y2,
    x3,
    y3,
    area
  )

  x1() = 0
  y1() = 0
  x2() = 6
  y2() = 0
  x3() = 4
  y3() = 3

  println(output())

  x1() = 1
  y1() = 0
  x2() = 2
  y2() = 2
  x3() = 0
  y3() = 1

  println(output())
}
