import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    val inputStream: InputStream = File("data/day1-input.txt").inputStream()
    val lineList = mutableListOf<Int>()

    inputStream.bufferedReader().forEachLine { lineList.add(it.toInt()) }
    var product: Int = 0;
    for (x: Int in lineList) {
      for (y: Int in lineList) {
        for (z: Int in lineList) {
          if (x != y && y != z && x != z && 2020 == x + y + z) {
            print("x: " + x + "\n")
            print("y: " + y + "\n")
            print("z: " + z + "\n")
            product = x * y * z
            break
          }
        }
        if (0 != product) break
      }
      if (0 != product) break
    }
    print(product.toString() + "\n");
}