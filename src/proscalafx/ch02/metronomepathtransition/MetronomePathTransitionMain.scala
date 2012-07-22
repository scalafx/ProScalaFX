/**
 *
 */
package proscalafx.ch02.metronomepathtransition

import javafx.animation.Animation.Status
import javafx.animation.PathTransition.OrientationType
import scalafx.Includes._
import scalafx.animation.PathTransition.sfxPathTransition2jfx
import scalafx.animation.Interpolator
import scalafx.animation.PathTransition
import scalafx.animation.Timeline
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.ArcTo.sfxArcTo2jfx
import scalafx.scene.shape.MoveTo.sfxMoveTo2jfx
import scalafx.scene.shape.ArcTo
import scalafx.scene.shape.Ellipse
import scalafx.scene.shape.MoveTo
import scalafx.scene.shape.Path
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.util.Duration

/**
 *
 */
object MetronomePathTransitionMain extends JFXApp {

  val ellipse = new Ellipse {
    centerX = 100
    centerY = 50
    radiusX = 4
    radiusY = 8
    fill = Color.BLUE
  }

  val anim = new PathTransition {
    duration = Duration(1000.0)
    node = ellipse
    path = new Path {
      elements = List(
        MoveTo(100, 50),
        ArcTo(350, 350, 0, 300, 50, false, true))
    }
    orientation = OrientationType.ORTHOGONAL_TO_TANGENT
    interpolator = Interpolator.LINEAR
    autoReverse = true
    cycleCount = Timeline.INDEFINITE
  }

  stage = new Stage {
    width = 400
    height = 500
    title = "Metronome using PathTransition"
    scene = new Scene {
      content = List(
        ellipse,
        new HBox {
          layoutX = 60
          layoutY = 420
          spacing = 10
          content = List(
            new Button {
              text = "Start"
              onAction = (ae: ActionEvent) => anim.playFromStart
              disable <== (anim.status =!= Status.STOPPED)
            },
            new Button {
              text = "Pause"
              onAction = (ae: ActionEvent) => anim.pause
              disable <== (anim.status =!= Status.RUNNING)
            },
            new Button {
              text = "Resume"
              onAction = (ae: ActionEvent) => anim.play
              disable <== (anim.status =!= (Status.PAUSED))
            },
            new Button {
              text = "Stop"
              onAction = (ae: ActionEvent) => anim.stop
              disable <== (anim.status === Status.STOPPED)
            })
        })
    }
  }

}