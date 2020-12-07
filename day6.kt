import java.io.File
import java.io.InputStream

fun main() {
  val inputStream: InputStream = File("data/day6-input.txt").inputStream()
  
  var totalGroupQuestionCount = 0
  val allQuestions = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
  var currentGroupQuestions = allQuestions.toMutableList()
  inputStream.bufferedReader().forEachLine {
    if (it.isEmpty()) {
      totalGroupQuestionCount += currentGroupQuestions.distinct().size
      currentGroupQuestions = allQuestions.toMutableList()
    } else {
      val chars = it.split("")
      currentGroupQuestions.retainAll(chars)
    }
  }
  totalGroupQuestionCount += currentGroupQuestions.distinct().size


  println(totalGroupQuestionCount)
}