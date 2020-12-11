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

fun getAdjacentOccupiedSeats(map: MutableList<MutableList<String>>, x: Int, y: Int): Int {
  var occupiedSeats = 0
  for (i in y - 1 until y + 2) {
    for (j in x - 1 until x + 2) {
      occupiedSeats += if (i == y && j == x) 0 else if (i < 0 || i >= map.size || j < 0 || j >= map[0].size) 0 else if (map[i][j] == "#") 1 else 0
    }
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
        } else if (currentSeatState == "#" && getAdjacentOccupiedSeats(oldMap, x, y) >= 4) {
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