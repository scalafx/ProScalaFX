package proscalafx.ch06

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.{binding => jfxbb}
import javafx.{concurrent => jfxc}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.concurrent.Task
import scalafx.event.ActionEvent
import scalafx.geometry.{HPos, Pos, Insets}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ProgressBar}
import scalafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, HBox}
import scalafx.stage.Stage


/**
 * @author Jarek Sacha
 */
object WorkerAndTaskExample extends JFXApp {

  hookupEvents()
  stage = new Stage {
    title = "Worker and Task Example"
    scene = view.scene
  }


  private def hookupEvents() {
    view.startButton.onAction = {ae: ActionEvent => new Thread(model.worker).start()}
    // NOTE: Implicit conversion for `onAction` requires that the handler return `Unit`.
    // In the handler wi call `cancel` that returns `Boolean`. To satisfy handler signature, we add empty block `{}`
    // at the end of action handler, which effectively makes the handler to return `Unit`. We could use any statement
    // that produces `Unit`, for instance, `println`.
    // Semicolon as added after `cancel` to make it explicit that the empty block is not a part of `cancel` invocation.
    view.cancelButton.onAction = {
      ae: ActionEvent => {
        model.worker.cancel;
        {/* return `Unit` */}
      }
    }
    view.exceptionButton.onAction = {ae: ActionEvent => model.shouldThrow.set(true)}
  }


  private object model {
    var shouldThrow = new AtomicBoolean(false)

    // NOTE: Object worker is created by extending `Task`.
    // ScalaFX `Task` cannot be directly instantiated since it is `abstract`, so we use `object` as a shortcut.
    // NOTE: ScalaFX `Task` is created by extending JavaFX `Task` that is passed to ScalaFX `Task` as the
    // delegate parameter (ScalaFX `Task` has no default constructor).
    object worker extends Task(new jfxc.Task[String] {

      protected def call(): String = {
        updateTitle("Example Task")
        updateMessage("Starting...")
        val total = 250
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
          updateTitle("Example Task (" + i + ")")
          updateMessage("Processed " + i + " of " + total + " items.")
          updateProgress(i, total)
        }

        "Completed at " + System.currentTimeMillis
      }

    })

  }

  private object view {
    val stateProperty = model.worker.state

    val progressBar = new ProgressBar() {
      minWidth = 250
      progress <== model.worker.progress
    }
    val title = new Label {
      text <== model.worker.title
    }
    val message = new Label {
      text <== model.worker.message
    }
    val running = new Label {
      text <== model.worker.running.asString()
    }
    val state = new Label {
      text <== jfxbb.Bindings.format("%s", stateProperty)
    }
    val totalWork = new Label {
      text <== model.worker.totalWork.asString()
    }
    val workDone = new Label {
      text <== model.worker.workDone.asString()
    }
    val progress = new Label {
      text <== (model.worker.progress * 100).asString("%5.2f%%")
    }
    val value = new Label {
      // NOTE: using delegate
      text <== model.worker.delegate.valueProperty()
    }
    val exception = new Label {
      text <== new jfxbb.StringBinding {
        super.bind(model.worker.exceptionProperty)

        protected def computeValue(): String = {
          val e = model.worker.exception()
          if (e == null) "" else e.getMessage
        }
      }
    }

    val startButton = new Button("Start") {
      disable <== stateProperty =!= jfxc.Worker.State.READY
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
      innerAlignment = Pos.CENTER
      content = progressBar
    }

    val centerPane = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(10)
      columnConstraints = List(new ColumnConstraints {
        halignment = HPos.RIGHT
        minWidth = 65
      },
        new ColumnConstraints {
          halignment = HPos.LEFT
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
      innerAlignment = Pos.CENTER
      content = List(
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
