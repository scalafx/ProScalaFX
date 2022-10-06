package proscalafx.ch07

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.layout.StackPane

/**
 * @author Jarek Sacha
 */
object ChartApp2 extends JFXApp3 {

  override def start(): Unit = {

    stage = new PrimaryStage {
      title = "Chart App 2"
      scene = new Scene(400, 350) {
        root = new StackPane {
          children = new PieChart() {
            data = chartData()
            title = "Tiobe index"
            legendSide = Side.Left
            clockwise = false
            labelsVisible = false
          }
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
