package proscalafx.ch08.VideoPlayer4

import scalafx.Includes._
import scalafx.beans.property.ReadOnlyObjectProperty
import scalafx.scene.media.MediaView


object VideoView {
  implicit def sfxReadOnlyObjectProperty2jfx[T <: Object](roop: ReadOnlyObjectProperty[T]) = roop.delegate
}

/**
 * @author Jarek Sacha 
 */
class VideoView(mediaModel: MediaModel) extends AbstractView[MediaView](mediaModel) {

  protected def initView(): MediaView = {
    val mediaView = new MediaView
    mediaView.mediaPlayer <== mediaModel.mediaPlayer
    mediaView
  }
}
