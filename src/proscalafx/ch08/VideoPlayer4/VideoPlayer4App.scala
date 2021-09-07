package proscalafx.ch08.VideoPlayer4

import com.sun.javafx.{runtime => csjfxr}
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene

/**
  * @author Jarek Sacha
  */
object VideoPlayer4App extends JFXApp3 {

  override def start(): Unit = {

    println("JavaFX version: " + csjfxr.VersionInfo.getRuntimeVersion)

    val videoPlayer = new VideoPlayer4()

    val stylesheet = getClass.getResource("media.css")

    stage = new PrimaryStage {
      title = "Video Player 4"
      scene = new Scene(videoPlayer.rootNode, 1024, 680) {
        stylesheets += stylesheet.toString
      }
      videoPlayer.initSceneDragAndDrop(scene())
    }

    videoPlayer.mediaModel.mediaPlayer().play()
  }

}
