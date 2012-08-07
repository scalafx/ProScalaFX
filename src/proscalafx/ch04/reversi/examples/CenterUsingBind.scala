package proscalafx.ch04.reversi.examples

import javafx.geometry.VPos
import javafx.scene.text.FontWeight
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.binding.NumberBinding.sfxNumberBinding2jfx
import scalafx.scene.text.Text.sfxText2jfx
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.scene.Group
import scalafx.scene.Scene
import scalafx.stage.Stage

object CenterUsingBind extends JFXApp {
  val text = new Text("JavaFX Reversi") {
    textOrigin = VPos.TOP
    font = Font.font(null, FontWeight.BOLD, 18)
  }

  stage = new Stage {
    width = 400
    height = 100
    scene = new Scene {
      content = new Group(text)
    }
  }

  text.layoutX <== (stage.scene.width - text.prefWidth(-1)) / 2
  text.layoutY <== (stage.scene.height - text.prefWidth(-1)) / 2
}