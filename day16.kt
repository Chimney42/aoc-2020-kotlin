import java.io.File
import java.io.InputStream

fun main() {
  var currCategory = ""
  val rules = hashMapOf<String, List<IntRange>>()
  val invalidValues = mutableListOf<Int>()
  File("data/day16-input.txt").bufferedReader().forEachLine {
    if (it.get(0).toString().equals("#")) {
      currCategory = it.slice(IntRange(2, it.length-2))
    } else {
      println(currCategory)
      when (currCategory) {
        "rules" -> {
          val (rule, min1, max1, min2, max2) = "(\\D+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex().find(it)!!.destructured
          rules.put(rule, listOf(IntRange(min1.toInt(), max1.toInt()), IntRange(min2.toInt(), max2.toInt())))
        }
        "nearby tickets" -> {
          invalidValues.addAll(it.split(",").map{it.toInt()}.filter {
            val value = it.toInt()
            rules.values.all {
              val ranges = it
              ranges.all {
                !it.contains(value)
              }
            }
          })
        }
        "else" -> {}
      }
    }
  }
  println(invalidValues)
  println(invalidValues.sumBy{it})
}