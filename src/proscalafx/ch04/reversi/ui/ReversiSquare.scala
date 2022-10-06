package proscalafx.ch04.reversi.ui

import javafx.scene.layout as jfxsl
import proscalafx.ch04.reversi.model.ReversiModel
import scalafx.Includes.*
import scalafx.animation.FadeTransition
import scalafx.geometry.{HPos, VPos}
import scalafx.scene.effect.{Light, Lighting}
import scalafx.scene.layout.Region
import scalafx.util.Duration

class ReversiSquare(val x: Int, val y: Int) extends Region {

  private val highlight = new Region {
    opacity = 0
    style = "-fx-border-width: 3; -fx-border-color: dodgerblue"
  }

  override val delegate: jfxsl.Region = new jfxsl.Region {
    getChildren.add(highlight)

    protected override def layoutChildren(): Unit = {
      layoutInArea(highlight, 0, 0, getWidth, getHeight, getBaselineOffset, HPos.Center, VPos.Center)
    }
  }

  private val highlightTransition = new FadeTransition {
    node = highlight
    duration = Duration(200)
    fromValue = 0
    toValue = 1
  }

  style <== when(ReversiModel.legalMove(x, y)) choose
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

  onMouseEntered = () => {
    if (ReversiModel.legalMove(x, y).get) {
      highlightTransition.rate() = 1
      highlightTransition.play()
    }
  }

  onMouseExited = () => {
    highlightTransition.rate = -1
    highlightTransition.play()
  }

  onMouseClicked = () => {
    ReversiModel.play(x, y)
    highlightTransition.rate() = -1
    highlightTransition.play()
  }
}
