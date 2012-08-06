package proscalafx.ch04.reversi.reversipieces

import proscalafx.ch04.reversi.ui.ReversiSquare
import scalafx.application.JFXApp
import scalafx.scene.layout.StackPane
import scalafx.scene.Scene
import scalafx.stage.Stage

object ReversiSquareTest extends JFXApp {

  stage = new Stage {
    scene = new Scene {
      content = new StackPane {
        /*
         * IMPLEMENTATION NOTE: Unlike what happen in JavaFX Example, ReversiSquare is not growing along with 
         * Scene / Stage. :-( 
         */
        content = new ReversiSquare(0, 0)
      }
    }
  }

}