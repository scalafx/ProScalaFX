
package proscalafx.ch08.AudioPlayer4

import com.sun.javafx.{runtime => csjfxr}
import javafx.scene.{input => jfxsi}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.scene.input.DragEvent
import scalafx.scene.layout.{StackPane, BorderPane}
import scalafx.scene.{Node, Scene}
import scalafx.stage.Stage


/**
 * @author Jarek Sacha 
 */
object AudioPlayer4 extends JFXApp {
  println("JavaFX version: " + csjfxr.VersionInfo.getRuntimeVersion)

  private val songModel = new SongModel() {url = "http://traffic.libsyn.com/dickwall/JavaPosse373.mp3"}
  private var playerControlsView: PlayerControlsView = _
  private var metaDataView: MetadataView = _
  private var equalizerView: EqualizerView = _

  private val page1 = createPageOne()
  private val page2 = createPageTwo()
  private val rootNode = new StackPane {content = page1}

  stage = new Stage {
    title = "Audio Player 4"
    scene = new Scene(rootNode, 800, 400) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
    initSceneDragAndDrop(scene())
  }

  songModel.mediaPlayer().play()


  private def createPageOne(): Node = {
    metaDataView = new MetadataView(songModel)
    playerControlsView = new PlayerControlsView(songModel)
    playerControlsView.onNextPageAction {(ae: ActionEvent) => rootNode.content = page2}
    new BorderPane {
      center = metaDataView.viewNode
      bottom = playerControlsView.viewNode
    }
  }


  private def createPageTwo(): Node = {
    equalizerView = new EqualizerView(songModel)
    equalizerView.onNextPageAction {
      (ae: ActionEvent) =>
        println("Got back button click")
        rootNode.content = page1
    }
    equalizerView.viewNode
  }


  private def initSceneDragAndDrop(scene: Scene) {
    scene.onDragOver = (event: DragEvent) => {
      val db = event.dragboard
      if (db.hasFiles || db.hasUrl) {
        // NOTE: We need to pass in `TransferMode` as Java vararg, since we use `javafx.scene.input.DragEvent`
        event.acceptTransferModes(jfxsi.TransferMode.ANY: _*)
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
        songModel.url = url
        songModel.mediaPlayer().play()
      }
      event.dropCompleted = (url != null)
      event.consume()
    }
  }
}
