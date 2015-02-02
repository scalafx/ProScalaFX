package proscalafx.ch01.audioconfig

import scalafx.beans.property.{BooleanProperty, IntegerProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.SingleSelectionModel

class AudioConfigModel {

  /** The minimum audio volume in decibels */
  val minDecibels = 0.0

  /** The maximum audio volume in decibels */
  val maxDecibels = 160.0

  /** The selected audio volume in decibels */
  val selectedDBs = IntegerProperty(0)

  /** Indicates whether audio is muted */
  val muting = BooleanProperty(false)

  /**
   * List of some musical genres
   */
  val genres = ObservableBuffer("Chamber",
    "Country",
    "Cowbell",
    "Metal",
    "Polka",
    "Rock"
  )

  /** A reference to the selection model used by the Slider */
  var genreSelectionModel: SingleSelectionModel[String] = _

  /** Adds a change listener to the selection model of the ChoiceBox, and contains
    * code that executes when the selection in the ChoiceBox changes.
    */
  def addListenerToGenreSelectionModel() {
    this.genreSelectionModel.selectedIndex.onChange({
      selectedDBs.value = this.genreSelectionModel.selectedIndex() match {
        case 0 => 80
        case 1 => 100
        case 2 => 150
        case 3 => 140
        case 4 => 120
        case 5 => 130
      }
    })
  }
}