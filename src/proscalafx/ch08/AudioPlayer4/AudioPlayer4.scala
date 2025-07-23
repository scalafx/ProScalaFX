package proscalafx.ch08.AudioPlayer4

import com.sun.javafx.runtime as csjfxr
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.input.TransferMode
import scalafx.scene.layout.{BorderPane, StackPane}
import scalafx.scene.{Node, Scene}

/**
 * @author Jarek Sacha
 */
object AudioPlayer4 extends JFXApp3 {
  println("JavaFX version: " + csjfxr.VersionInfo.getRuntimeVersion)

  private var songModel: SongModel                   = _
  private var playerControlsView: PlayerControlsView = _
  private var metaDataView: MetadataView             = _
  private var equalizerView: EqualizerView           = _
  private var page1: Node                            = _
  private var page2: Node                            = _
  private var rootNode: StackPane                    = _

  override def start(): Unit = {

    songModel = new SongModel() {
      url = "https://traffic.libsyn.com/dickwall/JavaPosse373.mp3"
    }

    page1 = createPageOne()
    page2 = createPageTwo()
    rootNode = new StackPane {
      children = page1
    }

    stage = new PrimaryStage {
      title = "Audio Player 4"
      scene = new Scene(rootNode, 800, 400) {
        val stylesheet = getClass.getResource("media.css")
        stylesheets += stylesheet.toString
      }
      initSceneDragAndDrop(scene())
    }

    songModel.mediaPlayer().play()
  }

  private def createPageOne(): Node = {
    metaDataView = new MetadataView(songModel)
    playerControlsView = new PlayerControlsView(songModel)
    playerControlsView.onNextPageAction { _ => rootNode.children = page2 }
    new BorderPane {
      center = metaDataView.viewNode
      bottom = playerControlsView.viewNode
    }
  }

  private def createPageTwo(): Node = {
    equalizerView = new EqualizerView(songModel)
    equalizerView.onNextPageAction { _ =>
      println("Got back button click")
      rootNode.children = page1
    }
    equalizerView.viewNode
  }

  private def initSceneDragAndDrop(scene: Scene): Unit = {
    scene.onDragOver = event => {
      val db = event.dragboard
      if (db.hasFiles || db.hasUrl) {
        // NOTE: We need to pass in `TransferMode` as Java vararg, since we use `javafx.scene.input.DragEvent`
        event.acceptTransferModes(TransferMode.Any*)
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
      event.dropCompleted = (url != null)
      event.consume()
    }
  }
}
