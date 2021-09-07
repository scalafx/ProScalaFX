package proscalafx.ch02.metronomepathtransition

import scalafx.Includes._
import scalafx.animation.Animation.Status
import scalafx.animation.PathTransition.OrientationType
import scalafx.animation.{Interpolator, PathTransition, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.{ArcTo, Ellipse, MoveTo, Path}
import scalafx.util.Duration

object MetronomePathTransitionMain extends JFXApp3 {

  override def start(): Unit = {
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
      orientation = OrientationType.OrthogonalToTangent
      interpolator = Interpolator.Linear
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
            // NOTE: the `disable` bindings below compare value of a property to JavaFX constant
            children = List(
              new Button {
                text = "Start"
                onAction = () => anim.playFromStart()
                disable <== (anim.status =!= Status.Stopped.delegate)
              },
              new Button {
                text = "Pause"
                onAction = () => anim.pause()
                disable <== (anim.status =!= Status.Running.delegate)
              },
              new Button {
                text = "Resume"
                onAction = () => anim.play()
                disable <== (anim.status =!= Status.Paused.delegate)
              },
              new Button {
                text = "Stop"
                onAction = () => anim.stop()
                disable <== (anim.status === Status.Stopped.delegate)
              }
            )
          }
        )
      }
    }
  }
}
