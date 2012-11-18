package sfxext.scene.control

import javafx.scene.{control => jfxsc}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalafx.testutil.AbstractSFXDelegateSpec
import sfxext.scene.control.ControlIncludes._

@RunWith(classOf[JUnitRunner])
class SplitMenuButtonSpec
  extends AbstractSFXDelegateSpec[jfxsc.SplitMenuButton, SplitMenuButton, jfxsc.SplitMenuButtonBuilder[_]](classOf[jfxsc.SplitMenuButton], classOf[SplitMenuButton], classOf[jfxsc.SplitMenuButtonBuilder[_]]) {

  override protected def getScalaClassInstance = new SplitMenuButton(new jfxsc.SplitMenuButton())

  protected def convertScalaClassToJavaClass(sfxControl: SplitMenuButton) = {
    val jfxSplitMenuButton: jfxsc.SplitMenuButton = sfxControl
    jfxSplitMenuButton
  }

  override protected def getJavaClassInstance = new jfxsc.SplitMenuButton()

  protected def convertJavaClassToScalaClass(jfxControl: jfxsc.SplitMenuButton) = {
    val sfxSplitMenuButton: SplitMenuButton = jfxControl
    sfxSplitMenuButton
  }


}
