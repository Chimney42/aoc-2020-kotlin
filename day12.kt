import java.io.File
import java.io.InputStream

enum class Direction {
  NORTH {
    override val diffX = 0
    override val diffY = -1
  },
  EAST {
    override val diffX = 1
    override val diffY = 0
  },
  SOUTH {
    override val diffX = 0
    override val diffY = 1
  },
  WEST {
    override val diffX = -1
    override val diffY = 0
  };
  abstract val diffX: Int
  abstract val diffY: Int
}

class Ship {
  class Waypoint {
    var diffX = 10
    var diffY = -1
  }

  var waypoint: Waypoint = Waypoint()
  var direction: Direction = Direction.EAST
  var currentX: Int = 0
  var currentY: Int = 0

  fun moveForward(value: Int) {
    currentX += waypoint.diffX * value
    currentY += waypoint.diffY * value
  }

  fun turn(orientation: String, value: Int) {
    for (i in 0 until value/90) {
      val diffX = when (orientation) {
        "R" -> waypoint.diffY * -1
        else -> waypoint.diffY
      }
      val diffY = when (orientation) {
        "L" -> waypoint.diffX * - 1
        else -> waypoint.diffX
      }

      waypoint.diffX = diffX
      waypoint.diffY = diffY
    }
  }
}

fun calculateManhattanDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
  return Math.abs(x1 - x2) + Math.abs(y1 - y2)
}

fun main() {
  val inputStream: InputStream = File("data/day12-input.txt").inputStream()
  val ship = Ship()
  inputStream.bufferedReader().forEachLine {
    val action = it.get(0).toString()
    val value = it.subSequence(1, it.length).toString().toInt()
    when(action) {
      "N" -> ship.waypoint.diffY -= value
      "S" -> ship.waypoint.diffY += value
      "E" -> ship.waypoint.diffX += value
      "W" -> ship.waypoint.diffX -= value
      "F" -> ship.moveForward(value)
      else -> ship.turn(action, value)
    }
    println("ship x " + ship.currentX)
    println("ship y " + ship.currentY)
    println("waypoint x " + ship.waypoint.diffX)
    println("waypoint y " + ship.waypoint.diffY)
  }
  println(calculateManhattanDistance(0, 0, ship.currentX, ship.currentY))

}