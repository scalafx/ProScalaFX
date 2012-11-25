package proscalafx.ch08.AudioPlayer2

import javafx.scene.{layout => jfxsl}
import javafx.{collections => jfxc}
import javafx.{geometry => jfxg}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.effect.Reflection
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{RowConstraints, ColumnConstraints, GridPane}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.stage.Stage


/**
 * Displaying the metadata information in the scene graph.
 *
 * @author Jarek Sacha
 */
object AudioPlayer2 extends JFXApp {
  private var media: Media = _
  private var mediaPlayer: MediaPlayer = _
  private var artist: Label = _
  private var album: Label = _
  private var title: Label = _
  private var year: Label = _
  private var albumCover: ImageView = _

  createControls()
  createMedia()

  stage = new Stage {
    title = "Audio Player 2"
    scene = new Scene(createGridPane(), 800, 400) {
      val stylesheet = getClass.getResource("media.css")
      stylesheets += stylesheet.toString
    }
  }


  private def createGridPane(): GridPane = new GridPane {
    padding = Insets(10)
    hgap = 20
    add(albumCover, 0, 0, 1, jfxsl.GridPane.REMAINING)
    add(title, 1, 0)
    add(artist, 1, 1)
    add(album, 1, 2)
    add(year, 1, 3)
    columnConstraints +=(
      new ColumnConstraints(),
      // NOTE: the call to delegate to avoid compilation error.
      // Should `scalafx.scene.layout.GridPane.columnConstraints_=()` be fixed to work without call to delegate?
      new ColumnConstraints {
        hgrow = jfxsl.Priority.ALWAYS
      }.delegate
      )
    val r0 = new RowConstraints {
      valignment = jfxg.VPos.TOP
    }
    rowConstraints +=(r0, r0, r0, r0)
  }


  private def createControls() {
    artist = new Label {
      id = "artist"
    }
    album = new Label {
      id = "album"
    }
    title = new Label {
      id = "title"
    }
    year = new Label {
      id = "year"
    }
    val url = getClass.getResource("resources/defaultAlbum.png")
    albumCover = new ImageView {
      image = new Image(url.toString)
      fitWidth = 240
      preserveRatio = true
      smooth = true
      effect = new Reflection {
        fraction = 0.2
      }
    }
  }


  private def createMedia() {
    try {
      media = new Media("http://traffic.libsyn.com/dickwall/JavaPosse373.mp3")
      // NOTE: Adding ScalaFX like listener will not work, using JavaFX style listener
      //      media.getMetadata.onChange((_, change) => {
      //        change match {
      //          case Add(key, added) => handleMetadata(key, added)
      //          case _               => {}
      //        }
      //      })
      media.getMetadata.addListener(new jfxc.MapChangeListener[String, AnyRef] {
        def onChanged(ch: jfxc.MapChangeListener.Change[_ <: String, _ <: AnyRef]) {
          if (ch.wasAdded) handleMetadata(ch.getKey, ch.getValueAdded)
        }
      })

      // NOTE: Since ScalaFX MediaPlayer is declared `final` cannot use 'hierarchical'/`anonymous class` pattern
      //      mediaPlayer = new MediaPlayer(media) {
      //        onError = new Runnable {
      //          def run() {
      //            val errorMessage: String = media.getError.getMessage
      //            System.out.println("MediaPlayer Error: " + errorMessage)
      //          }
      //        }
      //      }
      mediaPlayer = new MediaPlayer(media)
      mediaPlayer.onError = new Runnable {
        def run() {
          val errorMessage = media.error().getMessage
          println("MediaPlayer Error: " + errorMessage)
        }
      }
      mediaPlayer.play()
    } catch {
      case re: RuntimeException => println("Caught Exception: " + re.getMessage)
    }
  }


  private def handleMetadata(key: String, value: AnyRef) {
    key match {
      case "album"  => album.text = value.toString
      case "artist" => artist.text = value.toString
      case "title"  => title.text = value.toString
      case "year"   => year.text = value.toString
      case "image"  => albumCover.setImage(value.asInstanceOf[javafx.scene.image.Image])
      case _        => println("metadata key: " + key + ", value: " + value)
    }
  }
}
