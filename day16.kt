import java.io.File
import java.io.InputStream

fun main() {
  var currCategory = ""
  val rules = hashMapOf<String, List<IntRange>>()
  val myTicket = mutableListOf<Int>()
  val valuesByIndex = mutableListOf<MutableList<Int>>()
  File("data/day16-input.txt").bufferedReader().forEachLine {
    if (it.get(0).toString().equals("#")) {
      currCategory = it.slice(IntRange(2, it.length-2))
    } else {
      when (currCategory) {
        "rules" -> {
          val (rule, min1, max1, min2, max2) = "(\\D+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex().find(it)!!.destructured
          rules.put(rule, listOf(IntRange(min1.toInt(), max1.toInt()), IntRange(min2.toInt(), max2.toInt())))
        }
        "nearby tickets" -> {
          val values = it.split(",").map{ it.toInt() }
          val invalid = values.any {
            val value = it.toInt()
            rules.values.all {
              it.all{ !it.contains(value) }
            }
          }
          if (!invalid) {
            for (x in 0 until values.size) {
              if (valuesByIndex.size <= x) {
                valuesByIndex.add(mutableListOf<Int>())
              }
              valuesByIndex[x].add(values[x])
            }
          }
        }
        else -> {
          println("my ticket $it")
          myTicket.addAll(it.split(",").map{ it.toInt()})
        }
      }
    }
  }
  println("valuesByIndex $valuesByIndex")

  val possibleRulesByIndex = valuesByIndex.map {
    val listOfValues = it
    rules.keys.filter {
      val validRanges = rules.get(it)!!
      listOfValues.all {
        val value = it
        validRanges.any { it.contains(value) }
      }
    }.toMutableList()
  }
  println("possibleRulesByIndex $possibleRulesByIndex")

  val indexbyRule = HashMap<String,Int>()
  while (indexbyRule.size < possibleRulesByIndex.size) {
    possibleRulesByIndex.forEach {
      val listOfPossibleRules = it
      if (listOfPossibleRules.size == 1) {
        val rule = listOfPossibleRules[0]
        val index = possibleRulesByIndex.indexOf(listOfPossibleRules)
        indexbyRule.put(rule, index)
      } else {
        listOfPossibleRules.removeAll { indexbyRule.containsKey(it) }
      }
    }
  }
  println("indexByRule " + indexbyRule.toString())

  val product = indexbyRule.entries
    .filter {it.key.contains("departure")}
    .map {myTicket.getOrElse(it.value){1}}
    .map {it.toLong()}
    .reduce {acc, value -> acc * value}
  println(product)
}