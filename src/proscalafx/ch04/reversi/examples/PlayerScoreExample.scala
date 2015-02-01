package proscalafx.ch04.reversi.examples

import proscalafx.ch04.reversi.model.{Black, Owner, ReversiModel, White}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.effect.{DropShadow, InnerShadow}
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{Font, FontWeight, Text}


object PlayerScoreExample extends JFXApp {

  val tiles = new TilePane() {
    snapToPixel = false
    children = List(
      createScore(Black),
      createScore(White)
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
      color = Color.DodgerBlue
      choke = 0.5
    }

    val backgroundRegion = new Region() {
      style = "-fx-background-color: " + owner.opposite.colorStyle
      if (Black == owner) {
        effect = innerShadow
      }
    }

    val dropShadow = new DropShadow() {
      color = Color.DodgerBlue
      spread = 0.2
    }

    val piece = new Ellipse() {
      radiusX = 32
      radiusY = 20
      fill = owner.color
      if (Black == owner) {
        effect = dropShadow
      }
    }

    val score = new Text() {
      font = Font.font(null, FontWeight.Bold, 100)
      fill = owner.color
      text <== ReversiModel.score(owner).asString()
    }

    val remaining = new Text() {
      font = Font.font(null, FontWeight.Bold, 12)
      fill = owner.color
      text <== ReversiModel.turnsRemaining(owner).asString() + " turns remaining"
    }

    new StackPane() {
      children = List(
        backgroundRegion,
        new FlowPane() {
          hgap = 20
          vgap = 10
          alignment = Pos.Center
          children = List(
            score,
            new VBox() {
              alignment = Pos.Center
              spacing = 10
              children = List(
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
