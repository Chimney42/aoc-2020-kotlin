import java.io.File
import java.io.InputStream

fun main() {
  val inputStream: InputStream = File("data/day6-input.txt").inputStream()
  var totalGroupQuestionCount = 0
  var currentGroupQuestions = mutableListOf<String>()
  inputStream.bufferedReader().forEachLine {
    if (it.isEmpty()) {
      totalGroupQuestionCount += currentGroupQuestions.distinct().size
      currentGroupQuestions = mutableListOf<String>()
    } else {
      val chars = it.split("")
      currentGroupQuestions.addAll(chars.slice(IntRange(1, chars.size-2)))
    }
  }
  totalGroupQuestionCount += currentGroupQuestions.distinct().size
  println(totalGroupQuestionCount)
}