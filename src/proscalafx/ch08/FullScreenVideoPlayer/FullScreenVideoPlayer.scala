package proscalafx.ch08.FullScreenVideoPlayer

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{Media, MediaPlayer, MediaView}

import java.io.File


/**
  * @author Jarek Sacha
  */
object FullScreenVideoPlayer extends JFXApp3 {

  override def start(): Unit = {

    val file = new File("media/omgrobots.mp4")
    val media = new Media(file.toURI.toString)
    val mediaPlayer = new MediaPlayer(media)
    val mediaView = new MediaView(mediaPlayer) {
      fitWidth <== scene.selectDouble("width")
      fitHeight <== scene.selectDouble("height")
      preserveRatio = true
    }

    val root = new StackPane {
      children = mediaView
    }

    stage = new PrimaryStage {
      title = "Video Player 1"
      fullScreen = true
      scene = new Scene(root, 960, 540)
    }

    mediaPlayer.play()
  }
}
