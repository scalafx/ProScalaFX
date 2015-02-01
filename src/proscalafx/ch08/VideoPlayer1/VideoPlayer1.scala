package proscalafx.ch08.VideoPlayer1

import java.io.File

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.StackPane
import scalafx.scene.media.{Media, MediaPlayer, MediaView}


/**
 * @author Jarek Sacha 
 */
object VideoPlayer1 extends JFXApp {

  val file = new File("media/omgrobots.flv")
  val media = new Media(file.toURI.toString)
  val mediaPlayer = new MediaPlayer(media)
  val mediaView = new MediaView(mediaPlayer)
  val root = new StackPane {children = mediaView}

  stage = new PrimaryStage {
    title = "Video Player 1"
    scene = new Scene(root, 960, 540)
  }

  mediaPlayer.play()
}
