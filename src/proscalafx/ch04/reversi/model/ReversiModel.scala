package proscalafx.ch04.reversi.model

import javafx.beans.{ binding => jfxbb }
import scalafx.Includes.when
import scalafx.Includes._
import scalafx.beans.binding.BooleanBinding
import scalafx.beans.property.ObjectProperty.sfxObjectProperty2jfx
import scalafx.beans.property.BooleanProperty
import scalafx.beans.property.IntegerProperty
import scalafx.beans.property.ObjectProperty

object ReversiModel {

  val BOARD_SIZE = 8

  val turn = ObjectProperty[Owner](BLACK)

  val board = Array.tabulate(BOARD_SIZE, BOARD_SIZE)((_, _) => ObjectProperty[Owner](NONE))

  private def initBoard {
    val center1 = BOARD_SIZE / 2 - 1
    val center2 = BOARD_SIZE / 2;
    board(center1)(center1)() = WHITE
    board(center1)(center2)() = BLACK
    board(center2)(center1)() = BLACK
    board(center2)(center2)() = WHITE
  }

  def restart {
    for {
      i <- 0 until BOARD_SIZE
      j <- 0 until BOARD_SIZE
    } board(i)(j)() = NONE

    initBoard
    turn() = BLACK
  }

  def score(owner: Owner) = {
    val score = IntegerProperty(0)

    for {
      i <- 0 until BOARD_SIZE
      j <- 0 until BOARD_SIZE
    } score() = score() // + (when(board(i)(j) === owner) then 1 otherwise 0)

    score
  }

  def turnsRemaining(owner: Owner) = {
    val emptyCellCount = score(NONE)

    when(turn === owner) then ((emptyCellCount + 1) / 2) otherwise (emptyCellCount / 2)
  }

  /*
return new BooleanBinding() { 
      {
        bind(turn);
        int x = cellX + directionX;
        int y = cellY + directionY;
        while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
          bind(board[x][y]);
          x += directionX;
          y += directionY;
        }
      }
      @Override
      protected boolean computeValue() {
        Owner turnVal = turn.get();
        int x = cellX + directionX;
        int y = cellY + directionY;
        boolean first = true;
        while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y].get() != Owner.NONE) {
          if (board[x][y].get() == turnVal) {
            return !first;
          }
          first = false;
          x += directionX;
          y += directionY;
        }
        return false;
      }
    };   */

  private def canFlip(cellX: Int, cellY: Int, directionX: Int, directionY: Int, turn: ObjectProperty[Owner]) = {
    new BooleanBinding(new jfxbb.BooleanBinding {
      bind(turn)
      var x = cellX + directionX
      var y = cellY + directionY
      while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
        bind(board(x)(y))
        x += directionX
        y += directionY
      }

      override protected def computeValue = {

        /*
        val turnVal = turn.get
        var x = cellX + directionX
        var y = cellY + directionY
        var first = true
        
        var foundResult = false
        while ((x >= 0) && (x < BOARD_SIZE) && (y >= 0) && (y < BOARD_SIZE) && (board(x)(y).get != NONE) && !foundResult) {
          if (board(x)(y).get == turnVal) {
            return !first
          }
          first = false
          x += directionX
          y += directionY
        }
        */

        false
      }
    })
  }

  def play(cellX: Int, cellY: Int) {
    /*
if (legalMove(cellX, cellY).get()) {
      board[cellX][cellY].setValue(turn.get());
      flip(cellX, cellY, 0, -1, turn);
      flip(cellX, cellY, -1, -1, turn);
      flip(cellX, cellY, -1, 0, turn);
      flip(cellX, cellY, -1, 1, turn);
      flip(cellX, cellY, 0, 1, turn);
      flip(cellX, cellY, 1, 1, turn);
      flip(cellX, cellY, 1, 0, turn);
      flip(cellX, cellY, 1, -1, turn);
      turn.setValue(turn.getValue().opposite());
    }
         */

  }

  def flip(cellX: Int, cellY: Int, directionX: Int, directionY: Int, turn: ObjectProperty[Owner]) {
    /*
if (canFlip(cellX, cellY, directionX, directionY, turn).get()) {
      int x = cellX + directionX;
      int y = cellY + directionY;
      while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y].get() != turn.get()) {
        board[x][y].setValue(turn.get());
        x += directionX;
        y += directionY;
      }
    }
         */
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
      canFlip(x, y, 1, -1, turn))
  }

  initBoard

}