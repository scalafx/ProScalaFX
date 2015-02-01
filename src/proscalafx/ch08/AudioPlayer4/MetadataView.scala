package proscalafx.ch08.AudioPlayer4

import scalafx.Includes._
import scalafx.geometry.{Insets, VPos}
import scalafx.scene.control.Label
import scalafx.scene.effect.Reflection
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{ColumnConstraints, GridPane, Priority, RowConstraints}


/**
 * @author Jarek Sacha
 */
class MetadataView(songModel: SongModel) extends AbstractView[GridPane](songModel) {


  def initView(): GridPane = {
    val title = new Label {
      text <== songModel.title
      id = "title"
    }
    val artist = new Label {
      text <== songModel.artist
      id = "artist"
    }
    val album = new Label {
      text <== songModel.album
      id = "album"
    }
    val year = new Label {
      text <== songModel.year
      id = "year"
    }
    val albumCover = new ImageView {
      image <== songModel.albumCover
      fitWidth = 240
      preserveRatio = true
      smooth = true
      effect = new Reflection {
        fraction = 0.2
      }
    }

    new GridPane {
      padding = Insets(10)
      hgap = 20
      add(albumCover, 0, 0, 1, GridPane.REMAINING)
      add(title, 1, 0)
      add(artist, 1, 1)
      add(album, 1, 2)
      add(year, 1, 3)
      columnConstraints +=(
        new ColumnConstraints(),
        // NOTE: the call to delegate to avoid compilation error.
        // Should `scalafx.scene.layout.GridPane.columnConstraints_=()` be fixed to work without call to delegate?
        new ColumnConstraints {
          hgrow = Priority.Always
        }.delegate
        )
      val r0 = new RowConstraints {
        valignment = VPos.Top
      }
      rowConstraints +=(r0, r0, r0, r0)
    }
  }
}
