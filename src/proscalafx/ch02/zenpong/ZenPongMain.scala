package proscalafx.ch02.zenpong

import javafx.scene.{ input => jfxsi }
import javafx.{ event => jfxe }
import scalafx.Includes._
import scalafx.Includes.double2DoubleBinding
import scalafx.Includes.integer2IntegerBinding
import scalafx.Includes.jfxDuration2sfx
import scalafx.animation.KeyFrame
import scalafx.animation.Timeline
import scalafx.application.JFXApp
import scalafx.beans.property.BooleanProperty.fromBoolean
import scalafx.beans.property.BooleanProperty.sfxBooleanProperty2jfx
import scalafx.beans.property.BooleanProperty
import scalafx.beans.property.DoubleProperty
import scalafx.scene.control.Button
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.LinearGradient.sfxLinearGradient2jfx
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle.sfxRectangle2jfx
import scalafx.scene.shape.Circle
import scalafx.scene.shape.Rectangle
import scalafx.scene.Cursor
import scalafx.scene.Group
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.util.Duration
import scalafx.event.EventHandler
import javafx.scene.input.KeyCode
import scalafx.animation.Animation
import javafx.animation.Animation.Status

object ZenPongMain extends JFXApp {

  /**
   * The center points of the moving ball
   */
  val centerX = new DoubleProperty
  val centerY = new DoubleProperty

  /**
   * The Y coordinate of the left paddle
   */
  val leftPaddleY = new DoubleProperty

  /**
   * The Y coordinate of the right paddle
   */
  val rightPaddleY = new DoubleProperty

  /**
   * The drag anchor for left and right paddles
   */
  var leftPaddleDragAnchorY: Double = _
  var rightPaddleDragAnchorY: Double = _

  /**
   * The initial translateY property for the left and right paddles
   */
  var initLeftPaddleTranslateY: Double = _
  var initRightPaddleTranslateY: Double = _

  /**
   * The moving ball
   */
  var ball: Circle = new Circle {
    radius = 5.0
    fill = Color.WHITE
  }

  /**
   * The Group containing all of the walls, paddles, and ball.  This also allows
   * us to requestFocus for KeyEvents on the Group
   */
  var pongComponents: Group = _

  /**
   * The left and right paddles
   */
  var leftPaddle: Rectangle = new Rectangle {
    x = 20
    width = 10
    height = 30
    fill = Color.LIGHTBLUE
    cursor = Cursor.HAND
    onMousePressed = (me: MouseEvent) => {
      initLeftPaddleTranslateY = leftPaddle.translateY.get
      leftPaddleDragAnchorY = me.sceneY
    }
    onMouseDragged = (me: MouseEvent) => {
      val dragY = me.sceneY - leftPaddleDragAnchorY
      leftPaddleY.setValue(initLeftPaddleTranslateY + dragY);
    }
  }
  var rightPaddle: Rectangle = new Rectangle {
    x = 470
    width = 10
    height = 30
    fill = Color.LIGHTBLUE
    cursor = Cursor.HAND
    onMousePressed = (me: MouseEvent) => {
      initRightPaddleTranslateY = rightPaddle.getTranslateY
      rightPaddleDragAnchorY = me.getSceneY
    }
    onMouseDragged = (me: MouseEvent) => {
      val dragY = me.sceneY - rightPaddleDragAnchorY
      rightPaddleY.setValue(initRightPaddleTranslateY + dragY)
    }
  }

  /**
   * The walls
   */
  var topWall: Rectangle = new Rectangle {
    x = 0
    y = 0
    width = 500
    height = 1
  }
  var rightWall: Rectangle = new Rectangle {
    x = 500
    y = 0
    width = 1
    height = 500
  }
  var leftWall: Rectangle = new Rectangle {
    x = 0
    y = 0
    width = 1
    height = 500
  }
  var bottomWall: Rectangle = new Rectangle {
    x = 0
    y = 500
    width = 500
    height = 1
  }

  /**
   * Controls whether the startButton is visible
   */
  val startVisible: BooleanProperty = true

  /**
   * The animation of the ball
   */
  val pongAnimation = new Timeline {
    keyFrames = Seq(
      KeyFrame(Duration(10.0) //          ,
      //        onFinished: (ae: ActionEvent) => {})
      ))
  }

  var startButton: Button = new Button {
    layoutX = 225
    layoutY = 470
    text = "Start!"
    onAction = {
      startVisible.set(false)
      pongAnimation.playFromStart
      pongComponents.requestFocus
    }
  }

  /**
   * Controls whether the ball is moving right
   */
  var movingRight = true

  /**
   * Controls whether the ball is moving down
   */
  var movingDown = true

  /**
   * Sets the initial starting positions of the ball and paddles
   */
  def initialize {
    centerX <== 250
    centerY <== 250
    leftPaddleY <== 235.0
    rightPaddleY <== 235
    startVisible.set(true)
    pongComponents.requestFocus
  }

  /**
   * Checks whether or not the ball has collided with either the paddles,
   * topWall, or bottomWall.  If the ball hits the wall behind the paddles,
   * the game is over.
   */
  def checkForCollision {
    if (ball.intersects(rightWall.boundsInLocal.get) || ball.intersects(leftWall.boundsInLocal.get)) {
      pongAnimation.stop
      initialize
    } else if (ball.intersects(bottomWall.getBoundsInLocal) ||
      ball.intersects(topWall.getBoundsInLocal)) {
      movingDown = !movingDown;
    } else if (ball.intersects(leftPaddle.getBoundsInParent) && !movingRight) {
      movingRight = !movingRight;
    } else if (ball.intersects(rightPaddle.getBoundsInParent) && movingRight) {
      movingRight = !movingRight;
    }
  }

  val keyEventHandler = (k: KeyEvent) => {
    if (k.code == KeyCode.SPACE && pongAnimation.status == Status.STOPPED) {
      rightPaddleY.setValue(rightPaddleY.value - 6);
    } else if (k.code == KeyCode.L &&
      !rightPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())) {
      rightPaddleY.setValue(rightPaddleY.value - 6);
    } else if (k.code == KeyCode.COMMA &&
      !rightPaddle.getBoundsInParent().intersects(bottomWall.getBoundsInLocal())) {
      rightPaddleY.setValue(rightPaddleY.value + 6);
    } else if (k.code == KeyCode.A &&
      !leftPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())) {
      leftPaddleY.setValue(leftPaddleY.value - 6);
    } else if (k.code == KeyCode.Z &&
      !leftPaddle.getBoundsInParent().intersects(bottomWall.getBoundsInLocal())) {
      leftPaddleY.setValue(leftPaddleY.value + 6);
    }
  }

  stage = new Stage {
    width = 500
    height = 500
    title = "ZenPong Example"
    scene = new Scene {
      // def apply(startX: Double, startY: Double, endX: Double, endY: Double, proportional: Boolean, cycleMethod: CycleMethod, stops: Stop*) {
      //      fill = LinearGradient(0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
      //          Stop(0.0, Color.BLACK), Stop(0.0, Color.GRAY))
      content = List(
        ball,
        topWall,
        leftWall,
        rightWall,
        bottomWall,
        leftPaddle,
        rightPaddle,
        startButton)
//            onKeyPressed = keyEventHandler
    }
  }

}