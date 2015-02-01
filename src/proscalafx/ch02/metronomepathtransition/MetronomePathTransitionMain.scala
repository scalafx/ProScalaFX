package proscalafx.ch02.metronomepathtransition

import javafx.animation.Animation.Status

import scalafx.Includes._
import scalafx.animation.PathTransition.OrientationType
import scalafx.animation.{Interpolator, PathTransition, Timeline}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.{ArcTo, Ellipse, MoveTo, Path}
import scalafx.util.Duration

object MetronomePathTransitionMain extends JFXApp {

  val ellipse = new Ellipse {
    centerX = 100
    centerY = 50
    radiusX = 4
    radiusY = 8
    fill = Color.Blue
  }

  val anim = new PathTransition {
    duration = Duration(1000.0)
    node = ellipse
    path = new Path {
      elements = List(
        MoveTo(100, 50),
        ArcTo(350, 350, 0, 300, 50, largeArcFlag = false, sweepFlag = true)
      )
    }
    orientation = OrientationType.ORTHOGONAL_TO_TANGENT
    interpolator = Interpolator.LINEAR
    autoReverse = true
    cycleCount = Timeline.Indefinite
  }

  stage = new PrimaryStage {
    title = "Metronome using PathTransition"
    scene = new Scene(400, 500) {
      content = List(
        ellipse,
        new HBox {
          layoutX = 60
          layoutY = 420
          spacing = 10
          children = List(
            new Button {
              text = "Start"
              onAction = handle {anim.playFromStart()}
              disable <== (anim.status =!= Status.STOPPED)
            },
            new Button {
              text = "Pause"
              onAction = handle {anim.pause()}
              disable <== (anim.status =!= Status.RUNNING)
            },
            new Button {
              text = "Resume"
              onAction = handle {anim.play()}
              disable <== (anim.status =!= Status.PAUSED)
            },
            new Button {
              text = "Stop"
              onAction = handle {anim.stop()}
              disable <== (anim.status === Status.STOPPED)
            }
          )
        }
      )
    }
  }
}