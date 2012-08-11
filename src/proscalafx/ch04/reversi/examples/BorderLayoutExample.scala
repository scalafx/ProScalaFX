package proscalafx.ch04.reversi.examples

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import proscalafx.ch04.reversi.model.BLACK
import proscalafx.ch04.reversi.model.Owner
import proscalafx.ch04.reversi.model.WHITE
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.effect.DropShadow
import scalafx.scene.effect.InnerShadow
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.FlowPane
import scalafx.scene.layout.Region
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.TilePane
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse
import scalafx.scene.text.Font
import scalafx.scene.text.Text
import scalafx.scene.Node
import scalafx.scene.Scene
import scalafx.stage.Stage
import proscalafx.ch04.reversi.model.ReversiModel
import javafx.beans.{ binding => jfxbb }
import scalafx.beans.binding.NumberExpression

object BorderLayoutExample extends JFXApp {
  val borderPane = new BorderPane {
    top = createTitle
    center = createBackground
    bottom = createScoreBoxes
  }
  println("Center - SFX: " + borderPane.center.get)
  val myscene = new Scene(600, 400) {
    root = borderPane
  }
  println("Root   - SFX: " + myscene.getRoot)

  stage = new Stage {
    //    width = 600
    //    height = 400
    scene = myscene
  }

  private def createTitle = new TilePane {
    snapToPixel = false
    content = List(
      new StackPane {
        style = "-fx-background-color: black"
        content = new Text("ScalaFX") {
          font = Font.font(null, FontWeight.BOLD, 18)
          fill = Color.WHITE
          alignment = Pos.CENTER_RIGHT
        }
      },
      new Text("Reversi") {
        font = Font.font(null, FontWeight.BOLD, 18)
        alignment = Pos.CENTER_LEFT
      })
    prefTileHeight = 40
    prefTileWidth <== parent.selectDouble("width") / 2
  }

  def createBackground = new Region {
    style = "-fx-background-color: radial-gradient(radius 100%, white, gray)"
  }

  def createScoreBoxes = new TilePane {
    snapToPixel = false
    prefColumns = 2
    content = List(createScore(BLACK), createScore(WHITE))
    prefTileWidth <== parent.selectDouble("width") / 2
  }

  private def createScore(owner: Owner): Node = {
    val innerShadow = new InnerShadow {
      color = Color.DODGERBLUE
      choke = 0.5
    }
    val background = new Region {
      style = "-fx-background-color: " + owner.opposite.colorStyle
//      effect <== when(ReversiModel.turn === owner) then innerShadow otherwise null
    }
    /*
- ambiguous reference to overloaded definition, both method otherwise in class ObjectConditionBuilder of type (otherwiseExpression: 
	 scalafx.scene.effect.InnerShadow)javafx.beans.binding.ObjectBinding[scalafx.scene.effect.InnerShadow] and method otherwise in class ObjectConditionBuilder of type 
	 (otherwiseExpression: javafx.beans.value.ObservableObjectValue[scalafx.scene.effect.InnerShadow])javafx.beans.binding.ObjectBinding[scalafx.scene.effect.InnerShadow] 
	 match argument types (Null)
	- overloaded method value <== with alternatives: (v: scalafx.beans.value.ObservableValue[_ <: javafx.scene.effect.Effect, _ <: javafx.scene.effect.Effect])Unit 
	 <and> (v: javafx.beans.value.ObservableValue[_ <: javafx.scene.effect.Effect])Unit cannot be applied to 
	 (javafx.beans.binding.ObjectBinding[scalafx.scene.effect.InnerShadow])
	 */
    //      background.effect <== when(ReversiModel.turn === owner) then innerShadow otherwise null.asInstanceOf[InnerShadow]

    val dropShadow = new DropShadow {
      color = Color.DODGERBLUE
      spread = 0.2
    }
    val piece = new Ellipse {
      effect = new DropShadow {
        color = Color.DODGERBLUE
        spread = 0.2
        effect = dropShadow
        //        effect <== when(ReversiModel.turn === owner) then dropShadow otherwise null.asInstanceOf[DropShadow]
      }
      radiusX = 32
      radiusY = 20
      fill = owner.color
    }
    val score = new Text {
      font = Font.font(null, FontWeight.BOLD, 100)
      fill = owner.color
      text <== ReversiModel.score(owner).asString
    }
    val remaining = new Text {
      font = Font.font(null, FontWeight.BOLD, 12)
      fill = owner.color
      text = " pieces remaing"
      //      text <== ReversiModel.turnsRemaining(owner).asString
    }
    //     remaining.textProperty().bind(model.getTurnsRemaining(owner).asString().concat(" turns remaining"));

    val stack = new StackPane {
      content = List(background,
        new FlowPane {
          hgap = 20
          vgap = 10
          alignment = Pos.CENTER
          content = List(score, new VBox {
            alignment = Pos.CENTER
            spacing = 10
            content = List(piece, remaining)
          })
        })
    }

    stack
  }

}