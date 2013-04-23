package proscalafx.ch04.reversi.model

import scalafx.scene.paint.Color

sealed case class Owner(color: Color, colorStyle: String) {

  def opposite: Owner = this match {
    case WHITE => BLACK
    case BLACK => WHITE
    case _ => NONE
  }

}

object NONE extends Owner(Color.TRANSPARENT, "")

object WHITE extends Owner(Color.WHITE, "white")

object BLACK extends Owner(Color.BLACK, "black")

