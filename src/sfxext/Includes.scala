package sfxext

import javafx.scene.{media => jfxsm}
import javafx.{event => jfxe}
import scalafx.Includes._
import scalafx.scene.{media => sfxsm}

object Includes extends Includes


/**
 * Fixes for includes/explicit conversions missing in ScalaFX
 * @author Jarek Sacha 
 */
trait Includes {

  /**
   * Converts a Function that manipulates a [[scalafx.scene.media.MediaMarkerEvent]]
   * and returns a [[scala.Unit]] in a
   * [[http://docs.oracle.com/javafx/2/api/javafx/event/EventHandler.html JavaFX`s EventHandler]]
   * that manipulates a
   * [[http://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaMarkerEvent.html JavaFX`s MediaMarkerEvent]]
   *
   * @param handler function that manipulates a ScalaFX's MediaMarkerEvent
   * @return a JavaFX's EventHandler that manipulates a JavaFX's MediaMarkerEvent
   */
  implicit def mediaMarkerEventClosureWrapper(handler: (sfxsm.MediaMarkerEvent) => Unit) = new jfxe.EventHandler[jfxsm.MediaMarkerEvent] {
    def handle(event: jfxsm.MediaMarkerEvent) {
      handler(event)
    }
  }
}
