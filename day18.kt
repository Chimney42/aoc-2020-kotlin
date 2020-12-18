import java.io.File
import java.io.InputStream


fun calculate(term: String): Long {
  println("full term: $term")
  val regexResult = "(\\([^()]+\\))".toRegex().find(term)
  if (regexResult != null) {
    val (termBetweenParenthesis) = regexResult.destructured
    val index = term.indexOf(termBetweenParenthesis)
    val newTerm = term.substring(0, index) + calculate(termBetweenParenthesis.slice(IntRange(1, termBetweenParenthesis.length-2))) + term.substring(index+termBetweenParenthesis.length)
    println("new term: $newTerm")
    return calculate(newTerm)
  } else {
    val calcList = term.split(" ")
    var i = 2
    var result = calcList[0].toLong()
    while (i < calcList.size) {
      result = when (calcList[i-1]) {
        "*" -> result * calcList[i].toLong()
        "+" -> result + calcList[i].toLong()
        else -> result
      }
      i += 2
    }
    println("result: " + result)
    return result
  }
}

fun main() {
  val allResults = mutableListOf<Long>()
  File("data/day18-input.txt").bufferedReader().forEachLine {
    val result = calculate(it)
    println("$it equals $result")
    allResults.add(result)
  }
  println("sum of all results " + allResults.sumOf{it})
}
