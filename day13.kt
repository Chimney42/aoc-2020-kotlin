import java.io.File
import java.io.InputStream

fun main() {
  val lines = File("data/day13-input.txt").readLines()
  val startTime = lines[0].toInt()
  val busses = lines[1].split(",").filter{ it != "x" }.map{ it.toInt() }
  println(startTime)
  println(busses)

  val waitingTimesByBus = busses.map {
    Math.ceil(startTime.toDouble() / it).toInt() * it - startTime
  }
  val shortestWaitingTime = waitingTimesByBus.minByOrNull{it} ?: 0
  val soonestBus = busses[waitingTimesByBus.indexOf(shortestWaitingTime)]
  println(soonestBus * shortestWaitingTime)
}