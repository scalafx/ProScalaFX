package scalafx.testutil

import java.util.concurrent.CountDownLatch
import javafx.application.Application
import javafx.stage.Stage

object BootstrapApplication {
  private val launchLatch = new CountDownLatch(1)
  var launched = false
  def launch() {
    if (!launched) {
      new Thread(new Runnable() {
        def run() {
          Application.launch(classOf[BootstrapApplication])
        }
      }).start()
      launchLatch.await();
      launched = true
    }
  }
}

class BootstrapApplication extends Application {
  override def start(stage: Stage) {
    BootstrapApplication.launchLatch.countDown()
  }
}
