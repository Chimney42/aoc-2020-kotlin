import java.io.File
import java.io.InputStream

fun convertDecimalToBinaryMap(decimalNumber: Int): HashMap<Int, Int> {
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

fun convertBinaryMapToString(binaryRepresenation: MutableMap<Int,Int>): String {
  var s = ""
  for (x in 0 until 36) {
    s += binaryRepresenation.getOrDefault(35 - x, 0).toString()
  }
  return s
}

fun convertStringToBinaryMap(binaryString: String): MutableMap<Int, Int> {
  var s = binaryString
  var m = hashMapOf<Int, Int>()
  for (x in 0 until binaryString.length) {
    if (s.get(x).toString().equals("1")) {
      m.put(35-x, 1)
    }
  }
  return m
}

fun findAddresses(memoryAddress: String, mask: HashMap<Int, String>): MutableSet<String> {
  val addressInBinary = convertDecimalToBinaryMap(memoryAddress.toInt())
  val overWrite = mask.filterValues{it.equals("1")}.mapValues{it.value.toInt()}
  addressInBinary.putAll(overWrite)

  val superPositionKeys = mask.filter{it.value.equals("X")}.keys
  val addresses = mutableSetOf<String>()
  addresses.add(convertBinaryMapToString(addressInBinary))
  superPositionKeys.forEach {
    val key = it
    val newAdresses = mutableSetOf<String>()
    addresses.forEach {
      val addressMap = convertStringToBinaryMap(it)
      listOf(0, 1).forEach {
        val address = addressMap.toMutableMap()
        address.put(key, it)
        newAdresses.add(convertBinaryMapToString(address))
      }
    }
    addresses.addAll(newAdresses)
  }
  return addresses
}

fun main() {
  var mask: HashMap<Int, String> = hashMapOf<Int,String>()
  val memory = hashMapOf<String, Long>()
  File("data/day14-input.txt").bufferedReader().forEachLine {
    val command = it.split(" = ")
    if (command[0].equals("mask")) {
      mask = hashMapOf<Int, String>()
      var index = 0
      command[1].forEach {
        if (!it.toString().equals("0")) {
          mask.put(35 - index, it.toString())
        }
        index++
      }
      println(mask.toString())
    } else {
      val (memoryAddress) = "(\\d+)".toRegex().find(command[0])!!.destructured
      var memoryValue = command[1].toLong()
      findAddresses(memoryAddress, mask).forEach {
        memory.put(it, memoryValue)
      }
      
      println(memory)
    }
  }
  println(memory.values.sumOf{it})
}