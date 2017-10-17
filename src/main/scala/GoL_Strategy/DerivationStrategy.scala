package GoL_Strategy


/**
  * Created by RafaelSouza on 04/10/2017.
  */


trait DerivationStrategy {
  /* verifica se uma celula deve ser mantida viva */
  def shouldKeepAlive(line: Int, column: Int): Boolean

  /* verifica se uma celula deve (re)nascer */
  def shouldRevive(line: Int, column: Int): Boolean

}
