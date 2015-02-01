package proscalafx.ch06

import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
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
    scene = View.scene
  }


  def hookupEvents() {
    View.changeFillButton.onAction = {
      (ae: ActionEvent) => {
        val fillPaint = Model.fillPaint()
        Model.fillPaint() = if (Color.LightGray == fillPaint) Color.Gray else Color.LightGray

        val task = new Runnable {
          def run() {
            try {
              Thread.sleep(3000)
              Platform.runLater {
                val rect = View.rectangle
                val newArcSize = if (rect.arcHeight() < 20) 30 else 0
                rect.arcWidth() = newArcSize
                rect.arcHeight() = newArcSize
              }
            } catch {
              case e: InterruptedException => /* Properly handle exception */
            }
          }
        }
        new Thread(task).start()
      }
    }

    View.changeStrokeButton.onAction = {
      (ae: ActionEvent) => {
        val strokePaint = Model.strokePaint()
        Model.strokePaint() = if (strokePaint == Color.DarkGray) Color.Black else Color.DarkGray
      }
    }
  }


  private object Model {
    // `fill` and `stroke` are created using ObjectProperty factory method to ensure proper type parameter
    // to ObjectProperty. We use here, implicitly, JavaFX Paint as the  type for `ObjectProperty`.
    val fillPaint = ObjectProperty(this, "fillPaint", Color.LightGray)
    val strokePaint = ObjectProperty(this, "strokePaint", Color.DarkGray)
  }


  private object View {
    val rectangle = new Rectangle {
      width = 200
      height = 200
      strokeWidth = 10
      fill <== Model.fillPaint
      stroke <== Model.strokePaint
    }

    val changeFillButton = new Button("Change Fill")
    val changeStrokeButton = new Button("Chang Stroke")

    val buttonHBox = new HBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
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
