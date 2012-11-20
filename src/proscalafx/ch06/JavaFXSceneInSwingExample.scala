package proscalafx.ch06

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, FlowLayout, Dimension}
import javafx.embed.{swing => jfxes}
import javafx.scene.{paint => jfxsp}
import javafx.{application => jfxa}
import javax.swing.{JButton, JPanel, JFrame}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle


/**
 * @author Jarek Sacha
 */
object JavaFXSceneInSwingExample extends App {

  private val model = new Model()
  new Controller(model, new View(model)).mainLoop()


  private class Model {
    // NOTE: We use here JavaFX Paint as the  type for `ObjectProperty` (2012.11.17).
    // Without that we will have problems with binding those to `fill` and `stroke` in the `View`.
    // Compiler would throw:
    //   error: overloaded method value <== with alternatives:
    //   (v: scalafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint, _ <: javafx.scene.paint.Paint])Unit <and>
    //   (v: javafx.beans.value.ObservableValue[_ <: javafx.scene.paint.Paint])Unit
    //   cannot be applied to (scalafx.beans.property.ObjectProperty[scalafx.scene.paint.Paint])
    //   fill <== Model.fillPaint
    val fill = new ObjectProperty[jfxsp.Paint](this, "fillPaint", Color.LIGHTGRAY)
    val stroke = new ObjectProperty[jfxsp.Paint](this, "strokePaint", Color.DARKGRAY)
  }


  private class View(model: Model) {
    val frame = new JFrame("ScalaFX in Swing Example")
    // NOTE: ScalaFX does not currently implement JFXPanel, so we use JavaFX/Swing directly.
    val canvas = new jfxes.JFXPanel() {
      setPreferredSize(new Dimension(210, 210))
    }
    // Execute JavaFX code on JavaFX Application Thread.
    sfxext.application.Platform.runLater {
      val rectangle = new Rectangle {
        width = 200
        height = 200
        strokeWidth = 10
        fill <== model.fill
        stroke <== model.stroke
      }

      canvas.setScene(new Scene {
        root = new VBox {
          content = List(rectangle)
        }.delegate
      }.delegate)
    }

    val canvasPanelLayout = new FlowLayout(FlowLayout.CENTER, 10, 10)
    val canvasPanel = new JPanel(canvasPanelLayout)
    canvasPanel.add(canvas)
    val changeFillButton = new JButton("Change Fill")
    val changeStrokeButton = new JButton("Change Stroke")
    val buttonPanelLayout = new FlowLayout(FlowLayout.CENTER, 10, 10)
    val buttonPanel = new JPanel(buttonPanelLayout)
    buttonPanel.add(changeFillButton)
    buttonPanel.add(changeStrokeButton)
    frame.add(canvasPanel, BorderLayout.CENTER)
    frame.add(buttonPanel, BorderLayout.SOUTH)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setLocationByPlatform(true)
    frame.pack()
  }


  private class Controller(model: Model, view: View) {
    view.changeFillButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        sfxext.application.Platform.runLater {
          model.fill() = if (model.fill() == jfxsp.Color.LIGHTGRAY) jfxsp.Color.GRAY else jfxsp.Color.LIGHTGRAY
        }
      }
    })
    view.changeStrokeButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        sfxext.application.Platform.runLater {
          model.stroke() = if (model.stroke() == jfxsp.Color.DARKGRAY) jfxsp.Color.BLACK else jfxsp.Color.DARKGRAY
        }
      }
    })

    def mainLoop() {
      view.frame.setVisible(true)
    }
  }

}
