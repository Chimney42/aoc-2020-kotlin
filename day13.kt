import java.io.File
import java.io.InputStream

fun main() {
  val lines = File("data/day13-input.txt").readLines()
  val busIdToOffset = hashMapOf<Int, Int>()
  var index = 0
  lines[1].split(",").forEach {
    if (it != "x") {
      busIdToOffset.put(it.toInt(), index)
    }
    index++
  }
  println(busIdToOffset)

  var firstBus = busIdToOffset.entries.find { it.value == 0 }!!
  var cadence = firstBus.key.toLong()
  busIdToOffset.remove(firstBus.key)
  
  var currentT: Long = 0
  busIdToOffset.forEach {
    var tNotFound: Boolean = true
    do {
      if ((currentT + it.value) % it.key == 0.toLong()) {
        cadence *= it.key
        tNotFound = false
      } else {
        currentT += cadence
      }
    } while (tNotFound)
  }

  print(currentT)
}