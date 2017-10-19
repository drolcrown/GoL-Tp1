package GoL_Strategy

import scala.collection.mutable.ListBuffer

/**
  * Created by RafaelSouza on 04/10/2017.
  *
  *
  (1) Salvando o estado interno do originador: o Caretaker instancia uma lista de celulas memento
      e salva seu estado interno atual.
      Persist

  (2) Restaurando o estado interno do originador: Caretaker instancia uma lista de celulas memento
      e especifica o objeto memento que armazena o estado que deve ser restaurado.

  (3) Verifica se uma celula memento esta entre um valor valido.
*/

object Caretaker {
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