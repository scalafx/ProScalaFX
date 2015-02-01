
package proscalafx.ch08.VideoPlayer4

import com.sun.javafx.{runtime => csjfxr}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.{BorderPane, StackPane}
import scalafx.scene.{Node, Scene}


/**
 * @author Jarek Sacha 
 */
object VideoPlayer4 extends JFXApp {
  println("JavaFX version: " + csjfxr.VersionInfo.getRuntimeVersion)

  private val mediaModel = new MediaModel() {url = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv"}
  private var playerControlsView: PlayerControlsView = _
  private var videoView: VideoView = _
  private var equalizerView: EqualizerView = _

  private val page1 = createPageOne()
  private val page2 = createPageTwo()
  private val rootNode = new StackPane {children = page1}

  stage = new PrimaryStage {
    title = "Video Player 4"
    scene = new Scene(rootNode, 1024, 680) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
    initSceneDragAndDrop(scene())
  }

  mediaModel.mediaPlayer().play()


  private def createPageOne(): Node = {
    videoView = new VideoView(mediaModel)
    playerControlsView = new PlayerControlsView(mediaModel)
    playerControlsView.onNextPageAction { (ae: ActionEvent) => rootNode.children = page2}
    new BorderPane {
      center = videoView.viewNode
      bottom = playerControlsView.viewNode
    }
  }


  private def createPageTwo(): Node = {
    equalizerView = new EqualizerView(mediaModel)
    equalizerView.onNextPageAction { (ae: ActionEvent) => rootNode.children = page1}
    equalizerView.viewNode
  }


  private def initSceneDragAndDrop(scene: Scene) {
    scene.onDragOver = (event: DragEvent) => {
      val db = event.dragboard
      if (db.hasFiles || db.hasUrl) {
        // NOTE: We need to pass in `TransferMode` as Java vararg, since we use `javafx.scene.input.DragEvent`
        event.acceptTransferModes(TransferMode.ANY: _*)
      }
      event.consume()
    }

    scene.onDragDropped = (event: DragEvent) => {
      val db = event.dragboard
      val url = if (db.hasFiles) {
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
