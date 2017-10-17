package GoL_Original

/**
  * Created by RafaelSouza on 04/10/2017.
  */

object CellsRepository {

  val max_height = Main.height
  val max_width = Main.width
  val collection = Array.ofDim[Cell](max_height, max_width)

  for(line <- (0 until max_height)) {
    for(column <- (0 until max_width)) {
      collection(line)(column) = new Cell
    }
  }

  def apply(height: Int, width: Int): Cell = {
    val line = positiveModulo(height, max_height)
    val column = positiveModulo(width, max_width)
    collection(line)(column)
  }

  private def positiveModulo(num: Int, den: Int): Int = {
    val result = num % den
    if (result >= 0) { result } else { result + den }
  }

  def clear: Unit ={
    for(i <- 0 until max_height) {
      for (j <- 0 until max_width) {
        CellsRepository(i, j).kill
      }
    }
  }

  def save: CellsMemento = {
    new CellsMemento(max_height, max_width, collection)
  }

  def restore(memento: CellsMemento) = {
    for (line <- (0 until max_height)) {
      for (column <- (0 until max_width)) {
        if(memento.collection(line)(column).isAlive) {
          collection(line)(column).revive
        }else {
          collection(line)(column).kill
        }
      }
    }
  }


}
