package proscalafx.ch08.AudioPlayer3

import com.sun.javafx.runtime.VersionInfo
import javafx.scene.{input => jfxsi}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.input.DragEvent
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage


/**
 * @author Jarek Sacha 
 */
object AudioPlayer3 extends JFXApp {
  println("JavaFX version: " + VersionInfo.getRuntimeVersion)

  private final val songModel = new SongModel() {
    url = "http://traffic.libsyn.com/dickwall/JavaPosse373.mp3"
  }
  private val metaDataView = new MetadataView(songModel)
  private val playerControlsView = new PlayerControlsView(songModel)

  val root = new BorderPane {
    center = metaDataView.viewNode
    bottom = playerControlsView.viewNode
  }

  stage = new Stage {
    title = "Audio Player 3"
    scene = new Scene(root, 800, 400) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
    initSceneDragAndDrop(scene())
  }


  private def initSceneDragAndDrop(scene: Scene) {
    // NOTE: implicit conversion to EventHandler does not work correctly for  `(event: DragEvent) => Unit` in ScalaFX.
    // Here we relay on a patch in sfxext.Includes._
    scene.onDragOver = (event: DragEvent) => {
      val db = event.dragboard
      if (db.hasFiles || db.hasUrl) {
        // NOTE: We need to pass in `TransferMode` as Java vararg, since we use `javafx.scene.input.DragEvent`
        event.acceptTransferModes(jfxsi.TransferMode.ANY: _*)
      }
      event.consume()
    }

    // NOTE: implicit conversion to EventHandler does not work correctly for  `(event: DragEvent) => Unit` in ScalaFX.
    // Here we relay on a patch in sfxext.Includes._
    scene.onDragDropped = (event: DragEvent) => {
      val db = event.dragboard
      var url = if (db.hasFiles) {
        db.getFiles.get(0).toURI.toString
      } else if (db.hasUrl) {
        db.getUrl
      } else {
        null
      }

      if (url != null) {
        songModel.url = url
        songModel.mediaPlayer().play()
      }
      event.dropCompleted = url != null
      event.consume()
    }
  }

}
