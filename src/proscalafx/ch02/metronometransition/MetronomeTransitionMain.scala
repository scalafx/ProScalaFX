package proscalafx.ch02.metronometransition

import javafx.animation.Animation.Status

import scalafx.Includes._
import scalafx.animation.{Interpolator, Timeline, TranslateTransition}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.util.Duration

object MetronomeTransitionMain extends JFXApp {

  val circle = new Circle {
    centerX = 100
    centerY = 50
    radius = 4
    fill = Color.Blue
  }

  val anim = new TranslateTransition {
    duration = Duration(1000.0)
    node = circle
    fromX = 0
    toX = 200
    interpolator = Interpolator.LINEAR
    autoReverse = true
    cycleCount = Timeline.Indefinite
  }

  stage = new PrimaryStage {
    width = 400
    height = 500
    title = "Metronome using TranslateTransition"
    scene = new Scene(400, 500) {
      content = List(
        circle,
        new HBox {
          layoutX = 60
          layoutY = 420
          spacing = 10
          children = List(
            new Button {
              text = "Start"
              onAction = handle {anim.playFromStart()}
              disable <== anim.status.isNotEqualTo(Status.STOPPED)
            },
            new Button {
              text = "Pause"
              onAction = handle {anim.pause()}
              disable <== anim.status.isNotEqualTo(Status.RUNNING)
            },
            new Button {
              text = "Resume"
              onAction = handle {anim.play()}
              disable <== anim.status.isNotEqualTo(Status.PAUSED)
            },
            new Button {
              text = "Stop"
              onAction = handle {anim.stop()}
              disable <== anim.status.isEqualTo(Status.STOPPED)
            }
          )
        }
      )
    }
  }
}