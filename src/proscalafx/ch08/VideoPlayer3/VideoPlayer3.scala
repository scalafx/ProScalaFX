package proscalafx.ch08.VideoPlayer3

import java.io.File
import java.net.URL

import scalafx.Includes._
import scalafx.animation.{ParallelTransition, TranslateTransition}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.geometry.{Pos, Rectangle2D}
import scalafx.scene.control.Label
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{Media, MediaMarkerEvent, MediaPlayer, MediaView}
import scalafx.scene.{Node, Scene}
import scalafx.util.Duration


/**
 * @author Jarek Sacha 
 */
object VideoPlayer3 extends JFXApp {

  val message = new Label {
    text = "I \u2764 Robots"
    visible = false
  }

  val file = new File("media/omgrobots.flv")
  val media = new Media(file.toURI.toString) {
    markers ++= Map(
      "Split" -> (3000 ms),
      "Join" -> (9000 ms)
    )
  }

  val mediaPlayer = new MediaPlayer(media)

  val mediaView1 = new MediaView(mediaPlayer) {
    viewport = new Rectangle2D(0, 0, 960 / 2, 540)
    alignmentInParent = Pos.CenterLeft
  }

  val mediaView2 = new MediaView(mediaPlayer) {
    viewport = new Rectangle2D(960 / 2, 0, 960 / 2, 540)
    alignmentInParent = Pos.CenterRight
  }

  val root = new StackPane {
    children +=(message, mediaView1, mediaView2)
    onMouseClicked = (event: MouseEvent) => {
      mediaPlayer.seek(Duration.ZERO)
      message.visible = false
    }
  }

  stage = new PrimaryStage {
    title = "Video Player 3"
    scene = new Scene(root, 960, 540) {
      val stylesheet: URL = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
  }

  mediaPlayer.onMarker = (event: MediaMarkerEvent) => Platform.runLater {
    event.marker.getKey match {
      case "Split" =>
        message.visible = true
        buildSplitTransition(mediaView1, mediaView2).play()
      case _ => buildJoinTransition(mediaView1, mediaView2).play()
    }
  }

  mediaPlayer.play()


  private def buildJoinTransition(one: Node, two: Node) = new ParallelTransition {
    children = List(
      new TranslateTransition {
        duration = (1000 ms)
        node = one
        byX = 200
      },
      new TranslateTransition {
        duration = (1000 ms)
        node = two
        byX = -200
      }
    )
  }


  private def buildSplitTransition(one: Node, two: Node) = new ParallelTransition {
    children = List(
      new TranslateTransition {
        duration = (1000 ms)
        node = one
        byX = -200
      },
      new TranslateTransition {
        duration = (1000 ms)
        node = two
        byX = 200
      }
    )
  }
}
