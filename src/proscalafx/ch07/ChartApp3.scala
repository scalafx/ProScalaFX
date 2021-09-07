package proscalafx.ch07

import javafx.scene.{chart => jfxsc}
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}
import scalafx.scene.layout.StackPane

/**
  * @author Jarek Sacha
  */
object ChartApp3 extends JFXApp3 {

  override def start(): Unit = {

    val xAxis = new NumberAxis()
    val yAxis = new NumberAxis()
    val scatterChart = ScatterChart(xAxis, yAxis)

    scatterChart.data = createChartData()

    stage = new PrimaryStage {
      title = "Chart App 3"
      scene = new Scene(400, 250) {
        root = new StackPane {
          children = scatterChart
        }
      }
    }
  }

  // NOTE: explicit type signature using Number instead Int and Double
  // NOTE: use of jfxsc.XYChart.Series as type for ObservableBuffer, this the same as
  // signature for scalafx.scene.chart.XYChart.data used above.
  private def createChartData(): ObservableBuffer[jfxsc.XYChart.Series[Number, Number]] = {
    var javaValue = 17.56
    var cValue = 17.06
    var cppValue = 8.25
    val answer = new ObservableBuffer[jfxsc.XYChart.Series[Number, Number]]()
    val java = new XYChart.Series[Number, Number] {
      name = "Java"
    }
    val c = new XYChart.Series[Number, Number] {
      name = "C"
    }
    val cpp = new XYChart.Series[Number, Number] {
      name = "C++"
    }
    for (i <- 2011 to 2020) {
      java.data() += XYChart.Data[Number, Number](i, javaValue)
      javaValue += math.random() - .5

      c.data() += XYChart.Data[Number, Number](i, cValue)
      cValue += math.random() - .5

      cpp.data() += XYChart.Data[Number, Number](i, cppValue)
      cppValue += math.random() - .5
    }
    answer.addAll(java, c, cpp)
    answer
  }
}
