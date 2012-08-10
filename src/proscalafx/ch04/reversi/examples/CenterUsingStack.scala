package proscalafx.ch04.reversi.examples

import javafx.scene.text.FontWeight
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.binding.NumberBinding.sfxNumberBinding2jfx
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.scene.Scene
import scalafx.stage.Stage

object CenterUsingStack extends JFXApp {
  val ellipse = new Ellipse

  stage = new Stage {
    scene = new Scene(400, 100) {
      content = new StackPane {
        content = List(ellipse, new Text("JavaFX Reversi") {
          font = Font.font(null, FontWeight.BOLD, 18)
          fill = Color.WHITE
        })
      }
    }
  }

  ellipse.radiusX <== stage.scene.width / 2
  ellipse.radiusY <== stage.scene.height / 2
}