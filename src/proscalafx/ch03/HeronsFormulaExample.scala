package proscalafx.ch03

import scalafx.Includes._
import scalafx.beans.property.DoubleProperty

object HeronsFormulaExample extends App {
  val a = DoubleProperty(0.0)
  val b = DoubleProperty(0)
  val c = DoubleProperty(0)

  val s = (a + b + c) / 2.0

  val areaSquared =
    when(((a + b) > c) && ((b + c) > a) && ((a + c) > b)) choose (s * (s - a) * (s - b) * (s - c)) otherwise 0.0

  a() = 3
  b() = 4
  c() = 5

  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a(), b(), c(),
    math.sqrt(areaSquared.get))

  a() = 2
  b() = 2
  c() = 2

  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a(), b(), c(),
    math.sqrt(areaSquared.get))
}