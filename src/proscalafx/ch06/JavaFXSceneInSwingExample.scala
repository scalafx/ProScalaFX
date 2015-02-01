package proscalafx.ch06

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Dimension, FlowLayout}
import javafx.embed.{swing => jfxes}
import javafx.scene.{paint => jfxsp}
import javax.swing.{JButton, JFrame, JPanel}

import scalafx.application.Platform
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
    // `fill` and `stroke` are created using ObjectProperty factory method to ensure proper type parameter
    // to ObjectProperty. We use here, implicitly, JavaFX Paint as the  type for `ObjectProperty`.
    val fill = ObjectProperty(this, "fillPaint", Color.LightGray)
    val stroke = ObjectProperty(this, "strokePaint", Color.DarkGray)
  }


  private class View(model: Model) {
    val frame = new JFrame("ScalaFX in Swing Example")
    // NOTE: ScalaFX does not currently implement JFXPanel, so we use JavaFX/Swing directly.
    val canvas = new jfxes.JFXPanel() {
      setPreferredSize(new Dimension(210, 210))
    }
    // Execute JavaFX code on JavaFX Application Thread.
    Platform.runLater {
      val rectangle = new Rectangle {
        width = 200
        height = 200
        strokeWidth = 10
        fill <== model.fill
        stroke <== model.stroke
      }

      canvas.setScene(new Scene {
        root = new VBox {
          children = List(rectangle)
        }
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
        Platform.runLater {
          model.fill() = if (model.fill() == Color.LightGray) Color.Gray else Color.LightGray
        }
      }
    })
    view.changeStrokeButton.addActionListener(new ActionListener {
      def actionPerformed(e: ActionEvent) {
        Platform.runLater {
          model.stroke() = if (model.stroke() == Color.DarkGray) Color.Black else Color.DarkGray
        }
      }
    })

    def mainLoop() {
      view.frame.setVisible(true)
    }
  }

}
