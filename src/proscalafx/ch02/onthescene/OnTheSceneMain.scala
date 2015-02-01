package proscalafx.ch02.onthescene

import javafx.scene.{control => jfxsc, text => jfxst}
import javafx.{scene => jfxs}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.{DoubleProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Insets, Orientation, VPos}
import scalafx.scene.control._
import scalafx.scene.layout.{FlowPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight, Text}
import scalafx.scene.{Cursor, Scene}


/**
 * @author Rafael
 */
object OnTheSceneMain extends JFXApp {

  val fillVals = DoubleProperty(255.0)

  // NOTE: The buffer has a JFX value type due to ScalaFX Issue 14:
  // "ObjectProperty holding a ScalaFX wrapper cannot bind to ScalaFX control's properties"
  // We will nedd to bind, in the code below, the buffer to Scene's cursor property.
  val cursors = ObservableBuffer[jfxs.Cursor](
    Cursor.DEFAULT,
    Cursor.CROSSHAIR,
    Cursor.WAIT,
    Cursor.TEXT,
    Cursor.HAND,
    Cursor.MOVE,
    Cursor.N_RESIZE,
    Cursor.NE_RESIZE,
    Cursor.E_RESIZE,
    Cursor.SE_RESIZE,
    Cursor.S_RESIZE,
    Cursor.SW_RESIZE,
    Cursor.W_RESIZE,
    Cursor.NW_RESIZE,
    Cursor.NONE
  )

  val sliderRef = new Slider {
    min = 0
    max = 255
    value = 255
    orientation = Orientation.VERTICAL
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

  val labelStageW = new Label()
  val labelStageH = new Label()

  val toggleGrp = new ToggleGroup()

  val sceneRoot = new FlowPane {
    layoutX = 20
    layoutY = 40
    padding = Insets(0, 20, 40, 0)
    orientation = Orientation.VERTICAL
    vgap = 10
    hgap = 20
    columnHalignment = HPos.Left
    children = List(
      new HBox {
        spacing = 10
        children = List(sliderRef, choiceRef)
      },
      textSceneX,
      textSceneY,
      textSceneW,
      textSceneH,
      new Hyperlink {
        text = "lookup()"
        onAction = (ae: ActionEvent) => {
          println("sceneRef:" + stage.scene())
          val textRef = stage.scene().lookup("#sceneHeightText").asInstanceOf[jfxst.Text]
          println(textRef.text())
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
      labelStageH
    )
  }

  val sceneRef = new Scene(600, 250) {
    root = sceneRoot
    stylesheets = List(this.getClass.getResource("onTheScene.css").toExternalForm)
  }

  stage = new PrimaryStage {
    title = "On the Scene"
    scene = sceneRef
  }

  choiceRef.selectionModel().selectFirst()

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
    val fillValue: Double = fillVals() / 256.0
    sceneRef.fill = Color(fillValue, fillValue, fillValue, 1.0)
  })

  // When the selected radio button changes, set the appropriate stylesheet
  toggleGrp.selectedToggle.onChange {
    val radioButtonText = toggleGrp.selectedToggle().asInstanceOf[jfxsc.RadioButton].text()
    sceneRef.stylesheets = List(this.getClass.getResource(radioButtonText).toExternalForm)
  }

  // Define an unmanaged node that will display Text 
  val addedTextRef = new Text {
    layoutX = 0
    layoutY = -30
    textOrigin = VPos.Top
    fill = Color.Blue
    font = Font.font("Sans Serif", FontWeight.Bold, 16)
    managed = false
    text <== new StringProperty("Scene fill: ") + sceneRef.fill
  }

  // Add to the Text node to the FlowPane. 
  sceneRef.content += addedTextRef
}