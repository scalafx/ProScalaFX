package proscalafx.ch04.reversi.reversipieces

import javafx.scene.layout.Priority
import proscalafx.ch04.reversi.model.BLACK
import proscalafx.ch04.reversi.model.WHITE
import proscalafx.ch04.reversi.ui.ReversiPiece
import proscalafx.ch04.reversi.ui.ReversiSquare
import scalafx.application.JFXApp
import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.scene.Node
import scalafx.scene.Scene
import scalafx.stage.Stage

object ReversiPieceTest extends JFXApp {

  val white: Node = new StackPane {
    content = List(new ReversiSquare(0, 0), new ReversiPiece(WHITE, false))
  }
  /*
   * IMPLEMENTATION NOTE: Unlike what happen in JavaFX Example, ReversiSquare is not growing along with
   * Scene / Stage. :-(
   */
  HBox.setHgrow(white, Priority.ALWAYS)

  val black: Node = new StackPane {
    content = List(new ReversiSquare(0, 0), new ReversiPiece(BLACK, false))
  /*
   * IMPLEMENTATION NOTE: Unlike what happen in JavaFX Example, ReversiSquare is not growing along with
   * Scene / Stage. :-(
   */
    hgrow = Priority.ALWAYS
  }

  stage = new Stage {
    scene = new Scene {
      content = new HBox {
        snapToPixel = false
        content = List(white, black)
      }
    }
  }
}