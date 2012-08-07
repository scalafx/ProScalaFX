package proscalafx.ch04.reversi.examples

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.binding.NumberBinding.sfxNumberBinding2jfx
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.TilePane
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.scene.Scene
import scalafx.stage.Stage

object AlignUsingStackAndTile extends JFXApp {
  val left = new StackPane {
    style = "-fx-background-color: black"
    content = new Text {
      text = "JavaFX"
      font = Font.font(null, FontWeight.BOLD, 18)
      fill = Color.WHITE
      alignment = Pos.CENTER_RIGHT
    }
  }

  stage = new Stage {
    height = 100
    width = 400
    scene = new Scene {
      content = new TilePane {
        snapToPixel = false
        content = List(left, new Text {
          text = "Reversi"
          font = Font.font(null, FontWeight.BOLD, 18)
          alignment = Pos.CENTER_LEFT
        })
      }
    }
  }

  left.prefWidth <== stage.scene.width / 2
  left.prefHeight <== stage.scene.height

}