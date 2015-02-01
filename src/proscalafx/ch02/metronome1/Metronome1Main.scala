package proscalafx.ch02.metronome1

import javafx.animation.Animation.Status

import scalafx.Includes._
import scalafx.animation.{Interpolator, Timeline}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.DoubleProperty
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Line

object Metronome1Main extends JFXApp {

  val startXVal = DoubleProperty(100.0)

  val anim = new Timeline {
    autoReverse = true
    keyFrames = Seq(
      at(0 s) {
        startXVal -> 100
      },
      at(1 s) {
        startXVal -> 300 tween Interpolator.LINEAR
      })
    cycleCount = Timeline.Indefinite
  }

  stage = new PrimaryStage {
    title = "Metronome 1"
    scene = new Scene(400, 500) {
      content = List(
        new Line {
          startX <== startXVal
          startY = 50
          endX = 200
          endY = 400
          strokeWidth = 4
          stroke = Color.Blue
        },
        new HBox {
          layoutX = 60
          layoutY = 420
          spacing = 10
          children = List(
            new Button {
              text = "Start"
              onAction = handle {anim.playFromStart()}
              disable <== anim.status =!= Status.STOPPED
            },
            new Button {
              text = "Pause"
              onAction = handle {anim.pause()}
              disable <== anim.status =!= Status.RUNNING
            },
            new Button {
              text = "Resume"
              onAction = handle {anim.play()}
              disable <== anim.status =!= Status.PAUSED
            },
            new Button {
              text = "Stop"
              onAction = handle {anim.stop()}
              disable <== anim.status === Status.STOPPED
            }
          )
        }
      )
    }
  }

}