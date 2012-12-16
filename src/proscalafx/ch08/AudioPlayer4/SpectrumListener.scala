package proscalafx.ch08.AudioPlayer4

import javafx.scene.{media => jfxcm}
import scalafx.Includes._
import scalafx.scene.media.MediaPlayer

/**
 * @author Jarek Sacha 
 */
class SpectrumListener(startFreq: Double, mp: MediaPlayer, bars: Array[SpectrumBar]) extends jfxcm.AudioSpectrumListener {
  private val minValue = mp.audioSpectrumThreshold()
  private val norms = createNormArray()
  private val bandCount = mp.audioSpectrumNumBands()
  private val spectrumBucketCounts = createBucketCounts(startFreq, bandCount)

  def spectrumDataUpdate(timestamp: Double,
                         duration: Double,
                         magnitudes: Array[Float],
                         phases: Array[Float]) {
    var index = 0
    var bucketIndex = 0
    var currentBucketCount = 0
    var sum = 0.0

    while (index < magnitudes.length) {
      sum += magnitudes(index) - minValue
      currentBucketCount += 1

      if (currentBucketCount >= spectrumBucketCounts(bucketIndex)) {
        bars(bucketIndex).setValue(sum / norms(bucketIndex))
        currentBucketCount = 0
        sum = 0.0
        bucketIndex += 1
      }

      index += 1
    }
  }

  private def createNormArray(): Array[Double] = {
    val normArray = new Array[Double](bars.length)
    var currentNorm = 0.05
    for (i <- 0 until normArray.length) {
      normArray(i) = 1 + currentNorm
      currentNorm *= 2
    }
    normArray
  }

  private def createBucketCounts(startFreq: Double, bandCount: Int): Array[Int] = {
    val bucketCounts = new Array[Int](bars.length)

    val bandwidth = 22050.0 / bandCount
    val centerFreq = bandwidth / 2
    var currentSpectrumFreq = centerFreq
    var currentEQFreq = startFreq / 2
    var currentCutoff = 0d
    var currentBucketIndex = -1

    for (i <- 0 until bandCount) {
      if (currentSpectrumFreq > currentCutoff) {
        currentEQFreq *= 2
        currentCutoff = currentEQFreq + currentEQFreq / 2
        currentBucketIndex += 1
      }

      if (currentBucketIndex < bucketCounts.length) {
        bucketCounts(currentBucketIndex) += 1
        currentSpectrumFreq += bandwidth
      }
    }

    bucketCounts
  }
}
