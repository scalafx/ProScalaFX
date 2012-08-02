package proscalafx.ch03

import scalafx.beans.property.DoubleProperty
import scalafx.beans.binding.NumberBinding
import javafx.beans.{ binding => jfxbb }

object HeronsFormulaDirectExtensionExample extends App {
  val a = DoubleProperty(0.0)
  val b = DoubleProperty(0)
  val c = DoubleProperty(0)

  val area = new NumberBinding(new jfxbb.DoubleBinding {

    super.bind(a, b, c)

    override def computeValue = {
      val a0 = a.get
      val b0 = b.get
      val c0 = c.get

      if ((a0 + b0 > c0) && (b0 + c0 > a0) && (c0 + a0 > b0)) {
        val s = (a0 + b0 + c0) / 2.0D
        math.sqrt(s * (s - a0) * (s - b0) * (s - c0))
      } else {
        0
      }
    }
  })

  a() = 3
  b() = 4
  c() = 5
  /*
   * IMPLEMENTATION NOTE: Instead use area.get in printf, it was necessary
   * use area.value because get was throwing ClassCastException. Message was: 
   * "proscalafx.ch03.HeronsFormulaDirectExtensionExample$$anon$1 cannot be 
   * cast to javafx.beans.value.ObservableObjectValue"
   */
  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a.get, b.get, c.get,
    area.value)

  a() = 2
  b() = 2
  c() = 2
  printf("Given sides a = %1.0f, b = %1.0f, and c = %1.0f," +
    " the area of the triangle is %3.2f\n", a.get, b.get, c.get,
    area.value);

}