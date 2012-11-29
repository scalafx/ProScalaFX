package sfxext.bean.property

import javafx.beans.{property => jfxbp}
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.util.SFXDelegate
import scalafx.beans.value.ObservableValue

/**
 * @author Jarek Sacha 
 */
object ReadOnlyObjectWrapper {
  implicit def sfxReadOnlyObjectWrapper2jfx[T <: Object](roow: ReadOnlyObjectWrapper[T]) = roow.delegate

}

/**
 * Wrapper for [[javafx.beans.property.ReadOnlyObjectWrapper]]
 */
class ReadOnlyObjectWrapper[T <: AnyRef](override val delegate: jfxbp.ReadOnlyObjectWrapper[T])
  extends ObjectProperty[T](delegate)
  with SFXDelegate[jfxbp.ReadOnlyObjectWrapper[T]] {

  def this(bean: Object, name: String) = this(new jfxbp.ReadOnlyObjectWrapper[T](bean, name))

  def this(bean: Object, name: String, initialValue: T) =
    this(new jfxbp.ReadOnlyObjectWrapper[T](bean, name, initialValue))

  def readOnlyProperty = delegate.getReadOnlyProperty
}
