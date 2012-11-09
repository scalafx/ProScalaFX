package proscalafx.ch04.reversi.examples

import javafx.geometry.VPos
import javafx.scene.text.FontWeight
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.stage.Stage

object CenterUsingBind extends JFXApp {
  val text = new Text("ScalaFX Reversi") {
    textOrigin = VPos.TOP
    font = Font.font(null, FontWeight.BOLD, 18)
  }

  stage = new Stage {
    scene = new Scene(400, 100) {
      content = text
    }
  }

  text.layoutX <== (stage.scene.width - text.prefWidth(-1)) / 2
  text.layoutY <== (stage.scene.height - text.prefHeight(-1)) / 2
}