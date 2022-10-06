package proscalafx.ch06

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView, TextArea}
import scalafx.scene.layout.VBox

import scala.jdk.CollectionConverters.*

/**
 * ScalaFX version of `JavaFXThreadsExample` from "Pro JavaFX 2" book.
 *
 * @author Jarek Sacha
 */
object JavaFXThreadsExample extends JFXApp3 {

  class Model {
    val threadNames = new ObservableBuffer[String]()
    val stackTraces = new ObservableBuffer[String]()
    update()

    def update(): Unit = {
      threadNames.clear()
      stackTraces.clear()
      val map = Thread.getAllStackTraces.asScala
      for ((k, v) <- map) {
        threadNames += "\"" + k.getName + "\""
        stackTraces += formatStackTrace(v)
      }
    }

    private def formatStackTrace(v: Array[StackTraceElement]): String = {
      val sb = new StringBuilder("StackTrace: \n")
      for (stackTraceElement <- v) {
        sb ++= "    at " ++= stackTraceElement.toString ++= "\n"
      }
      sb.toString()
    }
  }

  class View(model: Model) {
    val threadNames  = new ListView(model.threadNames)
    val stackTrace   = new TextArea()
    val updateButton = new Button("Update")
    val scene = new Scene(440, 640) {
      root = new VBox {
        spacing = 10
        padding = Insets(10)
        children = List(
          threadNames,
          stackTrace,
          updateButton
        )
      }
    }
  }

  override def start(): Unit = {

    val model = new Model()
    val view  = new View(model)

    hookupEvents()
    stage = new PrimaryStage {
      title = "JavaFX Threads Information"
      scene = view.scene
    }

    def hookupEvents(): Unit = {
      view.updateButton.onAction = () => model.update()
      view.threadNames.selectionModel().selectedItem.onChange {
        val index = view.threadNames.selectionModel().getSelectedIndex
        if (index >= 0) {
          view.stackTrace.text = model.stackTraces(index)
        }
      }
    }
  }
}
