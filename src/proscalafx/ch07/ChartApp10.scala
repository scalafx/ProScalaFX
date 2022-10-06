package proscalafx.ch07

import javafx.scene.chart as jfxsc
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.*
import scalafx.scene.layout.StackPane
import scalafx.util.StringConverter

/**
 * We use here the version of the example that is described in the book, it scales axis so bubbles appear as spheres
 * rather than ellipses (in the downloadable Java code).
 *
 * @author Jarek Sacha
 */
object ChartApp10 extends JFXApp3 {

  private val xStep = 10
  private val xMin  = 2010 * xStep
  private val xMax  = 2016 * xStep

  override def start(): Unit = {

    val xAxis = new NumberAxis {
      autoRanging = false
      lowerBound = xMin - xStep
      upperBound = xMax + xStep
      tickUnit = xStep
      // The xAxis ranges from 20110 till 20210, but of course we want to show the years at the axis. This can
      // be achieved by calling
      tickLabelFormatter = new StringConverter[Number] {
        // Here we do not need to convert from string.
        def fromString(string: String): Number = throw new UnsupportedOperationException("Not implemented.")

        def toString(t: Number): String = (t.intValue() / xStep).toString
      }
    }
    val yAxis       = new NumberAxis()
    val bubbleChart = BubbleChart(xAxis, yAxis)
    bubbleChart.title = "Speculations"
    bubbleChart.data = createChartData()

    stage = new PrimaryStage {
      title = "BubbleChart example"
      scene = new Scene(400, 250) {
        root = new StackPane {
          children = bubbleChart
        }
      }
    }
  }

  // NOTE: explicit type signature using Number instead Int and Double
  // NOTE: use of jfxsc.XYChart.Series as type for ObservableBuffer, this the same as
  // signature for scalafx.scene.chart.XYChart.data used above.
  private def createChartData(): ObservableBuffer[jfxsc.XYChart.Series[Number, Number]] = {
    var javaValue = 17.56
    var cValue    = 17.06
    var cppValue  = 8.25
    val scale     = 10
    val answer    = new ObservableBuffer[jfxsc.XYChart.Series[Number, Number]]()
    val java = new XYChart.Series[Number, Number] {
      name = "Java"
    }
    val c = new XYChart.Series[Number, Number] {
      name = "C"
    }
    val cpp = new XYChart.Series[Number, Number] {
      name = "C++"
    }
    for (i <- xMin to xMax by xStep) {
      java.data() += XYChart.Data[Number, Number](i, javaValue)
      javaValue += +scale * math.random() - scale / 2

      c.data() += XYChart.Data[Number, Number](i, cValue)
      cValue += scale * math.random() - scale / 2

      cpp.data() += XYChart.Data[Number, Number](i, cppValue)
      cppValue += scale * math.random() - scale / 2
    }
    answer.addAll(java, c, cpp)
    answer
  }
}
