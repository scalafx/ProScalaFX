package proscalafx.ch08.BasicAudioClip

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.StackPane
import scalafx.scene.media.AudioClip


/**
  * Playing an audio clip.
  *
  * @author Jarek Sacha
  */
object BasicAudioClip extends JFXApp3 {

  override def start(): Unit = {

    val resource = getClass.getResource("resources/beep.wav")
    val audioClip = new AudioClip(resource.toString)
    val stackPane = new StackPane {
      padding = Insets(10)
      children = new Button {
        text = "Bing Zzzzt!"
        onAction = () => audioClip.play(1.0)
      }
    }

    stage = new PrimaryStage {
      title = "Basic AudioClip Example"
      scene = new Scene(stackPane, 200, 200) {
        stylesheets += getClass.getResource("media.css").toString
      }
    }
  }
}
