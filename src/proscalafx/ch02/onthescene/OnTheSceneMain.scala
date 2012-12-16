/**
 *
 */
package proscalafx.ch02.onthescene

import javafx.{scene => jfxs}
import javafx.scene.{text => jfxst}
import javafx.{geometry => jfxg}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.property.DoubleProperty
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.FlowPane
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.stage.Stage


/**
 * @author Rafael
 *
 */
object OnTheSceneMain extends JFXApp {

  val fillVals = DoubleProperty(255.0)

  val cursors = ObservableBuffer[jfxs.Cursor](
    jfxs.Cursor.DEFAULT,
    jfxs.Cursor.CROSSHAIR,
    jfxs.Cursor.WAIT,
    jfxs.Cursor.TEXT,
    jfxs.Cursor.HAND,
    jfxs.Cursor.MOVE,
    jfxs.Cursor.N_RESIZE,
    jfxs.Cursor.NE_RESIZE,
    jfxs.Cursor.E_RESIZE,
    jfxs.Cursor.SE_RESIZE,
    jfxs.Cursor.S_RESIZE,
    jfxs.Cursor.SW_RESIZE,
    jfxs.Cursor.W_RESIZE,
    jfxs.Cursor.NW_RESIZE,
    jfxs.Cursor.NONE
  )

  val sliderRef = new Slider {
    min = 0
    max = 255
    value = 255
    orientation = jfxg.Orientation.VERTICAL
  }

  val choiceRef = new ChoiceBox[jfxs.Cursor] {
    items = cursors
    // XXX: String converter used to remove "[SFX]" in begin of toString. 
    //However in selected item the prefix "[SFX]" appears.
    //    converter = StringConverter.toStringConverter((cursor: Cursor) => cursor.delegate.toString)
  }

  val textSceneX = new Text {
    styleClass = List("emphasized-text")
  }

  val textSceneY = new Text {
    styleClass = List("emphasized-text")
  }

  val textSceneW = new Text {
    styleClass = List("emphasized-text")
  }

  val textSceneH = new Text {
    styleClass = List("emphasized-text")
    id = "sceneHeightText"
  }
  val labelStageX = new Label {
    id = "stageX"
  }

  val labelStageY = new Label {
    id = "stageY"
  }

  val labelStageW = new Label
  val labelStageH = new Label

  val toggleGrp = new ToggleGroup

  val sceneRoot = new FlowPane {
    layoutX = 20
    layoutY = 40
    padding = Insets(0, 20, 40, 0)
    orientation = jfxg.Orientation.VERTICAL
    vgap = 10
    hgap = 20
    columnHalignment = jfxg.HPos.LEFT
    content = List(
      new HBox {
        spacing = 10
        content = List(sliderRef, choiceRef)
      },
      textSceneX,
      textSceneY,
      textSceneW,
      textSceneH,
      new Hyperlink {
        text = "lookup()"
        onAction = (ae: ActionEvent) => {
          println("sceneRef:" + stage.scene.get)
          val textRef: Text = stage.scene.get.lookup("#sceneHeightText").asInstanceOf[jfxst.Text]
          println(textRef.text.get)
        }
      },
      new RadioButton {
        text = "onTheScene.css"
        toggleGroup = toggleGrp
        selected = true
      },
      new RadioButton {
        text = "changeOfScene.css"
        toggleGroup = toggleGrp
      },
      labelStageX,
      labelStageY,
      labelStageW,
      labelStageH)
  }

  val sceneRef = new Scene(600, 250) {
    root = sceneRoot
    stylesheets = List(this.getClass.getResource("onTheScene.css").toExternalForm)
  }

  stage = new Stage {
    title = "On the Scene"
    scene = sceneRef
  }

  choiceRef.selectionModel.get.selectFirst

  // Setup various property binding
  textSceneX.text <== new StringProperty("Scene x: ") + sceneRef.x.asString
  textSceneY.text <== new StringProperty("Scene y: ") + sceneRef.y.asString
  textSceneW.text <== new StringProperty("Scene width: ") + sceneRef.width.asString
  textSceneH.text <== new StringProperty("Scene height: ") + sceneRef.height.asString
  labelStageX.text <== new StringProperty("Stage x: ") + sceneRef.window.get.x.asString
  labelStageY.text <== new StringProperty("Stage y: ") + sceneRef.window.get.y.asString
  labelStageW.text <== new StringProperty("Stage width: ") + sceneRef.window.get.width.asString
  labelStageH.text <== new StringProperty("Stage height: ") + sceneRef.window.get.height.asString
  sceneRef.cursor <== choiceRef.value
  fillVals <== sliderRef.value

  // When fillVals changes, use that value as the RGB to fill the scene
  fillVals.onChange({
    val fillValue: Double = fillVals.get / 256.0
    sceneRef.fill = Color(fillValue, fillValue, fillValue, 1.0)
  })

  // When the selected radio button changes, set the appropriate stylesheet
  toggleGrp.selectedToggle.onChange({
    val radioButtonText = toggleGrp.selectedToggle.get.asInstanceOf[javafx.scene.control.RadioButton].text
    sceneRef.stylesheets = List(this.getClass.getResource(radioButtonText.get).toExternalForm)
  })

  // Define an unmanaged node that will display Text 
  val addedTextRef = new Text {
    layoutX = 0
    layoutY = -30
    textOrigin = jfxg.VPos.TOP
    fill = Color.BLUE
    font = Font.font("Sans Serif", jfxst.FontWeight.BOLD, 16)
    managed = false
    text <== new StringProperty("Scene fill: ") + sceneRef.fill
  }

  // Add to the Text node to the FlowPane. 
  // TODO: Use ScalaFX notation. 
  sceneRef.content.add(addedTextRef)

}