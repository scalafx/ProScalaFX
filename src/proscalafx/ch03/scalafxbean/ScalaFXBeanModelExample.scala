package proscalafx.ch03.scalafxbean

import scalafx.beans.property.IntegerProperty
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty
import scalafx.scene.paint.Color

class ScalaFXBeanModelExample {

  val i = new IntegerProperty(this, "i", 0)

  def i_=(value: Int) {
    i() = value
  }

  val str = new StringProperty(this, "str", "Hello")

  def str_=(value: String) {
    str() = value
  }

  val color = new ObjectProperty[Color](this, "color", Color.BLACK)

  def color_=(value: Color) {
    color() = value
  }

}