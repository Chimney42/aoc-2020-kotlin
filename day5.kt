import java.io.File
import java.io.InputStream

fun convertBinaryToDecimal(binaryNumber: String): Int {
    var num = binaryNumber.toLong()
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

fun convertDecimalToBinary(decimalNumber: Int): Long {
    var n = decimalNumber
    var binaryNumber: Long = 0
    var remainder: Int
    var i = 1
    var step = 1

    while (n != 0) {
        remainder = n % 2
        System.out.printf("Step %d: %d/2, Remainder = %d, Quotient = %d\n", step++, n, remainder, n / 2)
        n /= 2
        binaryNumber += (remainder * i).toLong()
        i *= 10
    }
    return binaryNumber
}

fun main() {
  val inputStream: InputStream = File("data/day5-input.txt").inputStream()

  val allSeatsIds = IntRange(0, 127 * 8 + 7)
  val occupiedSeatIds = mutableListOf<Int>()
  inputStream.bufferedReader().forEachLine {
    val binarySeatId = it.replace("F", "0").replace("B", "1").replace("L", "0").replace("R", "1")
    val seatId = convertBinaryToDecimal(binarySeatId)
    occupiedSeatIds.add(seatId)
  }
  var mySeatId: Int = -1
  allSeatsIds.forEach {
    if(!occupiedSeatIds.contains(it) && occupiedSeatIds.contains(it+1) && occupiedSeatIds.contains(it-1)) {
      mySeatId = it
    }
  }
  println(mySeatId)
}