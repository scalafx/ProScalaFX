package proscalafx.ch05.model

import scalafx.beans.property.DoubleProperty
import scalafx.collections.ObservableBuffer

import scala.util.Random

/**
 * @author Jarek Sacha
 */
class StarterAppModel {

  val listViewItems  = new ObservableBuffer[String]()
  val choiceBoxItems = ObservableBuffer("Choice A", "Choice B", "Choice C", "Choice D")
  val maxRpm: Double = 8000d
  val rpm            = new DoubleProperty(this, "rpm", 0)
  val maxKph: Double = 300.0
  val kph            = new DoubleProperty(this, "kph", 0)

  def getTeamMembers: ObservableBuffer[Person] = {

    val teamMembers = new ObservableBuffer[Person]()

    for (i <- 1 to 1000) {
      teamMembers += new Person("FirstName" + i, "LastName" + i, "Phone" + i)
    }

    teamMembers
  }

  def randomWebSite(): String = {

    val webSites: Array[String] = Array(
      "https://openjfx.io/",
      "https://github.com/mhrimaz/AwesomeJavaFX",
      "http://fxexperience.com",
//      "http://steveonjava.com",
      "https://javafxpert.com",
      "https://pleasingsoftware.blogspot.com",
      "https://www.weiqigao.com/blog",
      "https://edencoding.com/category/javafx/",
      "https://codingonthestaircase.wordpress.com/",
      "http://google.com"
    )

    val randomIdx = new Random().nextInt(webSites.length)
    webSites(randomIdx)
  }

}
