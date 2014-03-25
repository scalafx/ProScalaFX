package proscalafx.ch08.AudioPlayer4

import javafx.scene.{image => jfxsi}
import scalafx.Includes._
import scalafx.beans.property.{ReadOnlyObjectWrapper, ObjectProperty, StringProperty}
import scalafx.collections.ObservableMap.Add
import scalafx.scene.image.Image
import scalafx.scene.media.{Media, MediaPlayer}

/**
 * @author Jarek Sacha 
 */
class SongModel {
  private val DefaultImageURL = classOf[SongModel].getResource("resources/defaultAlbum.png").toString
  private val DefaultImageCover = new Image(DefaultImageURL)
  val album = new StringProperty(this, "album")
  val artist = new StringProperty(this, "artist")
  val title = new StringProperty(this, "title")
  val year = new StringProperty(this, "year")
  // NOTE: use of `javafx.scene.image.Image` instead of `scalafx.scene.image.Image`, this is required for binding in
  // MetadataView to compile.
  val albumCover = new ObjectProperty[jfxsi.Image](this, "albumCover")
  private val _mediaPlayer = new ReadOnlyObjectWrapper[MediaPlayer](this, "mediaPlayer")

  resetProperties()


  def mediaPlayer = _mediaPlayer.readOnlyProperty

  def url: String = if (mediaPlayer() != null) mediaPlayer().media.source else null

  def url_=(url: String) {
    if (mediaPlayer() != null) mediaPlayer().stop()

    initializeMedia(url)
  }

  private def resetProperties() {
    artist() = ""
    album() = ""
    title() = ""
    year() = ""
    albumCover() = DefaultImageCover
  }


  private def initializeMedia(url: String) {
    resetProperties()

    try {
      val media = new Media(url) {
        metadata.onChange((_, change) => {
          change match {
            case Add(key, added) => handleMetadata(key, added)
            case _ =>
          }
        })
      }

      _mediaPlayer() = new MediaPlayer(media) {
        // Handle errors during playback
        onError = {
          val errorMessage = media.error().getMessage
          println("MediaPlayer Error: " + errorMessage)
        }
      }
    } catch {
      // Handle construction errors
      case re: RuntimeException => println("Caught Exception: " + re.getMessage)
    }
  }


  private def handleMetadata(key: String, value: AnyRef) {
    key match {
      case "album" => album() = value.toString
      case "artist" => artist() = value.toString
      case "title" => title() = value.toString
      case "year" => year() = value.toString
      case "image" => albumCover() = value.asInstanceOf[javafx.scene.image.Image]
      case _ => println("Unhandled metadata key: " + key + ", value: " + value)
    }
  }
}
