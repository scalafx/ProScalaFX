package proscalafx.ch08.VideoPlayer2

import scalafx.Includes._
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.util.Duration

import java.io.File
import java.net.URL
import scala.language.postfixOps


/**
  * @author Jarek Sacha
  */
object VideoPlayer2 extends JFXApp3 {

  override def start(): Unit = {

    val markerText = new Label {
      alignmentInParent = Pos.TopCenter
    }

    val file = new File("media/omgrobots.flv")
    val media = new Media(file.toURI.toString) {
      markers ++= Map(
        "Robot Finds Wall" -> (3100 ms),
        "Then Finds the Green Line" -> (5600 ms),
        "Robot Grabs Sled" -> (8000 ms),
        "And Heads for Home" -> (11500 ms)
      )
    }

    val mediaPlayer = new MediaPlayer(media) {
      onMarker = event => Platform.runLater {
        markerText.text = event.marker.getKey
      }
    }

    val mediaView = new MediaView(mediaPlayer)
    val root = new StackPane {
      children ++= Seq(mediaView, markerText)
      onMouseClicked = () => {
        mediaPlayer.seek(Duration.Zero)
        markerText.text = ""
      }
    }

    stage = new PrimaryStage {
      title = "Video Player 2"
      scene = new Scene(root, 960, 540) {
        val stylesheet: URL = getClass.getResource("media.css")
        stylesheets += stylesheet.toString
      }
    }

    mediaPlayer.play()
  }
}
