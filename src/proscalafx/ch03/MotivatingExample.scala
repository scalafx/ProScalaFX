package proscalafx.ch03

import javafx.{ beans => jfxb }
import scalafx.Includes._
import scalafx.beans.property.IntegerProperty

object MotivatingExample extends App {

  var intProperty: IntegerProperty = _

  def createProperty {
    println
    intProperty = IntegerProperty(1024)
    println("intProperty = " + intProperty);
    println("intProperty.get = " + intProperty.get);
    println("intProperty.getValue = " + intProperty.getValue.intValue);
  }

  def addAndRemoveInvalidationListener {
    println
    // IMPLEMENTATION NOTE: It is necessary explicit the closure below as  
    //JavaFX's InvalidationListener to allow its posterior remotion 
    val invalidationListener: jfxb.InvalidationListener =
      (observable: jfxb.Observable) => println("The observable has been invalidated: " + observable + ".")
    intProperty.addListener(invalidationListener)
    println("Added invalidation listener.")

    println("Calling intProperty.set(2048).")
    intProperty() = 2048

    println("Calling intProperty.setValue(3072).")
    intProperty() = Integer.valueOf(3072)

    intProperty.delegate.removeListener(invalidationListener)
    System.out.println("Removed invalidation listener.")

    println("Calling intProperty.set(4096).")
    intProperty() = 4096
  }

  def addAndRemoveChangeListener {
    println
    val changeListener = (observable: IntegerProperty, oldValue: Int, newValue: Int) => {
      println("The observableValue has changed: oldValue = " + oldValue + ", newValue = " + newValue)
    }

    //    intProperty.onChange(changeListener)
    println("Added change listener.")
  }

  def bindAndUnbindOnePropertyToAnother {
    println
    val otherProperty = IntegerProperty(0)
    println("otherProperty.get = " + otherProperty.get)

    println("Binding otherProperty to intProperty.")
    otherProperty <== intProperty
    println("otherProperty.get = " + otherProperty.get)

    println("Calling intProperty.set(7168).")
    intProperty() = 7168
    println("otherProperty.get = " + otherProperty.get)

    println("Unbinding otherProperty from intProperty.")
    otherProperty.unbind
    println("otherProperty.get = " + otherProperty.get)

    println("Calling intProperty.set(8192).")
    intProperty() = 8192
    println("otherProperty.get = " + otherProperty.get);
  }

  createProperty
  addAndRemoveInvalidationListener
  //  addAndRemoveChangeListener
  bindAndUnbindOnePropertyToAnother

}