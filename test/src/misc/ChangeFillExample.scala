package misc

import javafx.scene.{paint => jfxsp}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.geometry.{Pos, Insets}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle


/** Example illustrating problem with `ObjectProperty` holding a ScalaFX wrapper,
  * it cannot bind to component properties.
  * In example here we have to use JavaFX `Color` as value type for `ObjectProperty` to able to bind it
  * to `Rectangle#fill`.
  *
  * @author Jarek Sacha
  */
object ChangeFillExample extends JFXApp {

  // NOTE: We use here JavaFX Paint as the  type for `ObjectProperty`.
  // Without that we will have problems with binding `fillPaint` to `fill` in the `Rectangle`.
  // Compiler would throw:
  //   error: overloaded method value <== with alternatives:
  //   (v: scalafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint, _ <: javafx.scene.paint.Paint])Unit <and>
  //   (v: javafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint])Unit
  //   cannot be applied to (scalafx.beans.property.ObjectProperty[scalafx.scene.paint.Paint])
  //   fill <== fillPaint
  val fillPaint = new ObjectProperty[jfxsp.Paint](this, "fillPaint", Color.LIGHTGRAY)
  val light = jfxsp.Color.LIGHTGRAY
  val dark = jfxsp.Color.GRAY

  stage = new PrimaryStage {
    title = "Change Fill Example"
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(10)
        center = new Rectangle {
          width = 200
          height = 200
          // Binding here fails if `ObjectProperty` value type is ScalaFX color.
          fill <== fillPaint
        }
        bottom = new HBox {
          padding = Insets(10)
          innerAlignment = Pos.CENTER
          content = new Button {
            text = "Change Fill"
            onAction = (ae: ActionEvent) => fillPaint() = if (fillPaint() == light) dark else light
          }
        }
      }.delegate
    }
  }
}
