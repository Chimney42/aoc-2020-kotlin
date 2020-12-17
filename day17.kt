import java.io.File
import java.io.InputStream

data class ActiveCube(val x: Int, val y: Int, val z: Int, val w: Int)

fun getInitialActiveCubes(): MutableList<ActiveCube> {
  val activeCubes = mutableListOf<ActiveCube>()
  val z = 0
  val w = 0
  var y = 0
  File("data/day17-input.txt").bufferedReader().forEachLine {
    var x = 0
    it.split("").slice(IntRange(1, 8)).forEach {
      if (it.toString().equals("#")) {
        activeCubes.add(ActiveCube(x, y, z, w))
      }
      x++
    }
    y++
  }
  return activeCubes
}

fun main() {
  var activeCubes = getInitialActiveCubes()
  println(activeCubes.size)

  for (i in 1 until 7) {
      //find range for each coordinate
    val minX = activeCubes.minByOrNull {it.x}!!.x
    val maxX = activeCubes.maxByOrNull {it.x}!!.x
    val minY = activeCubes.minByOrNull {it.y}!!.y
    val maxY = activeCubes.maxByOrNull {it.y}!!.y
    val minZ = activeCubes.minByOrNull {it.z}!!.z
    val maxZ = activeCubes.maxByOrNull {it.z}!!.z
    val minW = activeCubes.minByOrNull {it.w}!!.w
    val maxW = activeCubes.maxByOrNull {it.w}!!.w
    
    val activeCubesNextCycle = mutableListOf<ActiveCube>()
    for (z in minZ-1 until maxZ+2) {
      for (y in minY-1 until maxY+2) {
        for (x in minX -1 until maxX+2) {
          for (w in minW-1 until maxW+2) {
            val thisCube = activeCubes.find {it.x.equals(x) && it.y.equals(y) && it.z.equals(z) && it.w.equals(w)}
            val activeNeighbourCount = activeCubes
              //find all neighbours
              .filter {IntRange(x-1, x+1).contains(it.x) && IntRange(y-1, y+1).contains(it.y) && IntRange(z-1, z+1).contains(it.z) && IntRange(w-1, w+1).contains(it.w)}
              .filterNot {it.x.equals(x) && it.y.equals(y) && it.z.equals(z) && it.w.equals(w)}
              .size
            if ((thisCube != null && IntRange(2, 3).contains(activeNeighbourCount)) || (thisCube == null && activeNeighbourCount == 3)) {
              activeCubesNextCycle.add(ActiveCube(x, y, z, w))
            }
          }

        }
      }
    }
    activeCubes = activeCubesNextCycle
    println("activeCubes: " + activeCubes)
    println("cycle $i: number of active cubes: " + activeCubes.size)
  }
}