import java.io.File
import java.io.InputStream

class Instruction(code: String, number: Int) {
  val code: String = code
  val number: Int = number
}

fun main() {
  val inputStream: InputStream = File("data/day8-input.txt").inputStream()
  val instructions = mutableListOf<Instruction>()
  val instructionPattern = "^(\\w+) ((?>\\+|-)\\d+)$".toRegex()
  inputStream.bufferedReader().forEachLine {
    val matchResult = instructionPattern.find(it)!!
    val (code, number) = matchResult.destructured
    instructions.add(Instruction(code, number.toInt()))
  }
  
  var changeInstructionIndex = 0
  var accumulator: Int = 0
  try {
    while (true) {
      var nextInstructionIndex = 0
      val executedInstructionIndices = mutableSetOf<Int>()
      accumulator = 0
      while (!executedInstructionIndices.contains(nextInstructionIndex)) {
        executedInstructionIndices.add(nextInstructionIndex)
        val nextInstruction = instructions.get(nextInstructionIndex)
        val code = if (nextInstructionIndex == changeInstructionIndex && nextInstruction.code == "jmp") "nop" else if (nextInstructionIndex == changeInstructionIndex && nextInstruction.code == "jump") "jmp" else nextInstruction.code
        when (code) {
          "jmp" -> nextInstructionIndex += nextInstruction.number
          "acc" -> {
            nextInstructionIndex += 1
            accumulator += nextInstruction.number
          }
          else -> nextInstructionIndex += 1
        }
      }
      changeInstructionIndex++
    }  
  } catch (e: IndexOutOfBoundsException) {
    println(accumulator)
  }
}