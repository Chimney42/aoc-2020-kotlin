import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
  val inputStream: InputStream = File("data/day2-input.txt").inputStream()
  val lineList = mutableListOf<String>()
  inputStream.bufferedReader().forEachLine { lineList.add(it) }

  val policyPasswordPattern = "^(\\d+)-(\\d+) (\\w+): (\\w+)$".toRegex()
  val isPasswordValid = { line: String ->
    val matchResult = policyPasswordPattern.find(line)
    val (minCount, maxCount, letter, password) = matchResult!!.destructured
    println("minCount " + minCount)
    println("maxCount " + maxCount)
    println("letter " + letter)
    println("password " + password)
    val letterCount = password.count{ letter.contains(it) }
    letterCount >= minCount.toInt() && letterCount <= maxCount.toInt()
  }
  val validPasswordCount = lineList.count(isPasswordValid)
  println("validPasswordCount " + validPasswordCount)
}