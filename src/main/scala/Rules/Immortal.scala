package Rules

import GoL_Strategy.{DerivationStrategy, CellsRepository, GameEngine}

/**
  * Created by RafaelSouza on 04/10/2017.
  */

object Immortal extends DerivationStrategy {
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 0)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) >= 1)
  }

}