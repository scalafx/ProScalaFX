package proscalafx.ch02.zenpong

import javafx.animation.Animation.Status

import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.{BooleanProperty, DoubleProperty}
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, Stop}
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.{Cursor, Group, Scene}

object ZenPongMain extends JFXApp {

  /** The center points of the moving ball */
  val centerBallX = new DoubleProperty
  val centerBallY = new DoubleProperty

  /** The Y coordinate of the left paddle */
  val leftPaddleY = new DoubleProperty

  /** The Y coordinate of the right paddle */
  val rightPaddleY = new DoubleProperty

  /** The drag anchor for left and right paddles */
  var leftPaddleDragAnchorY: Double = _
  var rightPaddleDragAnchorY: Double = _

  /** Controls whether the ball is moving right */
  var movingRight = true

  /** Controls whether the ball is moving down */
  var movingDown = true

  /** The initial translateY property for the left and right paddles */
  var initLeftPaddleTranslateY: Double = _
  var initRightPaddleTranslateY: Double = _

  /** The moving ball */
  val ball = new Circle {
    radius = 5.0
    fill = Color.White
    centerX <== centerBallX
    centerY <== centerBallY
  }

  /** The left and right paddles */
  val leftPaddle: Rectangle = new Rectangle {
    x = 20
    width = 10
    height = 30
    fill = Color.LightBlue
    cursor = Cursor.HAND
    translateY <== leftPaddleY
    onMousePressed = (me: MouseEvent) => {
      initLeftPaddleTranslateY = leftPaddle.translateY()
      leftPaddleDragAnchorY = me.sceneY
    }
    onMouseDragged = (me: MouseEvent) => {
      val dragY = me.sceneY - leftPaddleDragAnchorY
      leftPaddleY() = initLeftPaddleTranslateY + dragY
    }
  }
  val rightPaddle: Rectangle = new Rectangle {
    x = 470
    width = 10
    height = 30
    fill = Color.LightBlue
    cursor = Cursor.HAND
    translateY <== rightPaddleY
    onMousePressed = (me: MouseEvent) => {
      initRightPaddleTranslateY = rightPaddle.getTranslateY
      rightPaddleDragAnchorY = me.getSceneY
    }
    onMouseDragged = (me: MouseEvent) => {
      val dragY = me.sceneY - rightPaddleDragAnchorY
      rightPaddleY() = initRightPaddleTranslateY + dragY
    }
  }

  /** The walls */
  val topWall = new Rectangle {
    x = 0
    y = 0
    width = 500
    height = 1
  }
  val rightWall = new Rectangle {
    x = 500
    y = 0
    width = 1
    height = 500
  }
  val leftWall = new Rectangle {
    x = 0
    y = 0
    width = 1
    height = 500
  }
  val bottomWall = new Rectangle {
    x = 0
    y = 500
    width = 500
    height = 1
  }

  /**
   * Controls whether the startButton is visible
   */
  val startVisible = BooleanProperty(true)

  /** The animation of the ball */
  val keyFrame = KeyFrame(10 ms, onFinished = {
    event: ActionEvent =>
      checkForCollision()
      val horzPixels = if (movingRight) 1 else -1
      val vertPixels = if (movingDown) 1 else -1
      centerBallX() = centerBallX.value + horzPixels
      centerBallY() = centerBallY.value + vertPixels
  })
  val pongAnimation = new Timeline {
    keyFrames = Seq(keyFrame)
    cycleCount = Timeline.Indefinite
  }

  val startButton = new Button {
    layoutX() = 225
    layoutY() = 470
    text = "Start!"
    visible <== startVisible
    onAction = {
      event: ActionEvent =>
        startVisible() = false
        pongAnimation.playFromStart()
        pongComponents.requestFocus()
    }
  }

  /** The Group containing all of the walls, paddles, and ball. This also allows
    * us to requestFocus for KeyEvents on the Group
    */
  var pongComponents: Group = new Group {
    focusTraversable = true
    children = List(
      ball,
      topWall,
      leftWall,
      rightWall,
      bottomWall,
      leftPaddle,
      rightPaddle,
      startButton
    )
    onKeyPressed = (k: KeyEvent) => k.code match {
      case KeyCode.SPACE if pongAnimation.status() == Status.STOPPED =>
      rightPaddleY() = rightPaddleY.value - 6
      case KeyCode.L if !rightPaddle.boundsInParent().intersects(topWall.boundsInLocal()) =>
        rightPaddleY() = rightPaddleY.value - 6
      case KeyCode.COMMA if !rightPaddle.boundsInParent().intersects(bottomWall.boundsInLocal()) =>
        rightPaddleY() = rightPaddleY.value + 6
      case KeyCode.A if !leftPaddle.boundsInParent().intersects(topWall.boundsInLocal()) =>
        leftPaddleY() = leftPaddleY.value - 6
      case KeyCode.Z if !leftPaddle.boundsInParent().intersects(bottomWall.boundsInLocal()) =>
        leftPaddleY() = leftPaddleY.value + 6
      case _ =>
    }
  }

  /** Sets the initial starting positions of the ball and paddles */
  def initialize() {
    centerBallX() = 250
    centerBallY() = 250
    leftPaddleY() = 235.0
    rightPaddleY() = 235
    startVisible() = true
    pongComponents.requestFocus()
  }

  /** Checks whether or not the ball has collided with either the paddles,
    * topWall, or bottomWall.  If the ball hits the wall behind the paddles,
    * the game is over.
    */
  def checkForCollision() {
    if (ball.intersects(rightWall.boundsInLocal()) || ball.intersects(leftWall.boundsInLocal())) {
      pongAnimation.stop()
      initialize()
    } else if (ball.intersects(bottomWall.boundsInLocal()) ||
      ball.intersects(topWall.boundsInLocal())) {
      movingDown = !movingDown
    } else if (ball.intersects(leftPaddle.boundsInParent()) && !movingRight) {
      movingRight = !movingRight
    } else if (ball.intersects(rightPaddle.boundsInParent()) && movingRight) {
      movingRight = !movingRight
    }
  }

  stage = new PrimaryStage {
    title = "ZenPong Example"
    scene = new Scene(500, 500) {
      fill = LinearGradient(
        startX = 0.0,
        startY = 0.0,
        endX = 0.0,
        endY = 1.0,
        proportional = true,
        cycleMethod = CycleMethod.NoCycle,
        stops = List(Stop(0.0, Color.Black), Stop(0.0, Color.Gray)))
      content = pongComponents
    }
  }

  initialize()
}