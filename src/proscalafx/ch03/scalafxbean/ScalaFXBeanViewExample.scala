package proscalafx.ch03.scalafxbean

class ScalaFXBeanViewExample(val model: ScalaFXBeanModelExample) {

  model.i.onChange((_, oldValue, newValue) => {
    println("Property i changed: old value = " + oldValue + ", new value = " + newValue)
  })

  model.str.onChange((_, oldValue, newValue) => {
    println("Property str changed: old value = " + oldValue + ", new value = " + newValue)
  })

  model.color.onChange((_, oldValue, newValue) => {
    println("Property color changed: old value = " + oldValue + ", new value = " + newValue)
  })

}