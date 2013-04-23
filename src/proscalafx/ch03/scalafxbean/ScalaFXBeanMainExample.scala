package proscalafx.ch03.scalafxbean

object ScalaFXBeanMainExample extends App {
  val model = new ScalaFXBeanModelExample()
  val view = new ScalaFXBeanViewExample(model)
  val controller = new ScalaFXBeanControllerExample(model, view)

  controller.incrementIPropertyOnModel()
  controller.changeStrPropertyOnModel()
  controller.switchColorPropertyOnModel()
  controller.incrementIPropertyOnModel()
  controller.changeStrPropertyOnModel()
  controller.switchColorPropertyOnModel()

}