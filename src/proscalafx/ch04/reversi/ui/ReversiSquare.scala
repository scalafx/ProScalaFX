package proscalafx.ch04.reversi.ui

import proscalafx.ch04.reversi.model.ReversiModel
import scalafx.Includes._
import scalafx.animation.FadeTransition
import scalafx.beans.property.StringProperty
import scalafx.scene.layout.Region
import scalafx.util.Duration
import scalafx.scene.effect.Light
import scalafx.scene.effect.Lighting
import javafx.scene.input.MouseEvent

class ReversiSquare(val x: Int, val y: Int) extends Region {

  private val highlight = new Region {
    opacity = 0
    style = "-fx-border-width: 3; -fx-border-color: dodgerblue"
  }

  private val highlightTransition = new FadeTransition {
    node = highlight
    duration = Duration(200)
    fromValue = 0
    toValue = 1
  }

  /*
    getChildren().add(highlight);
    addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent t) {
        if (model.legalMove(x, y).get()) {
          highlightTransition.setRate(1);
          highlightTransition.play();
        }
      }
    });   
    */

  style <== when(ReversiModel.legalMove(x, y)) then
    "-fx-background-color: derive(dodgerblue, -60%)" otherwise
    "-fx-background-color: burlywood"

  effect = new Lighting {
    light = new Light.Distant {
      azimuth = -135
      elevation = 30
    }
  }

  prefHeight = 200
  prefWidth = 200


}