package proscalafx.ch04.reversi.examples

import proscalafx.ch04.reversi.model.{Black, Owner, ReversiModel, White}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.{Node, Scene}
import scalafx.scene.effect.{DropShadow, InnerShadow}
import scalafx.scene.layout.{BorderPane, FlowPane, Region, StackPane, TilePane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{Font, FontWeight, Text}


object BorderLayoutExample extends JFXApp {

  val borderPane = new BorderPane {
    top = createTitle
    center = createBackground
    bottom = createScoreBoxes
  }

  stage = new PrimaryStage {
    scene = new Scene(600, 400) {
      // Assign borderPane directly to `root` to avoid layout issues.
      // If assigned to `content` there will be `Group` node at root that interferes with automatic rescaling.
      root = borderPane
    }
  }


  //---------------------------------------------------------------------------


  private def createTitle = new TilePane {
    snapToPixel = false
    children = List(
      new StackPane {
        style = "-fx-background-color: black"
        children = new Text("ScalaFX") {
          font = Font.font(null, FontWeight.Bold, 18)
          fill = Color.White
          alignmentInParent = Pos.CenterRight
        }
      },
      new Text("Reversi") {
        font = Font.font(null, FontWeight.Bold, 18)
        alignmentInParent = Pos.CenterLeft
      })
    prefTileHeight = 40
    prefTileWidth <== parent.selectDouble("width") / 2
  }


  private def createBackground = new Region {
    style = "-fx-background-color: radial-gradient(radius 100%, white, gray)"
  }


  private def createScoreBoxes = new TilePane {
    snapToPixel = false
    prefColumns = 2
    children = List(
      createScore(Black),
      createScore(White)
    )
    prefTileWidth <== parent.selectDouble("width") / 2
  }


  private def createScore(owner: Owner): Node = {

    val innerShadow = new InnerShadow() {
      color = Color.DodgerBlue
      choke = 0.5
    }
    val backgroundRegion = new Region {
      style = "-fx-background-color: " + owner.opposite.colorStyle
      if (Black == owner) {
        effect = innerShadow
      }
    }

    val dropShadow = new DropShadow() {
      color = Color.DodgerBlue
      spread = 0.2
    }

    val piece = new Ellipse {
      radiusX = 32
      radiusY = 20
      fill = owner.color
      if (Black == owner) {
        effect = dropShadow
      }
    }

    val score = new Text {
      font = Font.font(null, FontWeight.Bold, 100)
      fill = owner.color
      text <== ReversiModel.score(owner).asString
    }

    val remaining = new Text {
      font = Font.font(null, FontWeight.Bold, 12)
      fill = owner.color
      text <== ReversiModel.turnsRemaining(owner).asString() + " turns remaining"
    }

    new StackPane {
      children = List(
        backgroundRegion,
        new FlowPane {
          hgap = 20
          vgap = 10
          alignment = Pos.Center
          children = List(
            score,
            new VBox {
              alignment = Pos.Center
              spacing = 10
              children = List(
                piece,
                remaining)
            }
          )
        }
      )
    }
  }

}