package proscalafx.ch06

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.{binding => jfxbb}
import javafx.{concurrent => jfxc}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.IntegerProperty
import scalafx.concurrent.Service
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ProgressBar, TextField}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, HBox}


/**
 * @author Jarek Sacha
 */
object ServiceExample extends JFXApp {

  hookupEvents()
  stage = new PrimaryStage {
    title = "Service Example"
    scene = View.scene
  }


  private def hookupEvents() {
    View.startButton.onAction = {
      ae: ActionEvent =>
        Model.shouldThrow.set(false)
        Model.Worker.restart()
    }
    View.cancelButton.onAction = {ae: ActionEvent => Model.Worker.cancel}
    View.exceptionButton.onAction = {ae: ActionEvent => Model.shouldThrow.set(true)}
  }


  private object Model {
    val shouldThrow = new AtomicBoolean(false)
    val numberOfItems = new IntegerProperty(this, "numberOfItems", 250)

    // NOTE: Object worker is created by extending `Service`.
    // ScalaFX `Service` cannot be directly instantiated since it is `abstract`, so we use `object` as a shortcut.
    // NOTE: ScalaFX `Service` is created by extending JavaFX `Service` that is passed to  ScalaFX `Service` as the
    // delegate parameter (ScalaFX `Service` has no default constructor).
    object Worker extends Service(new jfxc.Service[String]() {

      protected def createTask(): jfxc.Task[String] = new jfxc.Task[String] {
        protected def call(): String = {
          updateTitle("Example Task")
          updateMessage("Starting...")
          val total: Int = numberOfItems()
          updateProgress(0, total)
          for (i <- 1 to total) {
            try {
              Thread.sleep(20)
            } catch {
              case e: InterruptedException => return "Canceled at " + System.currentTimeMillis
            }
            if (shouldThrow.get) {
              throw new RuntimeException("Exception thrown at " + System.currentTimeMillis)
            }
            updateTitle("Example Service (" + i + ")")
            updateMessage("Processed " + i + " of " + total + " items.")
            updateProgress(i, total)
          }

          "Completed at " + System.currentTimeMillis
        }
      }
    })

  }

  private object View {
    val stateProperty = Model.Worker.state

    val progressBar = new ProgressBar() {
      minWidth = 250
      progress <== Model.Worker.progress
    }
    val title = new Label {
      text <== Model.Worker.title
    }
    val message = new Label {
      text <== Model.Worker.message
    }
    val running = new Label {
      text <== Model.Worker.running.asString()
    }
    val state = new Label {
      // NOTE: we need to use delegate to get proper binding, without it the value of text will not change.
      text <== jfxbb.Bindings.format("%s", stateProperty.delegate)
    }
    val totalWork = new Label {
      text <== Model.Worker.totalWork.asString()
    }
    val workDone = new Label {
      text <== Model.Worker.workDone.asString()
    }
    val progress = new Label {
      text <== (Model.Worker.progress * 100).asString("%5.2f%%")
    }
    val value = new Label {
      text <== Model.Worker.value
    }
    val exception = new Label {
      text <== new jfxbb.StringBinding {
        super.bind(Model.Worker.exceptionProperty)

        protected def computeValue(): String = {
          val e = Model.Worker.exception()
          if (e == null) "" else e.getMessage
        }
      }
    }

    val numberOfItems = new TextField {
      maxWidth = 40
    }

    Model.numberOfItems <== new jfxbb.IntegerBinding {
      bind(numberOfItems.text)

      def computeValue(): Int = {
        val text = numberOfItems.text()
        var n = 250
        try {
          n = Integer.parseInt(text)
        }
        catch {
          case e: NumberFormatException =>
        }
        n
      }
    }


    val startButton = new Button("Start") {
      disable <== stateProperty === jfxc.Worker.State.RUNNING
    }
    val cancelButton = new Button("Cancel") {
      disable <== stateProperty =!= jfxc.Worker.State.RUNNING
    }
    val exceptionButton = new Button("Exception") {
      disable <== stateProperty =!= jfxc.Worker.State.RUNNING
    }


    val topPane = new HBox() {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = progressBar
    }

    val centerPane = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(10)
      columnConstraints = List(new ColumnConstraints {
        halignment = HPos.Right
        minWidth = 65
      },
        new ColumnConstraints {
          halignment = HPos.Left
          minWidth = 200
        }
      )

      add(new Label("Title:"), 0, 0)
      add(new Label("Message:"), 0, 1)
      add(new Label("Running:"), 0, 2)
      add(new Label("State:"), 0, 3)
      add(new Label("Total Work:"), 0, 4)
      add(new Label("Work Done:"), 0, 5)
      add(new Label("Progress:"), 0, 6)
      add(new Label("Value:"), 0, 7)
      add(new Label("Exception:"), 0, 8)
      add(title, 1, 0)
      add(message, 1, 1)
      add(running, 1, 2)
      add(state, 1, 3)
      add(totalWork, 1, 4)
      add(workDone, 1, 5)
      add(progress, 1, 6)
      add(value, 1, 7)
      add(exception, 1, 8)
    }
    val buttonPane = new HBox {
      padding = Insets(10)
      spacing = 10
      alignment = Pos.Center
      children = List(
        new Label("Process"),
        numberOfItems,
        new Label("items"),
        startButton,
        cancelButton,
        exceptionButton
      )
    }
    val scene = new Scene {
      root = new BorderPane {
        top = topPane
        center = centerPane
        bottom = buttonPane
      }
    }
  }

}
