fun main() {
  val numbersToIndex = hashMapOf(11.toLong() to 1.toLong(), 0.toLong() to 2.toLong(), 1.toLong() to 3.toLong(), 10.toLong() to 4.toLong(), 5.toLong() to 5.toLong())
  var lastNumber = 19.toLong()
  var index = 6.toLong()
  while (index < 30000000) {
    val lastIndex = numbersToIndex.getOrElse(lastNumber) {index}
    numbersToIndex.put(lastNumber, index)
    lastNumber = index - lastIndex
    index++
  }
  print(lastNumber)
} 