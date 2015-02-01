package proscalafx.ch08.CodeMonkeyToDo

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Hyperlink, Label, Slider}
import scalafx.scene.layout.{GridPane, Priority, VBox}
import scalafx.scene.media.AudioClip
import scalafx.scene.web.WebView


/**
 * Controlling the playback parameters of an AudioClip.
 *
 * @author Jarek Sacha 
 */
object CodeMonkeyToDo extends JFXApp {
  val coffeeClip = new AudioClip(clipResourceString("resources/coffee.mp3"))
  val jobClip = new AudioClip(clipResourceString("resources/job.mp3"))
  val meetingClip = new AudioClip(clipResourceString("resources/meeting.mp3"))

  val grid = new GridPane {
    padding = Insets(10)
    hgap = 10
    vgap = 5
  }

  val (volumeSlider, rateSlider, balanceSlider) = createControls(grid)
  createClipList(grid)

  stage = new PrimaryStage {
    scene = new Scene(grid, 640, 380) {
      title = "AudioClip Example"
      stylesheets += getClass.getResource("media.css").toString
    }
  }

  private def clipResourceString(clipName: String): String = {
    getClass.getResource(clipName).toString
  }

  private def createControls(grid: GridPane): (Slider, Slider, Slider) = {
    val volumeLabel = new Label {text = "Volume"}
    val rateLabel = new Label {text = "Rate"}
    val balanceLabel = new Label {text = "Balance"}

    GridPane.setHalignment(volumeLabel, HPos.Center)
    GridPane.setHalignment(rateLabel, HPos.Center)
    GridPane.setHalignment(balanceLabel, HPos.Center)

    val volumeSlider = new Slider {
      min = 0.0
      max = 1.0
      value = 1.0
      hgrow = Priority.Always
    }
    val rateSlider = new Slider {
      min = 0.25
      max = 2.5
      value = 1.0
      hgrow = Priority.Always
    }
    val balanceSlider = new Slider {
      min = -1.0
      max = 1.0
      value = 0.0
      hgrow = Priority.Always
    }

    grid.add(volumeLabel, 0, 2)
    grid.add(volumeSlider, 0, 3)
    grid.add(rateLabel, 1, 2)
    grid.add(rateSlider, 1, 3)
    grid.add(balanceLabel, 2, 2)
    grid.add(balanceSlider, 2, 3)

    (volumeSlider, rateSlider, balanceSlider)
  }

  private def createClipList(grid: GridPane) {

    val clipLabel = new Label {
      text = "Code Monkey To-Do List:"
      id = "clipLabel"
    }
    val getUpButton = new Button {
      text = "Get Up, Get Coffee"
      prefWidth = 300
      onAction = play(coffeeClip)
    }
    val goToJobButton: Button = new Button {
      text = "Go to Job"
      prefWidth = 300
      onAction = play(jobClip)
    }
    val meetingButton: Button = new Button {
      text = "Have Boring Meeting"
      prefWidth = 300
      onAction = play(meetingClip)
    }
    val link = new Hyperlink {
      text = "About Code Monkey..."
      onAction = (_: ActionEvent) => {
        val webView = new WebView {
          engine.load("http://www.jonathancoulton.com/2006/04/14/" + "thing-a-week-29-code-monkey/")
        }
        val stage = new PrimaryStage {
          title = "Code Monkey"
          scene = new Scene(webView, 720, 480)
        }
        stage.show()
      }
    }

    val vbox = new VBox {
      spacing = 30
      alignment = Pos.TopCenter
      children +=(clipLabel, getUpButton, goToJobButton, meetingButton, link)
    }

    GridPane.setHalignment(vbox, HPos.Center)

    GridPane.setHgrow(vbox, Priority.Always)
    GridPane.setVgrow(vbox, Priority.Always)
    grid.add(vbox, 0, 0, GridPane.REMAINING, 1)
  }

  /** Returns a function that can be assigned to `oAction` */
  private def play(audioClip: AudioClip) =
    (_: ActionEvent) => {
      audioClip.play(volumeSlider.value(), balanceSlider.value(), rateSlider.value(), 0.0, 0)
    }
}
