import java.io.File
import java.io.InputStream


fun main() {
  val adapterJoltages = mutableListOf<Int>()

  val inputStream: InputStream = File("data/day10-input.txt").inputStream()
  inputStream.bufferedReader().forEachLine { 
    adapterJoltages.add(it.toInt())
  }
  adapterJoltages.sort()
  adapterJoltages.add(adapterJoltages.last()+3)
  println(adapterJoltages)

  val joltageDifferences = hashMapOf<Int, Int>()
  var lastJoltage = 0
  adapterJoltages.forEach {
    val diff = it - lastJoltage
    val diffCount = joltageDifferences.get(diff) ?: 0
    joltageDifferences.put(diff, diffCount+1)
    lastJoltage = it
  }
  println(joltageDifferences.get(1)!! * joltageDifferences.get(3)!!)
}
