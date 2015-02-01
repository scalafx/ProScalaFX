package proscalafx.ch07

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.layout.StackPane


/**
 * @author Jarek Sacha
 */
object ChartApp2 extends JFXApp {

  stage = new PrimaryStage {
    title = "Chart App 2"
    scene = new Scene(400, 350) {
      root = new StackPane {
        children = new PieChart() {
          data = chartData()
          title = "Tiobe index"
          legendSide = Side.LEFT
          clockwise = false
          // Setting `labelsVisible` property to `false` is causing NullPointerException.
          // This is JavaFX 2.2 bug apparently fixed in v.2.2.4
          // See [[http://javafx-jira.kenai.com/browse/RT-24106]]
          // If you get NPE, comment line below
          labelsVisible = false
        }
      }
    }
  }


  private def chartData() = ObservableBuffer(
    PieChart.Data("java", 17.56),
    PieChart.Data("C", 17.06),
    PieChart.Data("C++", 8.25),
    PieChart.Data("C#", 8.20),
    PieChart.Data("ObjectiveC", 6.8),
    PieChart.Data("PHP", 6.0),
    PieChart.Data("(Visual)Basic", 4.76),
    PieChart.Data("Other", 31.37)
  )
}
