package proscalafx.ch04.reversi.ui

import javafx.geometry.HPos
import javafx.geometry.VPos
import javafx.scene.input.MouseEvent
import javafx.scene.{layout => jfxsl}
import proscalafx.ch04.reversi.model.ReversiModel
import scalafx.Includes._
import scalafx.animation.FadeTransition
import scalafx.scene.effect.Light
import scalafx.scene.effect.Lighting
import scalafx.scene.layout.Region
import scalafx.util.Duration

class ReversiSquare(val x: Int, val y: Int) extends Region {

  private val highlight = new Region {
    opacity = 0
    style = "-fx-border-width: 3; -fx-border-color: dodgerblue"
  }

  override val delegate: jfxsl.Region = new jfxsl.Region {
    getChildren.add(highlight)

    protected override def layoutChildren() {
      layoutInArea(highlight, 0, 0, getWidth, getHeight, getBaselineOffset, HPos.CENTER, VPos.CENTER)
    }
  }

  private val highlightTransition = new FadeTransition {
    node = highlight
    duration = Duration(200)
    fromValue = 0
    toValue = 1
  }

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

  onMouseEntered = (e: MouseEvent) => {
    if (ReversiModel.legalMove(x, y).get) {
      highlightTransition.rate() = 1
      highlightTransition.play
    }
  }

  onMouseExited = (e: MouseEvent) => {
    highlightTransition.rate = -1
    highlightTransition.play
  }

  onMouseClicked = (e: MouseEvent) => {
    ReversiModel.play(x, y)
    highlightTransition.rate() = -1
    highlightTransition.play
  }
}