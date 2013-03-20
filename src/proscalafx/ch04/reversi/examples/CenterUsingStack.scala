package proscalafx.ch04.reversi.examples

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{FontWeight, Font, Text}

object CenterUsingStack extends JFXApp {
  val ellipse = new Ellipse

  stage = new PrimaryStage {
    scene = new Scene(400, 100) {
      content = new StackPane {
        content = List(
          ellipse,
          new Text("ScalaFX Reversi") {
            font = Font.font(null, FontWeight.BOLD, 18)
            fill = Color.WHITE
          }
        )
      }
    }
  }

  ellipse.radiusX <== stage.scene.width / 2
  ellipse.radiusY <== stage.scene.height / 2
}