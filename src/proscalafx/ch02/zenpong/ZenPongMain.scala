package proscalafx.ch02.zenpong

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, Stop}

import scala.language.postfixOps

object ZenPongMain extends JFXApp3 {

  override def start(): Unit = {

    val zenPong = new ZenPong()

    stage = new PrimaryStage {
      title = "ZenPong Example"
      scene = new Scene(500, 500) {
        fill = LinearGradient(
          startX = 0.0,
          startY = 0.0,
          endX = 0.0,
          endY = 1.0,
          proportional = true,
          cycleMethod = CycleMethod.NoCycle,
          stops = List(Stop(0.0, Color.Black), Stop(0.0, Color.Gray))
        )
        content = zenPong.pongComponents
      }
    }

    zenPong.initialize()
  }
}
