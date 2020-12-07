import java.io.File
import java.io.InputStream

fun buildTree(): HashMap<String,MutableList<String>> {
  val inputStream: InputStream = File("data/day7-input.txt").inputStream()
  val innerToOuterBagTree = hashMapOf<String,MutableList<String>>()
  val bagColorPattern = "(\\d)* *(\\w+ \\w+) bag".toRegex()
  
  inputStream.bufferedReader().forEachLine {
    val outerInnerBagsList = it.split(" contain ")
    val outerBagColor = bagColorPattern.find(outerInnerBagsList[0])!!.destructured.toList().get(1)
    val innerBagsList = outerInnerBagsList[1].split(", ")
    innerBagsList.forEach {
      val (count, innerBagColor) = bagColorPattern.find(it)!!.destructured
      if ("no other" != innerBagColor) {
        var outerBagListForInnerBag = innerToOuterBagTree.get(innerBagColor)
        if (outerBagListForInnerBag == null) { outerBagListForInnerBag = mutableListOf<String>() }
        outerBagListForInnerBag.add(outerBagColor)
        innerToOuterBagTree.put(innerBagColor, outerBagListForInnerBag)
      }
    }
  }
  return innerToOuterBagTree
}

fun traverse(root: String, tree: HashMap<String,MutableList<String>>, traversed: MutableSet<String>): MutableSet<String> {
  val traversedColors = traversed
  val outerBagList = tree.get(root)
  if (outerBagList != null) {
    traversedColors.addAll(outerBagList)
    outerBagList.forEach {
      traversedColors.addAll(traverse(it, tree, traversedColors))
    }
  }
  return traversedColors
}

fun main() {
  val innerToOuterBagTree = buildTree()
  println(innerToOuterBagTree)
  val startColor = "shiny gold"

  val traversedColors = traverse(startColor, innerToOuterBagTree, mutableSetOf<String>()).distinct()
  println(traversedColors)
  println(traversedColors.size)
}