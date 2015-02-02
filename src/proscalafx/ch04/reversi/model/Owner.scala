package proscalafx.ch04.reversi.model

import scalafx.scene.paint.Color

sealed case class Owner(color: Color, colorStyle: String) {

  def opposite: Owner = this match {
    case White => Black
    case Black => White
    case _ => NONE
  }

}

object NONE extends Owner(Color.Transparent, "")

object White extends Owner(Color.White, "white")

object Black extends Owner(Color.Black, "black")

