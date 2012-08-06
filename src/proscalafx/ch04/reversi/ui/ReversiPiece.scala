package proscalafx.ch04.reversi.ui

import proscalafx.ch04.reversi.model.NONE
import proscalafx.ch04.reversi.model.Owner
import proscalafx.ch04.reversi.model.WHITE
import scalafx.Includes._
import scalafx.beans.binding.NumberBinding.sfxNumberBinding2jfx
import scalafx.beans.property.ObjectProperty
import scalafx.scene.effect.Reflection
import scalafx.scene.layout.Region

object ReversiPiece {

  private[ReversiPiece] val noneBackground = "radius 0"
  private[ReversiPiece] val whiteBackground = "-fx-background-color: radial-gradient(radius 100%, white .4, gray .9, darkgray 1)"
  private[ReversiPiece] val blackBackground = "-fx-background-color: radial-gradient(radius 100%, white .0, black .6)"
  private[ReversiPiece] val generalBackground = "; -fx-background-radius: 1000em; -fx-background-insets: 5"

}

/*
 * IMPLEMENTATION NOTE: Default added argument for mouseTransparent for use in example application.
 */
class ReversiPiece(_owner: Owner, mousetransparent: Boolean = true) extends Region {

  import ReversiPiece._

  val owner = new ObjectProperty[Owner](this, "owner", _owner)
  def owner_=(value: Owner) {
    owner() = value
  }

  style <== when(owner === NONE) then noneBackground otherwise
    (when(owner === WHITE) then whiteBackground otherwise blackBackground) +
    generalBackground

  effect = new Reflection {
    fraction = 1.0
    topOffset <== height * (-0.75)
  }

  prefWidth = 180
  prefHeight = 180
  mouseTransparent = mousetransparent
}