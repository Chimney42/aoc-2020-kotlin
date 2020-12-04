import java.io.File
import java.io.InputStream

class Passport {
  var byr: String? = null
  var iyr: String? = null
  var eyr: String? = null
  var hgt: String? = null
  var hcl: String? = null
  var ecl: String? = null
  var pid: String? = null
  var cid: String? = null

  internal fun isByrValid(): Boolean {
    if (null == byr) return false
    val birthYear = byr!!.toInt()
    return birthYear <= 2002 && birthYear >= 1920
  }

  internal fun isIyrValid(): Boolean {
    if (null == iyr) return false
    val issueYear = iyr!!.toInt()
    return issueYear <= 2020 && issueYear >= 2010
  }

  internal fun isEyrValid(): Boolean {
    if (null == eyr) return false
    val expirationYear = eyr!!.toInt()
    return expirationYear <= 2030 && expirationYear >= 2020
  }

  internal fun isHgtValid(): Boolean {
    if (null == hgt) return false
    val heightPattern = "^(\\d+)(\\w+)$".toRegex()
    val matchResult = heightPattern.find(hgt.toString())
    val (number, unit) = matchResult!!.destructured
    return when (unit) {
      "cm" -> number.toInt() <= 193 && number.toInt() >= 150
      "in" -> number.toInt() <= 76 && number.toInt() >= 59
      else -> false
    }
  }

  internal fun isHclValid(): Boolean {
    if (null == hcl) return false
    val colourPattern = "^(#{1})([0-9a-f]{6})$".toRegex()
    val matchResult = colourPattern.find(hcl.toString())
    if (null == matchResult) return false
    val (hash, hexValue) = matchResult.destructured
    return !hash.isEmpty() && !hexValue.isEmpty()

  }

  internal fun isEclValid(): Boolean {
    val validColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    return validColors.contains(ecl)
  }

  internal fun isPidValid(): Boolean {
    if (null == pid) return false
    val idPattern = "^\\d{9}$".toRegex()
    return idPattern.matches(pid.toString())
  }

  fun isValid(): Boolean {
    return isByrValid() && isIyrValid() && isEyrValid() && isHgtValid() && isHclValid() && isEclValid() && isPidValid()
  }
}

fun readPassports(): MutableList<Passport> {
  val passports = mutableListOf<Passport>()
  var currentPassport: Passport = Passport()

  val inputStream: InputStream = File("data/day4-input.txt").inputStream()
  inputStream.bufferedReader().forEachLine {
    if (it.isEmpty()) {
      passports.add(currentPassport)
      currentPassport = Passport()
    } else {
      val passportData = it.split(" ")
      passportData.forEach {
        val dataSet = it.split(":")
        when (dataSet[0]) {
          "byr" -> currentPassport.byr = dataSet[1]
          "iyr" -> currentPassport.iyr = dataSet[1]
          "eyr" -> currentPassport.eyr = dataSet[1]
          "hgt" -> currentPassport.hgt = dataSet[1]
          "hcl" -> currentPassport.hcl = dataSet[1]
          "ecl" -> currentPassport.ecl = dataSet[1]
          "pid" -> currentPassport.pid = dataSet[1]
          "cid" -> currentPassport.cid = dataSet[1]
        }
      }
    }
  }
  passports.add(currentPassport)

  return passports
}

fun main() {
  val passports = readPassports()
  var validPassportCount = passports.fold(0) { total, passport -> if (passport.isValid()) total+1 else total }
  println(validPassportCount)
}