package proscalafx.ch06

import javafx.scene.{paint => jfxsp}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{Platform, JFXApp}
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.geometry.{Pos, Insets}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle


/**
 * @author Jarek Sacha
 */
object ResponsiveUIExample extends JFXApp {

  hookupEvents()

  stage = new PrimaryStage {
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
    // `fill` and `stroke` are created using ObjectProperty factory method to ensure proper type parameter
    // to ObjectProperty. We use here, implicitly, JavaFX Paint as the  type for `ObjectProperty`.
    val fillPaint = ObjectProperty(this, "fillPaint", Color.LIGHTGRAY)
    val strokePaint = ObjectProperty(this, "strokePaint", Color.DARKGRAY)
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
      alignment = Pos.CENTER
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
