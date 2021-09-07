package proscalafx.ch08.AudioPlayer3

import com.sun.javafx.runtime.VersionInfo
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.input.TransferMode
import scalafx.scene.layout.BorderPane

/**
  * @author Jarek Sacha
  */
object AudioPlayer3 extends JFXApp3 {

  override def start(): Unit = {

    val songModel = new SongModel() {
      url = "https://traffic.libsyn.com/dickwall/JavaPosse373.mp3"
    }

    println("JavaFX version: " + VersionInfo.getRuntimeVersion)

    val metaDataView = new MetadataView(songModel)
    val playerControlsView = new PlayerControlsView(songModel)

    val root = new BorderPane {
      center = metaDataView.viewNode
      bottom = playerControlsView.viewNode
    }

    stage = new PrimaryStage {
      title = "Audio Player 3"
      scene = new Scene(root, 800, 400) {
        val stylesheet = getClass.getResource("media.css")
        stylesheets += stylesheet.toString
      }
      initSceneDragAndDrop(scene(), songModel)
    }
  }

  private def initSceneDragAndDrop(scene: Scene, songModel: SongModel): Unit = {
    scene.onDragOver = event => {
      val db = event.dragboard
      if (db.hasFiles || db.hasUrl) {
        // NOTE: We need to pass in `TransferMode` as Java vararg, since we use `javafx.scene.input.DragEvent`
        event.acceptTransferModes(TransferMode.Any: _*)
      }
      event.consume()
    }

    scene.onDragDropped = event => {
      val db = event.dragboard
      val url =
        if (db.hasFiles) {
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
