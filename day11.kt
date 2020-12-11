import java.io.File
import java.io.InputStream

fun buildMap(): MutableList<MutableList<String>> {
  val map = mutableListOf<MutableList<String>>()
  val inputStream: InputStream = File("data/day11-input.txt").inputStream()
  inputStream.bufferedReader().forEachLine {
    val line = it.split("")
    map.add(line.slice(IntRange(1, line.size - 2)).toMutableList())
  }
  return map
}

enum class Direction {
  NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
}

fun findFirstSeatInLineOfSight(map: MutableList<MutableList<String>>, x: Int, y: Int, dir: Direction): String {
  var diffX = when (dir) {
    Direction.NORTHEAST, Direction.EAST, Direction.SOUTHEAST -> 1
    Direction.NORTHWEST, Direction.WEST, Direction.SOUTHWEST -> -1
    else -> 0
  }

  var diffY = when (dir) {
    Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST -> -1
    Direction.SOUTHWEST, Direction.SOUTH, Direction.SOUTHEAST -> 1
    else -> 0
  }
  
  var nextX = x + diffX
  var nextY = y + diffY
  var nextSeatInSight = ""
  while (nextSeatInSight.isEmpty() && !(nextY < 0 || nextY >= map.size || nextX < 0 || nextX >= map[0].size)) {
    var nextTile = map[nextY][nextX]
    if (nextTile != ".") nextSeatInSight = nextTile
    nextX += diffX
    nextY += diffY
  }
  return nextSeatInSight
}

fun getAdjacentOccupiedSeats(map: MutableList<MutableList<String>>, x: Int, y: Int): Int {
  var occupiedSeats = 0
  for (direction in Direction.values()) {
    val firstSeat = findFirstSeatInLineOfSight(map, x, y, direction)
    occupiedSeats += if (firstSeat == "#") 1 else 0
  }
  return occupiedSeats
}

fun main() {
  var oldMap = buildMap()
  var newMap = oldMap
  do {
    oldMap = newMap
    newMap = mutableListOf<MutableList<String>>()
    for (y in 0 until oldMap.size) {
      val row = mutableListOf<String>()
      for (x in 0 until oldMap[0].size) {
        val currentSeatState = oldMap[y][x]
        if (currentSeatState == "L" && getAdjacentOccupiedSeats(oldMap, x, y) == 0) {
          row.add("#")
        } else if (currentSeatState == "#" && getAdjacentOccupiedSeats(oldMap, x, y) >= 5) {
          row.add("L")
        } else {
          row.add(currentSeatState)
        }
      }
      println(row)
      newMap.add(row)
    }
    println("\n")
  } while (!oldMap.equals(newMap))

  var occupiedSeats = 0
  for (row in newMap) {
    row.forEach{ occupiedSeats += if (it == "#") 1 else 0 }
  }
  println(occupiedSeats)
}