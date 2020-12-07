import java.io.File
import java.io.InputStream

fun buildTree(): HashMap<String,HashMap<String, Int>> {
  val inputStream: InputStream = File("data/day7-input.txt").inputStream()
  val outerInnerBagMap = hashMapOf<String,HashMap<String, Int>>()
  val bagColorPattern = "(\\d)* *(\\w+ \\w+) bag".toRegex()
  
  inputStream.bufferedReader().forEachLine {
    val outerInnerBagsList = it.split(" contain ")
    val outerBagColor = bagColorPattern.find(outerInnerBagsList[0])!!.destructured.toList().get(1)
    val innerBagsList = outerInnerBagsList[1].split(", ")
    innerBagsList.forEach {
      val (count, innerBagColor) = bagColorPattern.find(it)!!.destructured
      if ("no other" != innerBagColor) {
        var innerBagList = outerInnerBagMap.get(outerBagColor)
        if (innerBagList == null) { innerBagList = hashMapOf<String, Int>() }
        innerBagList.put(innerBagColor, count.toInt())
        outerInnerBagMap.put(outerBagColor, innerBagList)
      }
    }
  }
  return outerInnerBagMap
}

fun traverse(root: String, tree: HashMap<String,HashMap<String, Int>>, multiplier: Int, bagCount: Int): Int {
  var currentCount = bagCount
  val traversedBags = mutableSetOf<String>()
  val innerBagList = tree.get(root)
  if (innerBagList != null) {
    innerBagList.forEach {
      println("color " + it.key)
      println("count " + it.value)
      currentCount += it.value * multiplier
      traversedBags.add(it.key)
      println("currentCount " + currentCount)
      currentCount = traverse(it.key, tree, it.value * multiplier, currentCount)
    }
  }
  return currentCount
}

fun main() {
  val outerInnerBagMap = buildTree()
  println("map " + outerInnerBagMap)
  val startColor = "shiny gold"

  println(traverse(startColor, outerInnerBagMap, 1, 0))
}