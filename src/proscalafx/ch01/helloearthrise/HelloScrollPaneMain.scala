package proscalafx.ch01.helloearthrise

import javafx.geometry.VPos
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import scalafx.animation.Interpolator
import scalafx.animation.Timeline
import scalafx.animation.TranslateTransition
import scalafx.application.JFXApp
import scalafx.scene.Group
import scalafx.scene.Scene
import scalafx.scene.control.ScrollPane
import scalafx.scene.image.Image
import scalafx.scene.image.Image.sfxImage2jfx
import scalafx.scene.image.ImageView
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.stage.Stage
import scalafx.util.Duration
import scalafx.util.UtilIncludes.jfxDuration2sfx

object HelloScrollPaneMain extends JFXApp {

  val message = """Earthrise at Christmas: 
[Forty] years ago this Christmas, a turbulent world 
looked to the heavens for a unique view of our home 
planet. This photo of Earthrise over the lunar horizon 
was taken by the Apollo 8 crew in December 1968, showing 
Earth for the first time as it appears from deep space. 
Astronauts Frank Borman, Jim Lovell and William Anders 
had become the first humans to leave Earth orbit, 
entering lunar orbit on Christmas Eve. In a historic live 
broadcast that night, the crew took turns reading from 
the Book of Genesis, closing with a holiday wish from 
Commander Borman: "We close with good night, good luck, 
a Merry Christmas, and God bless all of you -- all of 
you on the good Earth." """.replace("\n", "")

  val textRef = new Text {
    layoutY = 100
    textOrigin = VPos.TOP
    textAlignment = TextAlignment.JUSTIFY
    wrappingWidth = 400
    text = message
    fill = Color.rgb(187, 195, 107)
    font = Font.font("SansSerif", FontWeight.BOLD, 24.0)
  }

  stage = new Stage {
    title = "Hello Earthrise"
    scene = new Scene(516, 387) {
      content = new Group {
        children = List(
          new ImageView {
            image = new Image("http://projavafx.com/images/earthrise.jpg")
          },
          new ScrollPane {
            layoutX = 50
            layoutY = 180
            prefWidth = 440
            prefHeight = 85
            hbarPolicy = ScrollBarPolicy.NEVER
            vbarPolicy = ScrollBarPolicy.NEVER
            pannable = true
            content = textRef
            style = "-fx-background-color: transparent;"
          })
      }
    }
  }

  new TranslateTransition {
    cycleCount = Timeline.INDEFINITE
    duration = Duration(75000)
    node = textRef
    toY = -820
    interpolator = Interpolator.LINEAR
  }.play

}