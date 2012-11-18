package sfxext.scene.control

import javafx.scene.{control => jfxsc}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalafx.testutil.AbstractSFXDelegateSpec
import sfxext.scene.control.ControlIncludes._

@RunWith(classOf[JUnitRunner])
class MenuButtonSpec
  extends AbstractSFXDelegateSpec[jfxsc.MenuButton, MenuButton, jfxsc.MenuButtonBuilder[_]](classOf[jfxsc.MenuButton], classOf[MenuButton], classOf[jfxsc.MenuButtonBuilder[_]]) {

  override protected def getScalaClassInstance = new MenuButton(new jfxsc.MenuButton())

  protected def convertScalaClassToJavaClass(sfxControl: MenuButton) = {
    val jfxMenuButton: jfxsc.MenuButton = sfxControl
    jfxMenuButton
  }

  override protected def getJavaClassInstance = new jfxsc.MenuButton()

  protected def convertJavaClassToScalaClass(jfxControl: jfxsc.MenuButton) = {
    val sfxMenuButton: MenuButton = jfxControl
    sfxMenuButton
  }


}
