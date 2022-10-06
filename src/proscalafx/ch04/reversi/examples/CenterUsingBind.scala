package proscalafx.ch04.reversi.examples

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.VPos
import scalafx.scene.Scene
import scalafx.scene.text.{Font, FontWeight, Text}

object CenterUsingBind extends JFXApp3 {

  override def start(): Unit = {

    val text = new Text("ScalaFX Reversi") {
      textOrigin = VPos.Top
      font = Font.font(null, FontWeight.Bold, 18)
    }

    stage = new PrimaryStage {
      scene = new Scene(400, 100) {
        content = text
      }
    }

    text.layoutX <== (stage.scene().width - text.prefWidth(-1)) / 2
    text.layoutY <== (stage.scene().height - text.prefHeight(-1)) / 2
  }
}
