import java.io.File
import java.io.InputStream

fun hasTree(x: Int, y: Int, map: List<List<String>>): Boolean {
  val realX = x%map.get(0).size
  return "#" == map.get(y).get(realX)
}

fun createMap(): List<List<String>> {
  val map = mutableListOf<List<String>>()

  val inputStream: InputStream = File("data/day3-input.txt").inputStream()
  inputStream.bufferedReader().forEachLine { 
      val row = it.split("")
      map.add(row.slice(IntRange(1, row.size-2)))
    }
  return map
}

fun main() {
  val map = createMap()
  val slopeX = 3
  val slopeY = 1

  var x = 0
  var y = 0
  var treeCount = 0
  while (y < map.size) {
    if (hasTree(x, y, map)) { treeCount++ }
    x += slopeX
    y += slopeY
  }

  println(treeCount)
}