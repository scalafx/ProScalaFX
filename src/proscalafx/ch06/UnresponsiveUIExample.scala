package proscalafx.ch06

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/**
 * @author Jarek Sacha
 */
object UnresponsiveUIExample extends JFXApp3 {

  override def start(): Unit = {

    hookupEvents()

    stage = new PrimaryStage {
      title = "Unresponsive UI Example"
      scene = View.scene
    }
  }

  def hookupEvents(): Unit = {
    View.changeFillButton.onAction = () => {
      val fillPaint = Model.fillPaint()
      Model.fillPaint() = if (fillPaint == Color.LightGray.delegate) Color.Gray else Color.LightGray
      // Bad code that will cause the UI to be unresponsive
      try {
        Thread.sleep(Long.MaxValue)
      } catch {
        case _: InterruptedException => /* Properly handle exception */
      }
    }

    View.changeStrokeButton.onAction = () => {
      val strokePaint = Model.strokePaint()
      Model.strokePaint() = if (strokePaint == Color.DarkGray.delegate) Color.Black else Color.DarkGray
    }
  }

  private object Model {
    // `fill` and `stroke` are created using ObjectProperty factory method to ensure proper type parameter
    // to ObjectProperty. We use here, implicitly, JavaFX Paint as the  type for `ObjectProperty`.
    val fillPaint   = ObjectProperty(this, "fillPaint", Color.LightGray)
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

    val changeFillButton   = new Button("Change Fill")
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
