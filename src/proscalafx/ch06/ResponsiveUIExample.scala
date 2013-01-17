package proscalafx.ch06

import javafx.scene.{paint => jfxsp}
import scalafx.Includes._
import scalafx.application.{Platform, JFXApp}
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.geometry.{Pos, Insets}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.Stage


/**
 * @author Jarek Sacha
 */
object ResponsiveUIExample extends JFXApp {

  hookupEvents()

  stage = new Stage {
    title = "Unresponsive UI Example"
    scene = view.scene
  }


  def hookupEvents() {
    view.changeFillButton.onAction = {
      (ae: ActionEvent) => {
        val fillPaint = model.fillPaint()
        model.fillPaint() = if (Color.LIGHTGRAY == fillPaint) Color.GRAY else Color.LIGHTGRAY

        val task = new Runnable {
          def run() {
            try {
              Thread.sleep(3000)
              Platform.runLater {
                val rect = view.rectangle
                val newArcSize = if (rect.arcHeight() < 20) 30 else 0
                rect.arcWidth() = newArcSize
                rect.arcHeight() = newArcSize
              }
            } catch {
              case e: InterruptedException => {/* Properly handle exception */}
            }
          }
        }
        new Thread(task).start()
      }
    }

    view.changeStrokeButton.onAction = {
      (ae: ActionEvent) => {
        val strokePaint = model.strokePaint()
        model.strokePaint() = if (strokePaint == jfxsp.Color.DARKGRAY) jfxsp.Color.BLACK else jfxsp.Color.DARKGRAY
      }
    }
  }


  private object model {
    // NOTE: We use here JavaFX Paint as the  type for `ObjectProperty` (2012.11.17).
    // Without that we will have problems with binding those to `fill` and `stroke` in the `View`.
    // Compiler would throw:
    //   error: overloaded method value <== with alternatives:
    //   (v: scalafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint, _ <: javafx.scene.paint.Paint])Unit <and>
    //   (v: javafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint])Unit
    //   cannot be applied to (scalafx.beans.property.ObjectProperty[scalafx.scene.paint.Paint])
    //   fill <== Model.fillPaint
    val fillPaint = new ObjectProperty[jfxsp.Paint](this, "fillPaint", Color.LIGHTGRAY)
    val strokePaint = new ObjectProperty[jfxsp.Paint](this, "strokePaint", Color.DARKGRAY)
  }


  private object view {
    val rectangle = new Rectangle {
      width = 200
      height = 200
      strokeWidth = 10
      fill <== model.fillPaint
      stroke <== model.strokePaint
    }

    val changeFillButton = new Button("Change Fill")
    val changeStrokeButton = new Button("Chang Stroke")

    val buttonHBox = new HBox {
      padding = Insets(10)
      spacing = 10
      innerAlignment = Pos.CENTER
      content = List(
        changeFillButton,
        changeStrokeButton
      )
    }

    val scene = new Scene {
      root = new BorderPane {
        padding = Insets(10)
        center = rectangle
        bottom = buttonHBox
      }
    }
  }

}
