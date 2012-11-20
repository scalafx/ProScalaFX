package sfxext.application

import javafx.{application => jfxa}


/** Application platform support, wrapper for [[javafx.application.Platform]]. */
object Platform {

  /** Causes the JavaFX application to terminate. */
  def exit() {
    jfxa.Platform.exit()
  }

  /** Returns true if the calling thread is the JavaFX Application Thread. */
  def isFxApplicationThread: Boolean = jfxa.Platform.isFxApplicationThread

  /** Gets the value of the implicitExit attribute. */
  def implicitExit: Boolean = jfxa.Platform.isImplicitExit

  /** Sets the implicitExit attribute to the specified value. */
  def implicitExit_=(implicitExit: Boolean) {
    jfxa.Platform.setImplicitExit(implicitExit)
  }

  /** Queries whether a specific conditional feature is supported by the platform. */
  def isSupported(feature: jfxa.ConditionalFeature) = jfxa.Platform.isSupported(feature)

  /** Run the specified Runnable on the JavaFX Application Thread at some unspecified time in the future.
    * Returns immediately.
    */
  def runLater(runnable: java.lang.Runnable) {
    jfxa.Platform.runLater(runnable)
  }

  /** Run the specified code block on the JavaFX Application Thread at some unspecified time in the future.
    * Returns immediately.
    */
  def runLater(op: => Unit) {
    runLater(new Runnable {
      def run() {
        op
      }
    })
  }
}
