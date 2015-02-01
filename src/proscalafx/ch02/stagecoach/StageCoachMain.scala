package proscalafx.ch02.stagecoach

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.geometry.VPos
import scalafx.scene.control.{Button, CheckBox, Label, TextField}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scalafx.scene.{Group, Scene}
import scalafx.stage.{Screen, StageStyle}


/** Stage property example.
  *
  * Can be run with various command line parameters to control stage style:
  * decorated - a solid white background and platform decorations (default).
  * transparent - transparent background and no decorations.
  * undecorated - a solid white background and no decorations.
  * utility - a solid white background and minimal platform decorations used for a utility window.
  * @author Rafael
  */
object StageCoachMain extends JFXApp {

  val titleProperty = StringProperty("")

  // Process command line parameters
  val stageStyle = parameters.unnamed match {
    case Seq("transparent") => StageStyle.TRANSPARENT
    case Seq("undecorated") => StageStyle.UNDECORATED
    case Seq("utility") => StageStyle.UTILITY
    case _ => StageStyle.DECORATED
  }

  lazy val textStageX = new Text {
    textOrigin = VPos.Top
  }
  lazy val textStageY = new Text {
    textOrigin = VPos.Top
  }
  lazy val textStageW = new Text {
    textOrigin = VPos.Top
  }
  lazy val textStageH = new Text {
    textOrigin = VPos.Top
  }
  lazy val textStageF = new Text {
    textOrigin = VPos.Top
  }
  lazy val checkBoxResizable = new CheckBox {
    text = "resizable"
    disable = stageStyle == StageStyle.TRANSPARENT || stageStyle == StageStyle.UNDECORATED
  }
  lazy val checkBoxFullScreen = new CheckBox {
    text = "fullScreen"
  }
  lazy val titleTextField = new TextField {
    text = "Stage Coach"
    prefColumnCount = 15
  }

  stage = new PrimaryStage {
    resizable = false
    title <== titleProperty
    scene = new Scene(270, 370) {
      fill = Color.Transparent
      content = new Group {
        children = List(
          new Rectangle {
            width = 250
            height = 350
            arcWidth = 50
            arcHeight = 50
            fill = Color.SkyBlue
          },
          new VBox {
            layoutX = 30
            layoutY = 20
            spacing = 10
            children = List(
              textStageX,
              textStageY,
              textStageW,
              textStageH,
              textStageF,
              checkBoxResizable,
              checkBoxFullScreen,
              new HBox {
                spacing = 10
                children = List(
                  new Label("title:"),
                  titleTextField)
              },
              new Button {
                text = "toBack()"
                onAction = handle {stage.toBack()}
              },
              new Button {
                text = "toFront()"
                onAction = handle {stage.toFront()}
              },
              new Button {
                text = "close()"
                onAction = handle {stage.close()}
              }
            )
          }
        )
      }
    }
  }

  //when mouse button is pressed, save the initial position of screen
  val rootGroup = stage.scene().content(0)
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
  // NOTE: Due to a bug in JavaFX (2.2.3+) Stage.resizableProperty(), cannot directly use binding here,
  // see http://javafx-jira.kenai.com/browse/RT-25942
  // TODO: Revert to binding once JavaFX bug is corrected
  //    stage.resizable <==> checkBoxResizable.selected
  checkBoxResizable.selected.onChange {
    // To avoid using resizableProperty, use delegate.setResizable()
    // stage.resizable = checkBoxResizable.selected.get
    stage.delegate.setResizable(checkBoxResizable.selected())
  }
  checkBoxFullScreen.onAction = handle {
    stage.fullScreen = checkBoxFullScreen.selected()
  }
  stage.title <== titleTextField.text

  stage.initStyle(stageStyle)
  stage.onCloseRequest = handle {println("Stage is closing")}
  val primScreenBounds = Screen.primary.visualBounds
  stage.x = (primScreenBounds.width - stage.width()) / 2
  stage.y = (primScreenBounds.height - stage.height()) / 2
}