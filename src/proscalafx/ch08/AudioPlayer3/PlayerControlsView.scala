package proscalafx.ch08.AudioPlayer3

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafx.event.subscriptions.Subscription
import scalafx.geometry.{HPos, Insets, Pos, VPos}
import scalafx.scene.Node
import scalafx.scene.control.{Button, Label, Slider}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{ColumnConstraints, GridPane, HBox, Priority}
import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.MediaPlayer.Status
import scalafx.stage.FileChooser
import scalafx.util.Duration


/**
 * @author Jarek Sacha 
 */
class PlayerControlsView(songModel: SongModel) extends AbstractView(songModel) {
  private var pauseImg: Image = _
  private var playImg: Image = _
  private var playPauseIcon: ImageView = _
  // ScalaFX uses `subscription` to keep track of assigned listeners
  private var statusInvalidationSubscription: Subscription = _
  private var currentTimeSubscription: Subscription = _
  private var controlPanel: Node = _
  private var statusLabel: Label = _
  private var currentTimeLabel: Label = _
  private var totalDurationLabel: Label = _
  private var volumeSlider: Slider = _
  private var positionSlider: Slider = _


  songModel.mediaPlayer.onChange(
    (_, oldValue, newValue) => {
      if (oldValue != null) removeListenersAndBinding(oldValue)
      addListenersAndBindings(newValue)
    }
  )

  addListenersAndBindings(songModel.mediaPlayer())

  protected def initView(): Node = {
    val openButton = createOpenButton()

    controlPanel = createControlPanel()
    volumeSlider = createSlider("volumeSlider")
    statusLabel = createLabel("Buffering", "statusDisplay")
    positionSlider = createSlider("positionSlider")
    totalDurationLabel = createLabel("00:00", "mediaText")
    currentTimeLabel = createLabel("00:00", "mediaText")

    positionSlider.valueChanging.onChange(
      (_, oldValue, newValue) =>
        if (oldValue && !newValue) {
          val pos = positionSlider.value()
          val mediaPlayer = songModel.mediaPlayer()
          val seekTo = mediaPlayer.totalDuration() * pos
          seekAndUpdatePosition(seekTo)
        }
    )

    val volLow = new ImageView {
      id = "volumeLow"
    }

    val volHigh = new ImageView {
      id = "volumeHigh"
    }

    val buttonCol = new ColumnConstraints(100)
    val spacerCol = new ColumnConstraints(40, 80, 80)
    val middleCol = new ColumnConstraints {
      hgrow = Priority.Always
    }

    val gp = new GridPane {
      hgap = 1
      vgap = 1
      padding = Insets(10)
      columnConstraints = List(buttonCol, spacerCol, middleCol, spacerCol, buttonCol)
    }

    GridPane.setValignment(openButton, VPos.Bottom)
    GridPane.setHalignment(volHigh, HPos.Right)
    GridPane.setValignment(volumeSlider, VPos.Top)
    statusLabel.alignmentInParent = Pos.TopRight
    GridPane.setHalignment(currentTimeLabel, HPos.Right)

    gp.add(openButton, 0, 0, 1, 3)
    gp.add(volLow, 1, 0)
    gp.add(volHigh, 1, 0)
    gp.add(volumeSlider, 1, 1)
    gp.add(controlPanel, 2, 0, 1, 2)
    gp.add(statusLabel, 3, 1)
    gp.add(currentTimeLabel, 1, 2)
    gp.add(positionSlider, 2, 2)
    gp.add(totalDurationLabel, 3, 2)

    gp
  }


  private def createOpenButton() = new Button {
    id = "openButton"
    onAction = (ae: ActionEvent) => {
      val fc = new FileChooser() {
        title = "Pick a Sound File"
      }

      val song = fc.showOpenDialog(viewNode.scene().window())
      if (song != null) {
        songModel.url = song.toURI.toString
        songModel.mediaPlayer().play()
      }
    }
    prefWidth = 32
    prefHeight = 32
  }


