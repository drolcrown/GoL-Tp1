package GoL_Original
import scala.collection.mutable.ListBuffer

/**
  * Contract for implementation of a Keeper of Mementos
  */
trait Caretaker[Memento] {
  val mementosRepository: ListBuffer[Memento]
  protected var current: Int = -1
  def persist: Unit
  def undo: Unit
  def redo: Unit
  def clear: Unit
}
