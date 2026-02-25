package proscalafx.ch03.scalafxbean

@main def scalaFXBeanMainExample(): Unit =
  val model      = new ScalaFXBeanModelExample()
  val view       = new ScalaFXBeanViewExample(model)
  val controller = new ScalaFXBeanControllerExample(model, view)

  controller.incrementIPropertyOnModel()
  controller.changeStrPropertyOnModel()
  controller.switchColorPropertyOnModel()
  controller.incrementIPropertyOnModel()
  controller.changeStrPropertyOnModel()
  controller.switchColorPropertyOnModel()
