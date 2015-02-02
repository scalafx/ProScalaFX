package proscalafx.ch04.reversi.ui

import proscalafx.ch04.reversi.model.NONE
import proscalafx.ch04.reversi.model.Owner
import proscalafx.ch04.reversi.model.White
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.effect.Reflection
import scalafx.scene.layout.Region

object ReversiPiece {

  private val nonePieceStyle = "radius: 0; "
  private val whitePieceStyle = "-fx-background-color: radial-gradient(radius 100%, white .4, gray .9, darkgray 1); "
  private val blackPieceStyle = "-fx-background-color: radial-gradient(radius 100%, white .0, black .6); "
  private val tileStyle = "-fx-background-radius: 1000em; -fx-background-insets: 5"

}

class ReversiPiece(_owner: Owner = NONE) extends Region {

  import ReversiPiece._

  val owner = new ObjectProperty[Owner](this, "owner", _owner)

  def owner_=(value: Owner) {
    owner() = value
  }


  style <== (
    when(owner === NONE) choose nonePieceStyle otherwise
      (when(owner === White) choose whitePieceStyle otherwise blackPieceStyle)
    ) + tileStyle

  effect = new Reflection {
    fraction = 1.0
    topOffset <== height * (-0.75)
  }

  prefWidth = 180
  prefHeight = 180
  mouseTransparent = true
}