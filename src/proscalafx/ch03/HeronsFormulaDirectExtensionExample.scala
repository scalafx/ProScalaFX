package proscalafx.ch03

import javafx.beans.{binding => jfxbb}
import scalafx.beans.binding.NumberBinding
import scalafx.beans.property.DoubleProperty

object HeronsFormulaDirectExtensionExample extends App {
  val a = DoubleProperty(0)
  val b = DoubleProperty(0)
  val c = DoubleProperty(0)

  val area = new NumberBinding(new jfxbb.DoubleBinding {

    super.bind(a, b, c)

    override def computeValue: Double = {
      val a0 = a()
      val b0 = b()
      val c0 = c()

      if ((a0 + b0 > c0) && (b0 + c0 > a0) && (c0 + a0 > b0)) {
        val s = (a0 + b0 + c0) / 2.0D
        math.sqrt(s * (s - a0) * (s - b0) * (s - c0))
      } else {
        0
      }
    }
  })

  // Use braces "()" to access values hold by properties, bindings, like `area`, and numeric expressions.
  a() = 3
  b() = 4
  c() = 5
  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a(), b(), c(), area())

  a() = 2
  b() = 2
  c() = 2
  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a(), b(), c(), area())

}