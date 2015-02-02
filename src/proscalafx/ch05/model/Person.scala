package proscalafx.ch05.model

import scalafx.beans.property.StringProperty


/**
 * @author Jarek Sacha
 */
class Person(firstName_ : String, lastName_ : String, phone_ : String) {

  val firstName = new StringProperty(this, "firstName", firstName_)
  val lastName = new StringProperty(this, "lastName", lastName_)
  val phone = new StringProperty(this, "phone", phone_)


  override def toString: String = {
    "Person: " + firstName() + " " + lastName()
  }
}
