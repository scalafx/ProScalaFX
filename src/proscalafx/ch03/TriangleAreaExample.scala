package proscalafx.ch03

import scalafx.beans.binding.NumberBinding
import scalafx.beans.property.IntegerProperty

object TriangleAreaExample extends App {

  def printResult(x1: IntegerProperty, y1: IntegerProperty,
                  x2: IntegerProperty, y2: IntegerProperty,
                  x3: IntegerProperty, y3: IntegerProperty,
                  area: NumberBinding) {
    println("For A(%d,%d), B(%d,%d), C(%d,%d), the area of triangle ABC is %1.1f".format(
      x1(), y1(), x2(), y2(), x3(), y3(), area()))
  }

  val x1 = IntegerProperty(0)
  val y1 = IntegerProperty(0)
  val x2 = IntegerProperty(0)
  val y2 = IntegerProperty(0)
  val x3 = IntegerProperty(0)
  val y3 = IntegerProperty(0)

  val x1y2 = x1 * y2
  val x2y3 = x2 * y3
  val x3y1 = x3 * y1
  val x1y3 = x1 * y3
  val x2y1 = x2 * y1
  val x3y2 = x3 * y2

  val sum1 = x1y2 + x2y3
  val sum2 = sum1 + x3y1
  val sum3 = sum2 + x3y1
  val diff1 = sum3 - x1y3
  val diff2 = diff1 - x2y1
  val determinant = diff2 - x3y2
  val area = determinant / 2.0D

  x1() = 0
  y1() = 0
  x2() = 6
  y2() = 0
  x3() = 4
  y3() = 3

  printResult(x1, y1, x2, y2, x3, y3, area)

  x1() = 1
  y1() = 0
  x2() = 2
  y2() = 2
  x3() = 0
  y3() = 1

  printResult(x1, y1, x2, y2, x3, y3, area)
}