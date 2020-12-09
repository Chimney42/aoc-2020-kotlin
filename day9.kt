import java.io.File
import java.io.InputStream

class Instruction(code: String, number: Int) {
  val code: String = code
  val number: Int = number
}

fun main() {
  val inputStream: InputStream = File("data/day9-input.txt").inputStream()
  val last25Numbers = mutableListOf<Long>()
  val wrongNumber: Long = 507622668
  //val wrongNumber: Long = 127
  val preambleLength = 25
  inputStream.bufferedReader().forEachLine {
    if (last25Numbers.size >= preambleLength) {
      loop@ for (i in 0 until preambleLength-1) {
        for (j in 1 until preambleLength) {
          val range = last25Numbers.slice(IntRange(i, j))
          val sum = range.sum()
          if (sum == wrongNumber) {
            val max = range.maxOrNull()!!
            val min = range.minOrNull()!!
            println(max + min)
            break@loop
          }
        }
      }
      last25Numbers.removeAt(0)
    }
    last25Numbers.add(it.toLong())
  }
}