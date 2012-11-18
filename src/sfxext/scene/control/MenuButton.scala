package sfxext.scene.control

import javafx.scene.{control => jfxsc}
import scala.collection.JavaConversions.asJavaCollection
import scalafx.Includes._
import scalafx.geometry.Side
import scalafx.scene.Node
import scalafx.scene.Node._
import scalafx.scene.control.{MenuItem, ButtonBase}
import scalafx.util.SFXDelegate


object MenuButton {
  implicit def sfxToggleButton2jfx(mb: MenuButton) = mb.delegate
}


class MenuButton(override val delegate: jfxsc.MenuButton = new jfxsc.MenuButton)
  extends ButtonBase(delegate)
  with SFXDelegate[jfxsc.MenuButton] {

  /**
   * Creates a toggle button with the specified text as its label.
   */
  def this(text: String) = this(new jfxsc.MenuButton(text))

  /**
   * Creates a toggle button with the specified text and icon for its label.
   */
  def this(text: String, graphic: Node) = this(new jfxsc.MenuButton(text, graphic))

  /**
   * The items to show within this buttons menu.
   */
  def items = delegate.getItems

  def items_=(v: Iterable[MenuItem]) = {
    items.addAll(v.map(_.delegate))
  }

  /**
   * Indicates on which side the ContextMenu should open in relation to the MenuButton.
   */
  def popupSide = delegate.popupSideProperty()

  def popupSide_=(side: Side) {
    popupSide() = side
  }

  /**
   * Hides the ContextMenu if it was previously showing, and any showing submenus.
   */
  def hide() {
    delegate.hide()
  }

  /**
   * Gets the value of the property showing.
   */
  def showing = delegate.isShowing

  /**
   * If the Menu is not disabled and the ContextMenu is not already showing, then this will cause the ContextMenu to be shown.
   */
  def show() {
    delegate.show()
  }
}
