package sfxext.scene.control

import javafx.scene.{control => jfxsc}
import scalafx.util.SFXDelegate


object SplitMenuButton {
  implicit def sfxSplitMenuButton2jfx(smb: SplitMenuButton) = smb.delegate
}

/** Wrapper for `javafx.scene.control.SplitMenuButton`. */
class SplitMenuButton(override val delegate: jfxsc.SplitMenuButton = new jfxsc.SplitMenuButton)
  extends MenuButton(delegate)
  with SFXDelegate[jfxsc.SplitMenuButton]
