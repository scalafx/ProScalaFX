package proscalafx.ch04.reversi.examples

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{Font, FontWeight, Text}

object CenterUsingStack extends JFXApp3 {

  override def start(): Unit = {

    val ellipse = new Ellipse

    stage = new PrimaryStage {
      scene = new Scene(400, 100) {
        content = new StackPane {
          children = List(
            ellipse,
            new Text("ScalaFX Reversi") {
              font = Font.font(null, FontWeight.Bold, 18)
              fill = Color.White
            }
          )
        }
      }
    }

    ellipse.radiusX <== stage.scene().width / 2
    ellipse.radiusY <== stage.scene().height / 2
  }
}