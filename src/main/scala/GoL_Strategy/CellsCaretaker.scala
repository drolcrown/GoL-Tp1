package GoL_Strategy

import scala.collection.mutable.ListBuffer

/**
  * Created by RafaelSouza on 04/10/2017.
  */

object CellsCaretaker {
  val cellsMementos = new ListBuffer[CellsMemento]
  private var current = -1

  def persist: Unit = {
    cellsMementos += CellsRepository.save
    current += 1
  }

  //  @throws(classOf[IllegalArgumentException])
  def undo: Unit = {
    if (validMemento(current - 1)) {
      current -= 1
      CellsRepository.restore(cellsMementos(current))
    } else {
      CellsRepository.restore(cellsMementos(0))
      //      throw new IllegalArgumentException
    }
  }

  //  @throws(classOf[IllegalArgumentException])
  def redo: Unit = {
    if (validMemento(current + 1)) {
      current += 1
      CellsRepository.restore(cellsMementos(current))
    } else {
      //      throw new IllegalArgumentException
      CellsRepository.restore(cellsMementos.last)
    }
  }

  private def validMemento(position: Int): Boolean = {
    position >= 0 && position < cellsMementos.size
  }
}