package proscalafx.ch04.reversi.examples

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.{StackPane, TilePane}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, Text}

object AlignUsingStackAndTile extends JFXApp3 {

  override def start(): Unit = {

    val left = new StackPane {
      style = "-fx-background-color: black"
      children = new Text {
        text = "ScalaFX"
        font = Font.font(null, FontWeight.Bold, 18)
        fill = Color.White
        alignmentInParent = Pos.CenterRight
      }
    }

    stage = new PrimaryStage {
      scene = new Scene(400, 100) {
        content = new TilePane {
          snapToPixel = false
          children = List(
            left,
            new Text {
              text = "Reversi"
              font = Font.font(null, FontWeight.Bold, 18)
              alignmentInParent = Pos.CenterLeft
            }
          )
        }
      }
    }

    left.prefWidth <== stage.scene().width / 2
    left.prefHeight <== stage.scene().height
  }
}
