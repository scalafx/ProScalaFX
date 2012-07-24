package proscalafx.ch03

//import scalafx.beans.InvalidationListener
import scalafx.beans.Observable
import scalafx.beans.property.IntegerProperty
//import scalafx.beans.property.SimpleIntegerProperty
//import scalafx.beans.value.ChangeListener
import scalafx.beans.value.ObservableValue
import scalafx.Includes._

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
    val invalidationListener = (observable: Observable) => println("The observable has been invalidated: " + observable + ".")
    intProperty.onInvalidate(invalidationListener)
    println("Added invalidation listener.")

    println("Calling intProperty.set(2048).")
    intProperty() = 2048

    println("Calling intProperty.setValue(3072).")
    intProperty() = Integer.valueOf(3072)

    System.err.println("SCALAFX LIMITATION: As it is configurated now, is not possible remove a function as invalidation listener")

    //intProperty.delegate.removeListener(invalidationListener)         
    //System.out.println("Removed invalidation listener.");     

//    println("Calling intProperty.set(4096).")
//    intProperty() = 4096
  }
  
  def addAndRemoveChangeListener {
    println
    val changeListener = (observable: IntegerProperty, oldValue: Int, newValue: Any) => {
      println("The observableValue has changed: oldValue = " + oldValue + ", newValue = " + newValue)
    }
    
//    intProperty.onChange(changeListener)
    println("Added change listener.")
  }

  createProperty
  addAndRemoveInvalidationListener

}