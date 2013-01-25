package proscalafx.ch04.reversi.examples

import proscalafx.ch04.reversi.model.{WHITE, BLACK, Owner, ReversiModel}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.effect.{InnerShadow, DropShadow}
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{FontWeight, Font, Text}


object PlayerScoreExample extends JFXApp {

  val tiles = new TilePane() {
    snapToPixel = false
    content = List(
      createScore(BLACK),
      createScore(WHITE)
    )
  }

  stage = new PrimaryStage() {
    scene = new Scene(600, 140) {
      content = tiles
    }
  }

  tiles.prefTileWidth <== stage.scene.width / 2
  tiles.prefTileHeight <== stage.scene.height


  //---------------------------------------------------------------------------


  private def createScore(owner: Owner): StackPane = {

    val innerShadow = new InnerShadow() {
      color = Color.DODGERBLUE
      choke = 0.5
    }

    val background = new Region() {
      style = "-fx-background-color: " + owner.opposite.colorStyle
      if (BLACK == owner) {
        effect = innerShadow
      }
    }

    val dropShadow = new DropShadow() {
      color = Color.DODGERBLUE
      spread = 0.2
    }

    val piece = new Ellipse() {
      radiusX = 32
      radiusY = 20
      fill = owner.color
      if (BLACK == owner) {
        effect = dropShadow
      }
    }

    val score = new Text() {
      font = Font.font(null, FontWeight.BOLD, 100)
      fill = owner.color
      text <== ReversiModel.score(owner).asString()
    }

    val remaining = new Text() {
      font = Font.font(null, FontWeight.BOLD, 12)
      fill = owner.color
      text <== ReversiModel.turnsRemaining(owner).asString() + " turns remaining"
    }

    new StackPane() {
      content = List(
        background,
        new FlowPane() {
          hgap = 20
          vgap = 10
          innerAlignment = Pos.CENTER
          content = List(
            score,
            new VBox() {
              innerAlignment = Pos.CENTER
              spacing = 10
              content = List(
                piece,
                remaining
              )
            }
          )
        }
      )
    }
  }
}
