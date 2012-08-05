package proscalafx.ch04.reversi.ui

import javafx.application.ConditionalFeature
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import proscalafx.ch04.reversi.model.ReversiModel
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.TilePane
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.scene.transform.Rotate
import scalafx.scene.transform.Scale
import scalafx.scene.transform.Translate
import scalafx.scene.Node

object Reversi extends JFXApp {

  /*
  private Node restart() {
    return ButtonBuilder.create().text("Restart").onAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent t) {
        model.restart();
      }
    }).build();
  }   * 
   */

  private def restart: Node = new Button {
    text = "Restart"
    onAction = { event: ActionEvent =>
      ReversiModel.restart
    }
  }

  private def createTitle: Node = {
    val left = new StackPane {
      style = "-fx-background-color: black"
    }
    val text = new Text {
      text = "JavaFX"
      font = Font.font(null, FontWeight.BOLD, 18)
      fill = Color.WHITE
    }
    StackPane.setAlignment(text, Pos.CENTER_RIGHT)
    left.children += text

    val right = new Text {
      text = "Reversi"
      font = Font.font(null, FontWeight.BOLD, 18)
    }
    TilePane.setAlignment(right, Pos.CENTER_LEFT)

    val tiles = new TilePane {
      content = List(left, right)
      prefTileHeight = 40
      snapToPixel = false
      //      tileWidth <== parent.
      // tiles.prefTileWidthProperty().bind(Bindings.selectDouble(tiles.parentProperty(), "width").divide(2));
    }

    tiles
  }

  private def createBackground: Node = new Region {
    style = "-fx-background-color: radial-gradient(radius 100%, white, gray)"
  }

  /*
    GridPane board = new GridPane();
    for (int i = 0; i < ReversiModel.BOARD_SIZE; i++) {
      for (int j = 0; j < ReversiModel.BOARD_SIZE; j++) {
        ReversiSquare square = new ReversiSquare(i, j);
        ReversiPiece piece = new ReversiPiece();
        piece.ownerProperty().bind(model.board[i][j]);
        board.add(StackPaneBuilder.create().children(
          square,
          piece
        ).build(), i, j);
      }
    }
    if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
      Transform scale = new Scale(.45, .8, 1, 300, 60, 0);
      Transform translate = new Translate(75, -2, -150);
      Transform xRot = new Rotate(-40, 300, 150, 0, Rotate.X_AXIS);
      Transform yRot = new Rotate(-5, 300, 150, 0, Rotate.Y_AXIS);
      Transform zRot = new Rotate(-6, 300, 150, 0, Rotate.Z_AXIS);
      board.getTransforms().addAll(scale, translate, xRot, yRot, zRot);
    }
    return board;
   * 
   */
  private def tiles: Node = {
    val board = new GridPane
    for {
      i <- 0 until ReversiModel.BOARD_SIZE
      j <- 0 until ReversiModel.BOARD_SIZE
    } {
      val square = new ReversiSquare(i, j)
      val piece = new ReversiPiece(null)
      piece.owner <== ReversiModel.board(i)(j)
      board.add(new StackPane {
        content = List(square, piece)
      }, i, j)
    }

    if (Platform.isSupported(ConditionalFeature.SCENE3D)) {
      val scale = new Scale(.45, .8, 1, 300, 60, 0)
      val translate = new Translate(75, -2, -150)
      /*
      val xRot = new Rotate {
        angle = -40
        pivotX = 300
        pivotY = 150
        pivotZ = 0
        axis = Rotate.XAxis
      }
      val yRot = new Rotate(-5, 300, 150, 0, Rotate.Y_AXIS)
      val zRot = new Rotate(-6, 300, 150, 0, Rotate.Z_AXIS)
      */
    }

    board
  }

}