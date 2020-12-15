import java.io.File
import java.io.InputStream

fun main() {
  val numbers = File("data/day15-input.txt").readLines()[0].split(",").map{it.toInt()}.toMutableList()
  val calledNumbers = mutableSetOf<Int>()
  while (numbers.size < 2020) {
    println(numbers)
    val lastNumber = numbers.last()
    var newNumber: Int = 0
    println(calledNumbers)
    if (calledNumbers.contains(lastNumber)) {
      val lastIndex = numbers.dropLast(1).indexOfLast{it.equals(lastNumber)}
      newNumber = numbers.lastIndex - lastIndex
    }
    numbers.add(newNumber)
    if (numbers.count{it.equals(newNumber)} >= 2) {
      calledNumbers.add(newNumber)
    }
  }
  print(numbers.last())
} 