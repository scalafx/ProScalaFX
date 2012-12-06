package proscalafx.ch08.VideoPlayer1

import java.io.File
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.stage.Stage


/**
 * @author Jarek Sacha 
 */
object VideoPlayer1 extends JFXApp {

  val file = new File("media/omgrobots.flv")
  val media = new Media(file.toURI.toString)
  val mediaPlayer = new MediaPlayer(media)
  val mediaView = new MediaView(mediaPlayer)
  val root = new StackPane {content = mediaView}

  stage = new Stage {
    title = "Video Player 1"
    scene = new Scene(root, 960, 540)
  }

  mediaPlayer.play()
}
