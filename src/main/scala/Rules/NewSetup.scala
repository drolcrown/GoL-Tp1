package Rules
import GoL_Original.{DerivationStrategy, CellsRepository, GameEngine}

/**
  * Created by RafaelSouza on 09/10/2017.
  */

class NewSetup(indx_alive: Int = 2,indx_die: Int = 3, indx_revive: Int = 4) extends DerivationStrategy {
  /**override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= indx_alive)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= indx_revive)
  }**/

  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == indx_alive || GameEngine.numberOfNeighborhoodAliveCells(line, column) == indx_die)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == indx_revive)
  }
}
