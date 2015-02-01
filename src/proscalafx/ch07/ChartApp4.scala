package proscalafx.ch07

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}
import scalafx.scene.layout.StackPane


/**
 * @author Jarek Sacha
 */
object ChartApp4 extends JFXApp {

  val xAxis = new NumberAxis {
    autoRanging = false
    lowerBound = 2011
    upperBound = 2021
  }
  val yAxis = new NumberAxis()
  val scatterChart = ScatterChart[Number, Number](xAxis, yAxis, createChartData())

  stage = new PrimaryStage {
    title = "Chart App 4"
    scene = new Scene(400, 250) {
      root = new StackPane {
        children = scatterChart
      }
    }
  }


  private def createChartData() = {

    val years = 2011 to 2020
    // Generate trend by creating a cumulative sum of random values
    def generateTrend(startValue: Double) = years.map(_ => math.random - .5).scanLeft(startValue)(_ + _)

    val javaTrend = generateTrend(17.56)
    val cTrend = generateTrend(17.06)
    val cppTrend = generateTrend(8.25)

    // NOTE: explicit type signature using Number instead Int and Double
    // We are deliberately using here factory methods, instead of "new", to create instances of
    // javafx.scene.chart.* types.
    val javaData = years zip javaTrend map {case (y, d) => XYChart.Data[Number, Number](y, d)}
    val cData = years zip cTrend map {case (y, d) => XYChart.Data[Number, Number](y, d)}
    val cppData = years zip cppTrend map {case (y, d) => XYChart.Data[Number, Number](y, d)}

    ObservableBuffer(
      XYChart.Series[Number, Number](name = "Java", data = ObservableBuffer(javaData)),
      XYChart.Series[Number, Number](name = "C", data = ObservableBuffer(cData)),
      XYChart.Series[Number, Number](name = "C++", data = ObservableBuffer(cppData))
    )
  }
}
