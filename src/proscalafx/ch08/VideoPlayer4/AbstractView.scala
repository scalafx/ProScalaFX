package proscalafx.ch08.VideoPlayer4

import scalafx.event.ActionEvent
import scalafx.scene.Node


/**
 * @author Jarek Sacha 
 */
abstract class AbstractView[T <: Node](protected val mediaModel: MediaModel) {

  private val _viewNode: T = initView()

  def viewNode: T = _viewNode

  def onNextPageAction(nextHandler: (ActionEvent => Unit)) {}

  protected def initView(): T
}
