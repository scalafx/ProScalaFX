package proscalafx.ch04.reversi.ui

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.*

object ReversiApp extends JFXApp3 {

  override def start(): Unit = {

    val reversi = new Reversi()

    stage = new PrimaryStage() {
      scene = new Scene(400, 600) {
        root = new AnchorPane() {
          children = List(
            reversi.game,
            reversi.restart
          )
        }
      }
    }

    AnchorPane.setTopAnchor(reversi.game, 0d)
    AnchorPane.setBottomAnchor(reversi.game, 0d)
    AnchorPane.setLeftAnchor(reversi.game, 0d)
    AnchorPane.setRightAnchor(reversi.game, 0d)
    AnchorPane.setRightAnchor(reversi.restart, 10d)
    AnchorPane.setTopAnchor(reversi.restart, 10d)

    // Code commended below was only used in the first edition of the book
    //  if (Platform.isSupported(ConditionalFeature.Scene3D)) {
    //    stage.scene().camera = new PerspectiveCamera() {
    //      fieldOfView = 60
    //    }.delegate
    //  }
  }
}
