package proscalafx.ch08.AudioPlayer4

import scalafx.event.ActionEvent
import scalafx.scene.Node


/**
 * @author Jarek Sacha 
 */
abstract class AbstractView[T <: Node](protected val songMadel: SongModel) {

  private val _viewNode: T = initView()

  def viewNode: T = _viewNode

  def onNextPageAction(nextHandler: (ActionEvent => Unit)) {}

  protected def initView(): T
}
