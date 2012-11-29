package sfxext

import javafx.scene.{input => jfxsi}
import javafx.{event => jfxe}
import scalafx.Includes._
import scalafx.scene.{input => sfxsi}

object Includes extends Includes

/**
 * Fixes for includes/explicit conversions missing in ScalaFX
 * @author Jarek Sacha 
 */
trait Includes {

  /**
   * Converts a Function that manipulates a [[scalafx.scene.input.DragEvent]]
   * and returns a [[scala.Unit]] in a
   * [[http://docs.oracle.com/javafx/2/api/javafx/event/EventHandler.html JavaFX`s EventHandler]]
   * that manipulates a
   * [[http://docs.oracle.com/javafx/2/api/javafx/scene/input/DragEvent.html JavaFX`s DragEvent]]
   *
   * @param handler function that manipulates a ScalaFX's DragEvent
   * @return a JavaFX's EventHandler that manipulates a JavaFX's DragEvent
   */
  implicit def dragEventClosureWrapper(handler: (sfxsi.DragEvent) => Unit) = new jfxe.EventHandler[jfxsi.DragEvent] {
    def handle(event: jfxsi.DragEvent) {
      handler(event)
    }
  }
}
