package proscalafx.ch10.fxml

import java.io.IOException
import javafx.{fxml => jfxf}
import javafx.{scene => jfxs}
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.stage.Stage

/** Example of using FXMLLoader from ScalaFX.
  *
  * @author Jarek Sacha
  */
object FXMLAdoptionForm extends JFXApp {

  val resource = getClass.getResource("AdoptionForm.fxml")
  if (resource == null) {
    throw new IOException("Cannot load resource: AdoptionForm.fxml")
  }

  // NOTE: ScalaFX doe not yet provide a wrapper fro FXMLLoader (2012.11.12)
  // We load here FXML content using JavaFX directly.
  // It is important to provide type for the element loaded,
  // though it can be a generic, here use `javafx.scene.parent`.
  val root: jfxs.Parent = jfxf.FXMLLoader.load(resource)

  stage = new Stage() {
    title = "FXML GridPane Demo"
    scene = new Scene(root)
  }

}
