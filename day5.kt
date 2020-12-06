import java.io.File
import java.io.InputStream

fun findRow(instructions: String): Int {
  var currentRange = IntRange(0, 128)
  instructions.forEach {
    currentRange = when (it) {
      "F".single() -> IntRange(currentRange.first(), currentRange.last() - currentRange.count() / 2)
      "B".single() -> IntRange(currentRange.first() + currentRange.count() / 2, currentRange.last()) 
      else -> currentRange
    }
  }
  return currentRange.first()
}

fun findColumn(instructions: String): Int {
  var currentRange = IntRange(0, 8)
  instructions.forEach {
    currentRange = when (it) {
      "L".single() -> IntRange(currentRange.first(), currentRange.last() - currentRange.count() / 2)
      "R".single() -> IntRange(currentRange.first() + currentRange.count() / 2, currentRange.last()) 
      else -> currentRange
    }
  }
  return currentRange.first()
}

fun calculateSeatId(row: Int, column: Int): Int {
  return row * 8 + column
}

fun main() {
  val inputStream: InputStream = File("data/day5-input.txt").inputStream()

  val allSeatsIds = IntRange(0, 127 * 8 + 7)
  val occupiedSeatIds = mutableListOf<Int>()
  inputStream.bufferedReader().forEachLine {
    val row = findRow(it.slice(IntRange(0, 6)))
    val column = findColumn(it.slice(IntRange(7, 9)))
    val seatId = calculateSeatId(row, column)
    occupiedSeatIds.add(seatId)
  }
  var mySeatId: Int = -1
  allSeatsIds.forEach {
    if(!occupiedSeatIds.contains(it) && occupiedSeatIds.contains(it+1) && occupiedSeatIds.contains(it-1)) {
      mySeatId = it
    }
  }
  println(mySeatId)
}