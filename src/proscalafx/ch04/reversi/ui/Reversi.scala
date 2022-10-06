package proscalafx.ch04.reversi.ui

import proscalafx.ch04.reversi.model.{Black, Owner, ReversiModel, White}
import scalafx.Includes.*
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.effect.{DropShadow, InnerShadow}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.{Font, FontWeight, Text}

class Reversi {

  private val model = ReversiModel

  val restart: Button = new Button() {
    text = "Restart"
    onAction = () => ReversiModel.restart()
  }

  val game: BorderPane = new BorderPane() {
    top = createTitle()
    center = new StackPane() {
      children = List(
        createBackground(),
        tiles()
      )
    }
    bottom = createScoreBoxes()
  }

  private def createTitle() = new TilePane {
    snapToPixel = false
    children = List(
      new StackPane {
        style = "-fx-background-color: black"
        children = new Text {
          text = "ScalaFX"
          font = Font.font(null, FontWeight.Bold, 18)
          fill = Color.White
          alignmentInParent = Pos.CenterRight
        }
      },
      new Text {
        text = "Reversi"
        font = Font.font(null, FontWeight.Bold, 18)
        alignmentInParent = Pos.CenterLeft
      }
    )
    prefTileHeight = 40
    prefTileWidth <== parent.selectDouble("width") / 2
  }

  private def createBackground() = new Region {
    style = "-fx-background-color: radial-gradient(radius 100%, white, gray)"
  }

  // Code commended below was only used in the first edition of the book
  //  private def createTiles(): GridPane = {
  //    val board = new GridPane
  //    for {
  //      i <- 0 until ReversiModel.BOARD_SIZE
  //      j <- 0 until ReversiModel.BOARD_SIZE
  //    } {
  //      val square = new ReversiSquare(i, j)
  //      val piece = new ReversiPiece()
  //      piece.owner <== model.board(i)(j)
  //      board.add(new StackPane {
  //        children = List(square, piece)
  //      }, i, j)
  //    }
  //
  //    if (Platform.isSupported(ConditionalFeature.Scene3D)) {
  //      val scale = new Scale(.45, .8, 1, 300, 60, 0)
  //      val translate = new Translate(75, -2, -150)
  //      val xRot = new Rotate {
  //        angle = -40
  //        pivotX = 300
  //        pivotY = 150
  //        pivotZ = 0
  //        axis = Rotate.XAxis
  //      }
  //      val yRot = new Rotate {
  //        angle = -5
  //        pivotX = 300
  //        pivotY = 150
  //        pivotZ = 0
  //        axis = Rotate.YAxis
  //      }
  //      val zRot = new Rotate {
  //        angle = -6
  //        pivotX = 300
  //        pivotY = 150
  //        pivotZ = 0
  //        axis = Rotate.ZAxis
  //      }
  //
  //      board.transforms ++= List(scale, translate, xRot, yRot, zRot)
  //    }
  //
  //    board
  //  }

  private def tiles(): Node = {
    val board = new GridPane()
    for (i <- 0 until ReversiModel.BOARD_SIZE; j <- 0 until ReversiModel.BOARD_SIZE) {
      val square = new ReversiSquare(i, j)
      val piece  = new ReversiPiece()
      piece.owner <== model.board(i)(j)
      board.add(
        new StackPane {
          children = Seq(square, piece)
        },
        i,
        j
      )

    }
    board
  }

  private def createScoreBoxes() = new TilePane() {
    snapToPixel = false
    prefColumns = 2
    children = List(
      createScore(Black),
      createScore(White)
    )
    prefTileWidth <== parent.selectDouble("width") / 2
  }

  private def createScore(owner: Owner): StackPane = {

    val innerShadow = new InnerShadow() {
      color = Color.DodgerBlue
      choke = 0.5
    }
    val noInnerShadow = null.asInstanceOf[javafx.scene.effect.InnerShadow]

    val backgroundRegion = new Region() {
      style = "-fx-background-color: " + owner.opposite.colorStyle
      effect <== when(ReversiModel.turn === owner) choose innerShadow otherwise noInnerShadow
    }

    val dropShadow = new DropShadow() {
      color = Color.DodgerBlue
      spread = 0.2
    }
    val noDropShadow = null.asInstanceOf[javafx.scene.effect.DropShadow]

    val piece = new Ellipse() {
      radiusX = 32
      radiusY = 20
      fill = owner.color
      effect <== when(ReversiModel.turn === owner) choose dropShadow otherwise noDropShadow
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
