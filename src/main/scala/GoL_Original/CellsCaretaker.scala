package GoL_Original

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

  def undo: Unit = {
    if (validMemento(current - 1)) {
      current -= 1
      CellsRepository.restore(cellsMementos(current))
    } else {
      CellsRepository.restore(cellsMementos(0))
    }
  }

  def redo: Unit = {
    if (validMemento(current + 1)) {
      current += 1
      CellsRepository.restore(cellsMementos(current))
    } else {
      CellsRepository.restore(cellsMementos.last)
    }
  }

  private def validMemento(position: Int): Boolean = {
    position >= 0 && position < cellsMementos.size
  }

  def clear: Unit = {
    current = -1
    cellsMementos.clear
  }
}