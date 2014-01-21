package proscalafx.ch03

import scalafx.beans.property.IntegerProperty

object MotivatingExample extends App {

  var intProperty: IntegerProperty = _


  def createProperty() {
    println()
    intProperty = IntegerProperty(1024)
    println("intProperty = " + intProperty)
    println("intProperty.get = " + intProperty.get)
    println("intProperty.value = " + intProperty.value)
    println("intProperty() = " + intProperty())
  }


  def addAndRemoveInvalidationListener() {
    println()
    val subscription = intProperty.onInvalidate {
      observable => println("The observable has been invalidated: " + observable + ".")
    }

    println("Added invalidation listener.")

    println("Calling intProperty.set(2048).")
    intProperty() = 2048

    println("Calling intProperty.setValue(3072).")
    intProperty() = Integer.valueOf(3072)

    subscription.cancel()
    System.out.println("Removed invalidation listener.")

    println("Calling intProperty.set(4096).")
    intProperty() = 4096
  }


  def addAndRemoveChangeListener() {
    println()
    val subscription = intProperty.onChange {
      (_, oldValue, newValue) =>
        println("The observableValue has changed: oldValue = " + oldValue + ", newValue = " + newValue)
    }
    println("Added change listener.")

    println("Calling intProperty.set(5120).")
    intProperty() = 5120

    subscription.cancel()
    println("Removed change listener.")

    println("Calling intProperty.set(6144).")
    intProperty() = 6144
  }


  def bindAndUnbindOnePropertyToAnother() {
    println()
    val otherProperty = IntegerProperty(0)
    println("otherProperty() = " + otherProperty())

    println("Binding otherProperty to intProperty.")
    otherProperty <== intProperty
    println("otherProperty() = " + otherProperty())

    println("Calling intProperty.set(7168).")
    intProperty() = 7168
    println("otherProperty.get = " + otherProperty.get)

    println("Unbinding otherProperty from intProperty.")
    otherProperty.unbind()
    println("otherProperty() = " + otherProperty())

    println("Calling intProperty.set(8192).")
    intProperty() = 8192
    println("otherProperty() = " + otherProperty())
  }


  createProperty()
  addAndRemoveInvalidationListener()
  addAndRemoveChangeListener()
  bindAndUnbindOnePropertyToAnother()

}