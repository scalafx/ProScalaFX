package proscalafx.ch08.BasicAudioClip

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
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
object BasicAudioClip extends JFXApp {

  val resource = getClass.getResource("resources/beep.wav")
  val audioClip = new AudioClip(resource.toString)
  val stackPane = new StackPane {
    padding = Insets(10)
    children = new Button {
      text = "Bing Zzzzt!"
      onAction = {_: ActionEvent => audioClip.play(1.0)}
    }
  }

  stage = new PrimaryStage {
    title = "Basic AudioClip Example"
    scene = new Scene(stackPane, 200, 200) {
      stylesheets += getClass.getResource("media.css").toString
    }
  }
}
