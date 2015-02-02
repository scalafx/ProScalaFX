package proscalafx.ch03.scalafxbean

import scalafx.beans.value.ObservableValue
import scalafx.scene.paint.Color

class ScalaFXBeanViewExample(val model: ScalaFXBeanModelExample) {

  model.i.onChange((obs: ObservableValue[Int, Number], oldValue: Number, newValue: Number) => {
    println("Property i changed: old value = " + oldValue + ", new value = " + newValue)
  })

  model.str.onChange((obs: ObservableValue[String, String], oldValue: String, newValue: String) => {
    println("Property str changed: old value = " + oldValue + ", new value = " + newValue)
  })

  model.color.onChange((obs: ObservableValue[Color, Color], oldValue: Color, newValue: Color) => {
    println("Property color changed: old value = " + oldValue + ", new value = " + newValue)
  })

}