package proscalafx.ch08.VideoPlayer4

import scalafx.Includes._
import scalafx.scene.media.MediaView


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
