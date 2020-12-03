import java.io.File
import java.io.InputStream

fun main() {
  val inputStream: InputStream = File("data/day2-input.txt").inputStream()
  val lineList = mutableListOf<String>()
  inputStream.bufferedReader().forEachLine { lineList.add(it) }

  val policyPasswordPattern = "^(\\d+)-(\\d+) (\\w+): (\\w+)$".toRegex()
  val isPasswordValid = { line: String ->
    val matchResult = policyPasswordPattern.find(line)
    var (firstIndex, secondIndex, letter, password) = matchResult!!.destructured
    println("firstIndex " + firstIndex)
    println("secondIndex " + secondIndex)
    println("letter " + letter)
    println("password " + password)
    
    val letterAtFirstIndex = password.get(firstIndex.toInt() - 1)
    val letterAtSecondIndex = password.get(secondIndex.toInt() - 1)
    (letterAtFirstIndex.toString() == letter) xor (letterAtSecondIndex.toString() == letter)
  }
  val validPasswordCount = lineList.count(isPasswordValid)
  println("validPasswordCount " + validPasswordCount)
}