package proscalafx.ch03.scalafxbean

import scalafx.scene.paint.Color

class ScalaFXBeanControllerExample(model: ScalaFXBeanModelExample,
                                   view: ScalaFXBeanViewExample) {

  def incrementIPropertyOnModel() {
    model.i() = model.i() + 1
  }

  def changeStrPropertyOnModel() {
    val str = model.str()
    model.str() = if (str == "Hello") "World" else "Hello"
  }

  def switchColorPropertyOnModel() {
    val color = model.color()
    model.color() = if (color == Color.Black) Color.White else Color.Black
  }

}