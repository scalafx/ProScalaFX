package proscalafx.ch10.fxml

import java.net.URL
import java.util
import javafx.scene.{layout => jfxsl}
import javafx.{event => jfxe}
import javafx.{fxml => jfxf}
import scalafx.scene.layout.GridPane

/** Example of a controlled initialized through FXML.
  *
  * ScalaFX does not yet provide a wrapper for FXML functionality (2012.11.12).
  * we let FXMLLoader to create JavaFX delegates, in out case `gridDelegate`,
  * then wrap those delegates in ScalaFX in the `initialize` method, in our case `grid`.
  * In the rest of the code we can use ScalaFX, for instance in the event handlers.
  *
  * @author Jarek Sacha
  */
class AdoptionFormController extends jfxf.Initializable {

  @jfxf.FXML
  private var gridDelegate: jfxsl.GridPane = null
  private var grid: GridPane = _


  @jfxf.FXML
  private def handleSubmit(event: jfxe.ActionEvent) {
    grid.gridLinesVisible() = !grid.gridLinesVisible()
  }


  def initialize(url: URL, rb: util.ResourceBundle) {
    grid = new GridPane(gridDelegate)
  }
}
