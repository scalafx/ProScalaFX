
package proscalafx.ch08.AudioPlayer4

import javafx.scene.{layout => jfxsl, paint => jfxsp}

import com.sun.{javafx => csjfx}

import scalafx.Includes._
import scalafx.geometry.Pos
import scalafx.scene.paint.{Color, Stop}
import scalafx.scene.shape.Rectangle

/** Custom component for displaying a spectrum bar.
  *
  * @author Jarek Sacha
  */
class SpectrumBar(maxValue: Int, barCount: Int) extends jfxsl.VBox {
  private final val SPACING = 1.0
  private final val ASPECT_RATIO = 3.0
  private final val MIN_BAR_HEIGHT = 3.0
  private var lastWidth = 0.0
  private var lastHeight = 0.0
  getStyleClass.add("spectrumBar")

  setSpacing(SPACING)
  setAlignment(Pos.BottomCenter)

  val stops: Array[jfxsp.Stop] = Array(
    Stop(0.3, Color.Red),
    Stop(0.7, Color.Yellow),
    Stop(0.9, Color.web("56F32B"))
  )
  for (i <- 0 until barCount) {
    val c = (i.toDouble / barCount.toDouble * 255.0).toInt
    val r = new Rectangle {
      visible = false
      fill = csjfx.Utils.ladder(Color.rgb(c, c, c), stops)
      arcWidth = 2
      arcHeight = 2
    }

    getChildren.add(r)
  }


  protected override def computeMinHeight(width: Double) = computeHeight(MIN_BAR_HEIGHT)

  protected override def computeMinWidth(height: Double) = computeWidthForHeight(MIN_BAR_HEIGHT)

  protected override def computePrefHeight(width: Double) = computeHeight(5)

  protected override def computePrefWidth(height: Double) = computeWidthForHeight(5)

  def setValue(value: Double) {
    val barsLit = math.min(barCount, math.round(value / maxValue * barCount).toInt)
    val childList = getChildren
    for (i <- 0 until childList.size) {
      childList.get(i).setVisible(i > barCount - barsLit)
    }
  }

  protected override def layoutChildren() {
    if (lastWidth != getWidth || lastHeight != getHeight) {
      val spacing = SPACING * (barCount - 1)
      val barHeight = (getHeight - getVerticalPadding - spacing) / barCount
      val barWidth = math.min(barHeight * ASPECT_RATIO, getWidth - getHorizontalPadding)

      for (node <- getChildren) {
        val r = node.asInstanceOf[javafx.scene.shape.Rectangle]
        r.width = barWidth
        r.height = barHeight
      }

      lastWidth = getWidth
      lastHeight = getHeight
    }

    super.layoutChildren()
  }

  private def computeWidthForHeight(barHeight: Double) = barHeight * ASPECT_RATIO + getHorizontalPadding

  private def computeHeight(barHeight: Double): Double = {
    val vPadding = getVerticalPadding
    val barHeights = barHeight * barCount
    val spacing = SPACING * (barCount - 1)
    barHeights + spacing + vPadding
  }

  private def getVerticalPadding: Double = {
    val padding = getPadding
    padding.top + padding.bottom
  }

  private def getHorizontalPadding: Double = {
    val padding = getPadding
    padding.left + padding.right
  }
}
