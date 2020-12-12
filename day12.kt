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

class Ship() {
  var direction: Direction = Direction.EAST
  var currentX: Int = 0
  var currentY: Int = 0

  fun moveForward(value: Int) {
    currentX += direction.diffX * value
    currentY += direction.diffY * value
  }

  fun turn(orientation: String, value: Int) {
    var ordinalOffset = when (orientation) {
      "L" -> (value / 90) * -1 + 4
      else -> value / 90 + 4
    }
    val ordinalValue = (direction.ordinal + ordinalOffset) % 4
    direction = Direction.values()[ordinalValue]
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
      "N" -> ship.currentY = ship.currentY - value
      "S" -> ship.currentY = ship.currentY + value
      "E" -> ship.currentX = ship.currentX + value
      "W" -> ship.currentX = ship.currentX - value
      "F" -> ship.moveForward(value)
      else -> ship.turn(action, value)
    }
    println(ship.currentX)
    println(ship.currentY)
    println(ship.direction.name)
  }
  println(calculateManhattanDistance(0, 0, ship.currentX, ship.currentY))

}