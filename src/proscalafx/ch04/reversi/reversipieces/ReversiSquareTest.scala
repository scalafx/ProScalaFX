package proscalafx.ch04.reversi.reversipieces

import proscalafx.ch04.reversi.ui.ReversiSquare
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.stage.Stage


object ReversiSquareTest extends JFXApp {

  // Unlike in many other examples, here content of the scene is assigned using `root` rather
  // than `content` properties.
  // `scalafx.scene.Scene` creates by default its root element as `Group`. If we assign using `content`, children
  // are added to that group. That may have undesired layout complications. In our case it will prevent automatic
  // resizing of the content.
  // To work around this, we assign to `root` directly.
  stage = new Stage {
    scene = new Scene() {
      root = new StackPane {
        content = new ReversiSquare(0, 0)
      }
    }
  }
}
