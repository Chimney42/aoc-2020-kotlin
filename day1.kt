import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("data/day1-input.txt").inputStream()
    val lineList = mutableListOf<Int>()

    inputStream.bufferedReader().forEachLine { lineList.add(it.toInt()) }
    var product: Int = 0;
    val listSize: Int = lineList.size
    print("listsize " + listSize + "\n")
     loop@ for (x: Int in 0..listSize) {
      for (y: Int in x+1..listSize) {
        if (y >= listSize) break
        for (z: Int in y+1..listSize) {
          if (z >= listSize) break
          if (2020 == lineList.get(x) + lineList.get(y) + lineList.get(z)) {
            print("x: " + lineList.get(x) + "\n")
            print("y: " + lineList.get(y) + "\n")
            print("z: " + lineList.get(z) + "\n")
            product = x * y * z
            break@loop
          }
        }
      }
    }
    print(product.toString() + "\n");
}