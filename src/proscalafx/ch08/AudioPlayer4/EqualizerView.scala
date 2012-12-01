package proscalafx.ch08.AudioPlayer4

import javafx.scene.{layout => jfxsl}
import javafx.{geometry => jfxg}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{Label, Slider, Button}
import scalafx.scene.layout.{RowConstraints, GridPane}
import scalafx.scene.media.{EqualizerBand, MediaPlayer}

/**
 * @author Jarek Sacha 
 */
class EqualizerView(songModel: SongModel) extends AbstractView[GridPane](songModel) {
  private final val START_FREQ: Double = 250.0
  private final val BAND_COUNT: Int = 7
  private var spectrumBars: Array[SpectrumBar] = null
  private var spectrumListener: SpectrumListener = null
  private val backButton = new Button {
    text = "Back"
    id = "backButton"
    prefWidth = 50
    prefHeight = 32
  }

  songModel.mediaPlayer.onChange {
    (_, oldValue, _) =>
      if (oldValue != null) {
        oldValue.setAudioSpectrumListener(null)
        clearGridPane()
      }

      createEQInterface()
  }

  createEQInterface()

  viewNode.scene.onChange {
    (_, _, newValue) =>
      val mp = songModel.mediaPlayer()
      mp.audioSpectrumListener = if (newValue != null) spectrumListener else null
  }

  override def onNextPageAction(nextHandler: (ActionEvent => Unit)) {
    backButton.onAction = nextHandler
  }

  protected def initView(): GridPane = {
    val middle = new RowConstraints()
    val outside = new RowConstraints() {vgrow = jfxsl.Priority.ALWAYS}
    new GridPane {
      padding = Insets(10)
      hgap = 20
      rowConstraints +=(outside, middle, outside)
    }
  }

  private def createEQInterface() {
    val gridPane = viewNode
    val mediaPlayer = songModel.mediaPlayer()
    createEQBands(gridPane, mediaPlayer)
    createSpectrumBars(gridPane)
    spectrumListener = new SpectrumListener(START_FREQ, mediaPlayer, spectrumBars)
    GridPane.setValignment(backButton, jfxg.VPos.BOTTOM)
    jfxsl.GridPane.setHalignment(backButton, jfxg.HPos.CENTER)
    GridPane.setMargin(backButton, Insets(20, 0, 0, 0))
    gridPane.add(backButton, 0, 3)
  }

  private def createEQBands(gp: GridPane, mp: MediaPlayer) {
    val bands = mp.getAudioEqualizer.getBands
    bands.clear()
    val min = EqualizerBand.MIN_GAIN
    val max = EqualizerBand.MAX_GAIN
    val mid = (max - min) / 2
    var freq = START_FREQ

    for (j <- 0 until BAND_COUNT) {
      val theta = j.toDouble / (BAND_COUNT - 1).toDouble * (2 * math.Pi)
      val scale = 0.4 * (1 + math.cos(theta))
      val gain = min + mid + (mid * scale)
      bands.add(new EqualizerBand(freq, freq / 2, gain))
      freq *= 2
    }

    for (i <- 0 until bands.size) {
      val band = bands.get(i)
      val slider = createEQSlider(band, min, max)
      val label = new Label {
        text = formatFrequency(band.getCenterFrequency)
        styleClass +=("mediaText", "eqLabel")
      }
      jfxsl.GridPane.setHalignment(label, jfxg.HPos.CENTER)
      jfxsl.GridPane.setHalignment(slider, jfxg.HPos.CENTER)
      GridPane.setHgrow(slider, jfxsl.Priority.ALWAYS)
      gp.add(label, i, 1)
      gp.add(slider, i, 2)
    }
  }

  private def createEQSlider(eb: EqualizerBand, minValue: Double, maxValue: Double) = new Slider {
    min = minValue
    max = maxValue
    value = eb.gain()
    styleClass += "eqSlider"
    orientation = jfxg.Orientation.VERTICAL
    value <==> eb.gain
    prefWidth = 44
  }


  private def createSpectrumBars(gridPane: GridPane) {
    spectrumBars = new Array[SpectrumBar](BAND_COUNT)
    for (i <- 0 until spectrumBars.length) {
      spectrumBars(i) = new SpectrumBar(100, 20)
      spectrumBars(i).setMaxWidth(44)
      jfxsl.GridPane.setHalignment(spectrumBars(i), jfxg.HPos.CENTER)
      gridPane.add(spectrumBars(i), i, 0)
    }
  }

  private def formatFrequency(centerFrequency: Double): String =
    if (centerFrequency < 1000) {
      "%.0f Hz".format(centerFrequency)
    } else {
      "%.1f kHz".format(centerFrequency / 1000)
    }

  private def clearGridPane() {
    viewNode.content.foreach(GridPane.clearConstraints(_))
    viewNode.content.clear()
  }
}
