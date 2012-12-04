package proscalafx.ch08.VideoPlayer2

import java.io.File
import java.net.URL
import javafx.geometry.Pos
import scalafx.Includes._
import scalafx.application.{Platform, JFXApp}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{MediaMarkerEvent, Media, MediaPlayer, MediaView}
import scalafx.stage.Stage
import scalafx.util.Duration
import sfxext.Includes._


/**
 * @author Jarek Sacha 
 */
object VideoPlayer2 extends JFXApp {

  val markerText = new Label {
    alignment = Pos.TOP_CENTER
  }

  val file = new File("media/omgrobots.flv")
  val media = new Media(file.toURI.toString)
  media.getMarkers ++= Map(
    "Robot Finds Wall" -> (3100 ms),
    "Then Finds the Green Line" -> (5600 ms),
    "Robot Grabs Sled" -> (8000 ms),
    "And Heads for Home" -> (11500 ms)
  )

  val mediaPlayer = new MediaPlayer(media)
  mediaPlayer.onMarker = (event: MediaMarkerEvent) => Platform.runLater {markerText.text = event.marker.getKey}

  val mediaView = new MediaView(mediaPlayer)
  val root = new StackPane {
    content +=(mediaView, markerText)
    onMouseClicked = (event: MouseEvent) => {
      mediaPlayer.seek(Duration.ZERO)
      markerText.text = ""
    }
  }

  stage = new Stage {
    title = "Video Player 2"
    scene = new Scene(root, 960, 540) {
      val stylesheet: URL = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
  }

  mediaPlayer.play()
}
