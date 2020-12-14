import java.io.File
import java.io.InputStream

fun convertDecimalToBinary(decimalNumber: Int): HashMap<Int, Int> {
  var n = decimalNumber
  var binaryPosition = 35
  val binaryRepresenation = hashMapOf<Int, Int>()
  while (binaryPosition >= 0) {
    val currentBinaryInDecimal = Math.pow(2.toDouble(), binaryPosition.toDouble())
    if (n >= currentBinaryInDecimal) {
      val binaryValue = Math.floor(n / currentBinaryInDecimal)
      binaryRepresenation.put(binaryPosition, binaryValue.toInt())
      n -= currentBinaryInDecimal.toInt() * binaryValue.toInt()
    }
    binaryPosition--
  }
  return binaryRepresenation
}

fun convertBinaryToDecimal(binaryRepresenation: HashMap<Int,Int>): Long {
  var n = 0.toLong()
  binaryRepresenation.forEach {
    n += Math.pow(2.toDouble(), it.key.toDouble()).toLong() * it.value
  }
  return n
}

fun main() {
  var mask: HashMap<Int, Int> = hashMapOf<Int,Int>()
  val memory = hashMapOf<String, Long>()
  File("data/day14-input.txt").bufferedReader().forEachLine {
    val command = it.split(" = ")
    if (command[0].equals("mask")) {
      mask = hashMapOf<Int, Int>()
      var index = 0
      command[1].forEach {
        if (!it.toString().equals("X")) {
          mask.put(35 - index, it.toString().toInt())
        }
        index++
      }
      println(mask.toString())
    } else {
      val (memoryAddress) = "(\\d+)".toRegex().find(command[0])!!.destructured
      var memoryValueInBinary = convertDecimalToBinary(command[1].toInt())
      println("memoryAddress: $memoryAddress")
      memoryValueInBinary.putAll(mask)
      val memoryValueInDecimal = convertBinaryToDecimal(memoryValueInBinary)
      println("memoryValueInDecimal: $memoryValueInDecimal")
      memory.put(memoryAddress, memoryValueInDecimal)
      println(memory)
    }
  }
  println(memory.values.sumOf{it})
}