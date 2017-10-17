package GoL_Strategy

/**
  * Created by RafaelSouza on 04/10/2017.
  */

class CellsMemento(height: Int, width: Int, cells: Array[Array[Cell]]) {
  val collection = Array.ofDim[Cell](height, width)

  for(line <- (0 until height)) {
    for(column <- (0 until width)) {
      collection(line)(column) = new Cell
      if (cells(line)(column). isAlive ) { collection(line)(column).revive }
    }
  }

}
