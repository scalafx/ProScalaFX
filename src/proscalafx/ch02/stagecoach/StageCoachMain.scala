/**
 *
 */
package proscalafx.ch02.stagecoach

import javafx.geometry.VPos
import javafx.stage.StageStyle
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.property.ReadOnlyDoubleProperty.sfxReadOnlyDoubleProperty2jfx
import scalafx.beans.property.StringProperty
import scalafx.geometry.Rectangle2D
import scalafx.scene.Group
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.CheckBox
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.sfxColor2jfx
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scalafx.stage.Screen
import scalafx.stage.Stage


/**
 * @author Rafael
 *
 */
object StageCoachMain extends JFXApp {

  val titleProperty: StringProperty = ""

  val stageStyle = parameters.unnamed match {
    case Seq("transparent") => StageStyle.TRANSPARENT
    case Seq("undecorated") => StageStyle.UNDECORATED
    case Seq("utility") => StageStyle.UTILITY
    case _ => StageStyle.DECORATED
  }

  lazy val textStageX = new Text {
    textOrigin = VPos.TOP
  }
  lazy val textStageY = new Text {
    textOrigin = VPos.TOP
  }
  lazy val textStageW = new Text {
    textOrigin = VPos.TOP
  }
  lazy val textStageH = new Text {
    textOrigin = VPos.TOP
  }
  lazy val textStageF = new Text {
    textOrigin = VPos.TOP
  }
  lazy val checkBoxResizable = new CheckBox {
    text = "resizable"
    disable = (stageStyle == StageStyle.TRANSPARENT ||
      stageStyle == StageStyle.UNDECORATED)
  }
  lazy val checkBoxFullScreen = new CheckBox {
    text = "fullScreen"
  }
  lazy val titleTextField = new TextField {
    text = "Stage Coach"
    prefColumnCount = 15
  }

  stage = new Stage {
    resizable = false
    title <== titleProperty
    scene = new Scene(270, 370) {
      fill = Color.TRANSPARENT
      content = new Group {
        children = List(
          new Rectangle {
            width = 250
            height = 350
            arcWidth = 50
            arcHeight = 50
            fill = Color.SKYBLUE
          },
          new VBox {
            layoutX = 30
            layoutY = 20
            spacing = 10
            content = List(
              textStageX,
              textStageY,
              textStageW,
              textStageH,
              textStageF,
              checkBoxResizable,
              checkBoxFullScreen,
              new HBox {
                spacing = 10
                content = List(
                  new Label {
                    text = "title:"
                  },
                  titleTextField)
              },
              new Button {
                text = "toBack()"
                onAction = stage.toBack
              },
              new Button {
                text = "toFront()"
                onAction = stage.toFront
              },
              new Button {
                text = "close()"
                onAction = stage.close
              })
          })
      }
    }
  }

  //when mouse button is pressed, save the initial position of screen
  val rootGroup = stage.scene.get.content(0)
  var dragAnchorX = 0.0
  var dragAnchorY = 0.0
  rootGroup.onMousePressed = (me: MouseEvent) => {
    dragAnchorX = me.screenX - stage.x.value
    dragAnchorY = me.screenY - stage.y.value
  }
  rootGroup.onMouseDragged = (me: MouseEvent) => {
    stage.x = me.screenX - dragAnchorX
    stage.y = me.screenY - dragAnchorY
  }

  textStageX.text <== new StringProperty("x: ") + stage.x.asString
  textStageY.text <== new StringProperty("y: ") + stage.y.asString
  textStageW.text <== new StringProperty("width: ") + stage.width.asString
  textStageH.text <== new StringProperty("height: ") + stage.height.asString
  textStageF.text <== new StringProperty("focused: ") + stage.focused.asString
  stage.resizable = false
  // NOTE: Due to a bug in JavaFX (2.2.3) Stage.resizableProperty(), cannot directly use binding here,
  // see http://javafx-jira.kenai.com/browse/RT-25942
  // TODO: Revert to binding once JavaFX bug is corrected
  //  stage.resizable <==> checkBoxResizable.selected
  checkBoxResizable.selected.onChange {
    // To avoid using resizableProperty, use delegate.setResizable()
    // stage.resizable = checkBoxResizable.selected.get
    stage.delegate.setResizable(checkBoxResizable.selected.get)
  }
  checkBoxFullScreen.onAction = {stage.fullScreen = checkBoxFullScreen.selected.get}
  stage.title <== titleTextField.text

  stage.initStyle(stageStyle)
  stage.onCloseRequest = println("Stage is closing")
  val primScreenBounds: Rectangle2D = Screen.primary.visualBounds
  stage.x = (primScreenBounds.width - stage.width.get) / 2
  stage.y = (primScreenBounds.height - stage.height.get) / 2

}