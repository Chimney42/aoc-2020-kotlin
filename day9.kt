import java.io.File
import java.io.InputStream

class Instruction(code: String, number: Int) {
  val code: String = code
  val number: Int = number
}

fun main() {
  val inputStream: InputStream = File("data/day9-input.txt").inputStream()
  val last25Numbers = mutableListOf<Long>()
  var wrongNumber: Long = -1
  val preambleLength = 25
  inputStream.bufferedReader().forEachLine {
    if (last25Numbers.size >= preambleLength) {
      var numberIsValid = false
      loop@ for (i in 0 until preambleLength) {
        for (j in 1 until preambleLength-1) {
          if (last25Numbers[i] + last25Numbers[j] == it.toLong()) {
            numberIsValid = true
            break@loop
          }
        }
      }
      if (!numberIsValid) {
        wrongNumber = it.toLong()
      }
      last25Numbers.removeAt(0)
    }
    last25Numbers.add(it.toLong())
  }
  println(wrongNumber)
}