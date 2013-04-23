package proscalafx.ch08.AudioPlayer3

import scalafx.scene.Node


/**
 * @author Jarek Sacha 
 */
abstract class AbstractView(protected val songMadel: SongModel) {
  private val _viewNode: Node = initView()

  def viewNode: Node = _viewNode

  protected def initView(): Node
}
