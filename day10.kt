import java.io.File
import java.io.InputStream


fun main() {
  val adapterJoltages = mutableListOf<Int>()
  adapterJoltages.add(0)
  val inputStream: InputStream = File("data/day10-input.txt").inputStream()
  inputStream.bufferedReader().forEachLine {
    adapterJoltages.add(it.toInt())
  }
  adapterJoltages.sort()
  adapterJoltages.add(adapterJoltages.last()+3)
  println(adapterJoltages)

  val pathsForAdapter: MutableMap<Int,Long> = mutableMapOf(0 to 1L)
  adapterJoltages.drop(1).forEach { adapter ->
      pathsForAdapter[adapter] = IntRange(1, 3).map { range -> 
          pathsForAdapter.getOrDefault(adapter - range, 0) 
      }.sum()
  }
  println(pathsForAdapter.getValue(adapterJoltages.last()))
}
