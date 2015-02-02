package proscalafx.ch10.fxml

import java.net.URL
import java.util
import javafx.scene.{control => jfxsc}
import javafx.scene.{layout => jfxsl}
import javafx.{event => jfxe}
import javafx.{fxml => jfxf}
import scalafx.Includes._
import scalafx.scene.layout.GridPane

/** Example of a controlled initialized through FXML.
  *
  * When working with FXML, due to the nature of JavaFX FXMLLoader, we need to expose variables and methods that
  * FXMLLoader will be using with JavaFX signatures.
  *
  * The FXMLLoader injects JavaFX objects as values of member variables marked with annotation `@jfxf.FXML`.
  * We need to declare those variables using JavaFX types (not ScalaFX types).
  * We can use those variables directly or wrap them in ScalaFX objects.
  * Here, for the sake of illustration, we only wrap one variable `gridDelegate` (it is not strictly necessary).
  * The most convenient place to do wrapping is in the overloaded method `initialize`. It is executed after
  * FXMLLoader injects its objects.
  *
  * We can rely on ScalaFX "magic" to use ScalaFX methods on variables that were not explicitly wrapped.
  * All we need to do is to "summon the magic" using "import scalafx.Includes._".
  * This is demonstrated in method "handleClear" where we access properties on
  * JavaFX objects using ScalaFX way, no `get` or `set` involved.
  *
  * Methods annotated with `@jfxf.FXML`, that will be wired to event handlers by FLXMLoader.
  * They need to use JavaFX method signatures. This is illustrated in methods: `handleSubmit` and  `handleClear`.
  *
  * In the rest of the code we can use ScalaFX, for instance, to create more in the event handlers or bind
  * properties.
  *
  * @author Jarek Sacha
  */
class AdoptionFormController extends jfxf.Initializable {

  @jfxf.FXML
  private var sizeTextField: jfxsc.TextField = _
  @jfxf.FXML
  private var breedTextField: jfxsc.TextField = _
  @jfxf.FXML
  private var sexChoiceBox: jfxsc.ChoiceBox[String] = _
  @jfxf.FXML
  private var additionalInfoTextArea: jfxsc.TextArea = _

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = _
  private var grid: GridPane = _


  @jfxf.FXML
  private def handleSubmit(event: jfxe.ActionEvent) {
    grid.gridLinesVisible() = !grid.gridLinesVisible()
  }

  @jfxf.FXML
  private def handleClear(event: jfxe.ActionEvent) {
    sizeTextField.text = ""
    breedTextField.text = ""
    sexChoiceBox.selectionModel().clearSelection()
    additionalInfoTextArea.text = ""
  }

  def initialize(url: URL, rb: util.ResourceBundle) {
    grid = new GridPane(gridDelegate)
  }
}
