package proscalafx.ch08.VideoPlayer4

import scalafx.Includes.*
import scalafx.scene.input.TransferMode
import scalafx.scene.layout.{BorderPane, StackPane}
import scalafx.scene.{Node, Scene}

/**
 * @author Jarek Sacha
 */
class VideoPlayer4 {

  private var playerControlsView: PlayerControlsView = _
  private var videoView: VideoView                   = _
  private var equalizerView: EqualizerView           = _

  val mediaModel: MediaModel = new MediaModel() {
    url = "https://download.oracle.com/otndocs/products/javafx/oow2010-2.mp4"
  }

  private val page1 = createPageOne()
  private val page2 = createPageTwo()
  val rootNode: StackPane = new StackPane {
    children = page1
  }

  private def createPageOne(): Node = {
    videoView = new VideoView(mediaModel)
    playerControlsView = new PlayerControlsView(mediaModel)
    playerControlsView.onNextPageAction { _ => rootNode.children = page2 }
    new BorderPane {
      center = videoView.viewNode
      bottom = playerControlsView.viewNode
    }
  }

  private def createPageTwo(): Node = {
    equalizerView = new EqualizerView(mediaModel)
    equalizerView.onNextPageAction { _ => rootNode.children = page1 }
    equalizerView.viewNode
  }

  def initSceneDragAndDrop(scene: Scene): Unit = {
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
        mediaModel.url = url
        mediaModel.mediaPlayer().play()
      }
      event.dropCompleted = (url != null)
      event.consume()
    }
  }
}
