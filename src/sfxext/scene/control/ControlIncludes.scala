package sfxext.scene.control

import javafx.scene.{control => jfxsc}

// NOTE: Should be merged with scalafx.scene.control.ControlIncludes
object ControlIncludes extends scalafx.scene.control.ControlIncludes {
  implicit def jfxMenuButton2sfx(h: jfxsc.MenuButton) = new MenuButton(h)

  implicit def jfxSplitMenuButton2sfx(h: jfxsc.SplitMenuButton) = new SplitMenuButton(h)

}
