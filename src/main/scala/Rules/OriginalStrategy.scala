package Rules

import GoL_Strategy.{CellsRepository, DerivationStrategy, GameEngine}

/**
  * Created by RafaelSouza on 04/10/2017.
  */

object OriginalStrategy extends DerivationStrategy {
  /* verifica se uma celula deve ser mantida viva */
  override def shouldKeepAlive(line: Int, column: Int): Boolean = {
    (CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == 2 || GameEngine.numberOfNeighborhoodAliveCells(line, column) == 3)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(line: Int, column: Int): Boolean = {
    (!CellsRepository(line,column).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(line, column) == 3)
  }

}
