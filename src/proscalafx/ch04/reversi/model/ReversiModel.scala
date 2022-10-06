package proscalafx.ch04.reversi.model

import scalafx.Includes.{when, *}
import scalafx.beans.Observable
import scalafx.beans.binding.*
import scalafx.beans.property.ObjectProperty

import scala.collection.mutable.ListBuffer

object ReversiModel {

  val BOARD_SIZE = 8

  val turn = ObjectProperty[Owner](Black)

  val board = Array.tabulate(BOARD_SIZE, BOARD_SIZE)((_, _) => ObjectProperty[Owner](NONE))

  initBoard()

  private def initBoard(): Unit = {
    val center1 = BOARD_SIZE / 2 - 1
    val center2 = BOARD_SIZE / 2
    board(center1)(center1)() = White
    board(center1)(center2)() = Black
    board(center2)(center1)() = Black
    board(center2)(center2)() = White
  }

  def restart(): Unit = {
    board.flatten.foreach(_() = NONE)

    initBoard()
    turn() = Black
  }

  def score(owner: Owner): NumberExpression = {
    board.flatten.map(p => when(p === owner) choose 1 otherwise 0).reduce(_ + _)
  }

  def turnsRemaining(owner: Owner): NumberBinding = {
    val emptyCellCount = score(NONE)

    when(turn === owner) choose ((emptyCellCount + 1) / 2) otherwise (emptyCellCount / 2)
  }

  def legalMove(x: Int, y: Int): BooleanBinding = {
    (board(x)(y) === NONE) && (
      canFlip(x, y, 0, -1, turn) ||
        canFlip(x, y, -1, -1, turn) ||
        canFlip(x, y, -1, 0, turn) ||
        canFlip(x, y, -1, 1, turn) ||
        canFlip(x, y, 0, 1, turn) ||
        canFlip(x, y, 1, 1, turn) ||
        canFlip(x, y, 1, 0, turn) ||
        canFlip(x, y, 1, -1, turn)
    )
  }

  private def canFlip(
    cellX: Int,
    cellY: Int,
    directionX: Int,
    directionY: Int,
    turn: ObjectProperty[Owner]
  ): BooleanBinding = {
    // Build list of dependencies for binding
    val dependencies = ListBuffer.empty[Observable]
    var x            = cellX + directionX
    var y            = cellY + directionY
    while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
      dependencies += board(x)(y)
      x += directionX
      y += directionY
    }
    dependencies += turn

    Bindings.createBooleanBinding(
      // Flip evaluation
      () => {
        val turnVal                 = turn.get
        var x                       = cellX + directionX
        var y                       = cellY + directionY
        var first                   = true
        var result: Option[Boolean] = None
        while (
          result.isEmpty &&
          x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board(x)(y).get != NONE
        ) {
          if (board(x)(y).get == turnVal) {
            result = Option(!first)
          }
          first = false
          x += directionX
          y += directionY
        }
        result.getOrElse(false)
      },
      dependencies.toSeq*
    )
  }

  def play(cellX: Int, cellY: Int): Unit = {
    if (legalMove(cellX, cellY).get) {
      board(cellX)(cellY)() = turn()
      flip(cellX, cellY, 0, -1, turn)
      flip(cellX, cellY, -1, -1, turn)
      flip(cellX, cellY, -1, 0, turn)
      flip(cellX, cellY, -1, 1, turn)
      flip(cellX, cellY, 0, 1, turn)
      flip(cellX, cellY, 1, 1, turn)
      flip(cellX, cellY, 1, 0, turn)
      flip(cellX, cellY, 1, -1, turn)
      turn.value = turn.value.opposite
    }
  }

  def flip(cellX: Int, cellY: Int, directionX: Int, directionY: Int, turn: ObjectProperty[Owner]): Unit = {
    if (canFlip(cellX, cellY, directionX, directionY, turn).get) {
      var x = cellX + directionX
      var y = cellY + directionY
      while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board(x)(y)() != turn()) {
        board(x)(y)() = turn()
        x += directionX
        y += directionY
      }
    }
  }
}