  private def createControlPanel(): Node = {
    val playPauseButton = createPlayPauseButton()
    val seekStartButton = new Button {
      id = "seekStartButton"
      onAction = (ae: ActionEvent) => seekAndUpdatePosition(Duration.ZERO)
    }
    val seekEndButton = new Button {
      id = "seekEndButton"
      onAction = (ae: ActionEvent) => {
        val mediaPlayer = songModel.mediaPlayer()
        val totalDuration = mediaPlayer.totalDuration()
        // Duration of 1 second is constructed using suffix `s`
        seekAndUpdatePosition(totalDuration - (1 s))
      }
    }

    new HBox {
      alignment = Pos.Center
      fillHeight = false
      children = List(seekStartButton, playPauseButton, seekEndButton)
    }
  }


  private def createPlayPauseButton(): Button = {
    val pauseUrl = getClass.getResource("resources/pause.png")
    pauseImg = new Image(pauseUrl.toString)

    val playUrl = getClass.getResource("resources/play.png")
    playImg = new Image(playUrl.toString)

    playPauseIcon = new ImageView {
      image = playImg
    }

    new Button {
      graphic = playPauseIcon
      id = "playPauseButton"
      onAction = (ae: ActionEvent) => {
        val mediaPlayer = songModel.mediaPlayer()
        mediaPlayer.status() match {
          case Status.PLAYING.delegate => mediaPlayer.pause()
          case _ => mediaPlayer.play()
        }
      }
    }
  }


  private def createSlider(sliderId: String): Slider = new Slider {
    min = 0
    max = 1
    value = 0
    id = sliderId
  }

  private def createLabel(labelText: String, labelStyleClass: String): Label = new Label {
    text = labelText
    styleClass += labelStyleClass
  }


  private def updatePositionSlider(currentTime: Duration) {
    if (positionSlider.isValueChanging) return

    val mediaPlayer = songModel.mediaPlayer()
    val total = mediaPlayer.totalDuration()

    positionSlider.value = if (total == null || currentTime == null) {
      0d
    } else {
      currentTime.toMillis / total.toMillis
    }
  }


  private def seekAndUpdatePosition(duration: Duration) {
    val mediaPlayer = songModel.mediaPlayer()
    if (Status.STOPPED == mediaPlayer.status) mediaPlayer.pause()

    mediaPlayer.seek(duration)

    if (Status.PLAYING != mediaPlayer.status) updatePositionSlider(duration)
  }

  private def formatDuration(duration: Duration): String = {
    val millis = duration.toMillis
    val seconds = (millis / 1000).toInt % 60
    val minutes = (millis / (1000 * 60)).toInt
    "%02d:%02d".format(minutes, seconds)
  }

  private def updateStatus(newStatus: Status) {
    if (Status.UNKNOWN == newStatus || newStatus == null) {
      controlPanel.disable = true
      positionSlider.disable = true
      statusLabel.text = "Buffering"
    } else {
      controlPanel.disable = false
      positionSlider.disable = false
      statusLabel.text = newStatus.toString
      playPauseIcon.image = if (Status.PLAYING == newStatus) pauseImg else playImg
    }
  }


  private def addListenersAndBindings(mp: MediaPlayer) {
    statusInvalidationSubscription = mp.status.onInvalidate {
      Platform.runLater {
        updateStatus(songModel.mediaPlayer().status())
      }
    }

    currentTimeSubscription = mp.currentTime.onInvalidate {
      Platform.runLater {
        val mediaPlayer = songModel.mediaPlayer()
        val currentTime = mediaPlayer.currentTime()
        currentTimeLabel.text = formatDuration(currentTime)
        updatePositionSlider(currentTime)
      }
    }

    mp.totalDuration.onInvalidate {
      val mediaPlayer = songModel.mediaPlayer()
      val totalDuration = mediaPlayer.totalDuration()
      totalDurationLabel.text = formatDuration(totalDuration)
    }

    mp.onEndOfMedia = songModel.mediaPlayer().stop()

    volumeSlider.value <==> mp.volume
  }

  private def removeListenersAndBinding(mp: MediaPlayer) {
    volumeSlider.value.unbind()
    statusInvalidationSubscription.cancel()
    currentTimeSubscription.cancel()
  }
}
