package proscalafx.ch04.reversi.reversipieces


import proscalafx.ch04.reversi.model.Black
import proscalafx.ch04.reversi.model.White
import proscalafx.ch04.reversi.ui.ReversiPiece
import proscalafx.ch04.reversi.ui.ReversiSquare
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.{Priority, HBox, StackPane}

object ReversiPieceTest extends JFXApp {

  // Unlike in many other examples, here content of the scene is assigned using `root` rather
  // than `content` properties.
  // `scalafx.scene.Scene` creates by default its root element as `Group`. If we assign using `content`, children
  // are added to that group. That may have undesired layout complications. In our case it will prevent automatic
  // resizing of the content.
  // To work around this, we assign to `root` directly.
  stage = new PrimaryStage() {
    scene = new Scene() {
      root = new HBox {
        snapToPixel = false
        content = List(
          new StackPane {
            content = List(
              new ReversiSquare(0, 0),
              new ReversiPiece(White)
            )
            hgrow = Priority.ALWAYS
          },
          new StackPane {
            content = List(
              new ReversiSquare(0, 0),
              new ReversiPiece(Black)
            )
            hgrow = Priority.ALWAYS
          }
        )
      }
    }
  }
}