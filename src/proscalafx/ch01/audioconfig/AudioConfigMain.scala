package proscalafx.ch01.audioconfig

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.VPos
import scalafx.scene.Scene
import scalafx.scene.control.{CheckBox, ChoiceBox, Slider}
import scalafx.scene.paint.{Color, LinearGradient, Stop}
import scalafx.scene.shape.{Line, Rectangle}
import scalafx.scene.text.{Font, FontWeight, Text}

object AudioConfigMain extends JFXApp {

  val acModel = new AudioConfigModel()

  val genreChoiceBox = new ChoiceBox[String] {
    layoutX = 204
    layoutY = 154
    prefWidth = 93
    items = acModel.genres
  }

  stage = new PrimaryStage {
    title = "Audio Configuration"
    scene = new Scene {
      content = List(
        new Rectangle {
          width = 320
          height = 45
          fill = new LinearGradient(
            endX = 0.0,
            endY = 1.0,
            stops = List(
              Stop(0, Color.web("0xAEBBCC")),
              Stop(1, Color.web("0x6D84A3"))
            )
          )
        },
        new Text {
          layoutX = 65
          layoutY = 12
          textOrigin = VPos.Top
          fill = Color.White
          text = "Audio Configuration"
          font = Font.font("SansSerif", FontWeight.Bold, 20)
        },
        new Rectangle {
          x = 0
          y = 43
          width = 320
          height = 300
          fill = Color.rgb(199, 206, 213)
        },
        new Rectangle {
          x = 9
          y = 54
          width = 300
          height = 130
          arcWidth = 20
          arcHeight = 20
          fill = Color.White
          stroke = Color.color(0.66, 0.67, 0.69)
        },
        new Text {
          layoutX = 18
          layoutY = 69
          textOrigin = VPos.Top
          fill = Color.web("#131021")
          font = Font.font("SansSerif", FontWeight.Bold, 18)
          text <== acModel.selectedDBs.asString + " dB"
        },
        new Slider {
          layoutX = 135
          layoutY = 69
          prefWidth = 162
          min = acModel.minDecibels
          max = acModel.maxDecibels
          value <==> acModel.selectedDBs
          disable <== acModel.muting
        },
        new Line {
          startX = 9
          startY = 97
          endX = 309
          endY = 97
          stroke = Color.color(0.66, 0.67, 0.69)
        },
        new Text {
          layoutX = 18
          layoutY = 113
          textOrigin = VPos.Top
          fill = Color.web("#131021")
          text = "Muting"
          font = Font.font("SanSerif", FontWeight.Bold, 18)
        },
        new CheckBox {
          layoutX = 280
          layoutY = 113
          selected <==> acModel.muting
        },
        new Line {
          startX = 9
          startY = 141
          endX = 309
          endY = 141
          stroke = Color.color(0.66, 0.67, 0.69)
        },
        new Text {
          layoutX = 18
          layoutY = 154
          textOrigin = VPos.Top
          fill = Color.web("#131021")
          text = "Genre"
          font = Font.font("SanSerif", FontWeight.Bold, 18)
        },
        genreChoiceBox)
    }
  }

  acModel.genreSelectionModel = genreChoiceBox.selectionModel()
  acModel.addListenerToGenreSelectionModel()
  acModel.genreSelectionModel.selectFirst()
}