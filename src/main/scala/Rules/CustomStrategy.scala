package Rules
import GoL_Original.{DerivationStrategy, CellsRepository, GameEngine}


/**
  * Created by RafaelSouza on 04/10/2017.
  */

object CustomStrategy extends DerivationStrategy {
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 1)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 4)
  }

}
