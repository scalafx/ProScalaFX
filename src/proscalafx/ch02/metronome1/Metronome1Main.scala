package proscalafx.ch02.metronome1

import scalafx.Includes._
import scalafx.animation.Interpolator
import javafx.animation.Animation.Status
import scalafx.animation.KeyFrame
import scalafx.animation.KeyValue
import scalafx.animation.Timeline
import scalafx.application.JFXApp
import scalafx.beans.property.DoubleProperty
import scalafx.scene.layout.HBox
import scalafx.scene.shape.Line
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.event._
import scalafx.stage.Stage

import scalafx.scene.paint.Color

object Metronome1Main extends JFXApp {

  val startXVal: DoubleProperty = DoubleProperty(100.0)

  val anim = new Timeline {
    autoReverse = true
    keyFrames = Seq(
      at(0 s) { startXVal -> 100 },
      at(1 s) { startXVal -> 300 tween Interpolator.LINEAR })
    cycleCount = Timeline.INDEFINITE
  }

  stage = new Stage {
    title = "Metronome 1"
    width = 400
    height = 500
    scene = new Scene {
      content = List(
        new Line {
          startX <== startXVal
          startY = 50
          endX = 200
          endY = 400
          strokeWidth = 4
          stroke = Color.BLUE
        },
        new HBox {
          layoutX = 60
          layoutY = 420
          spacing = 10
          content = List(
            new Button {
              text = "Start"
              onAction = (ae: ActionEvent) => {
                anim.playFromStart
              }
              disable <== anim.status.isNotEqualTo(Status.STOPPED)
            },
            new Button {
              text = "Pause"
              onAction = (ae: ActionEvent) => {
                anim.pause
              }
              disable <== anim.status.isNotEqualTo(Status.RUNNING)
            },
            new Button {
              text = "Resume"
              onAction = (ae: ActionEvent) => {
                anim.play
              }
              disable <== anim.status.isNotEqualTo(Status.PAUSED)
            },
            new Button {
              text = "Stop"
              onAction = (ae: ActionEvent) => {
                anim.stop
              }
              disable <== anim.status.isEqualTo(Status.STOPPED)
            })
        })
    }
  }

}