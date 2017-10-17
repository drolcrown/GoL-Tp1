package GoL_Original

/**
  */
trait DerivationStrategy {
  /* verifica se uma celula deve ser mantida viva */
  def shouldKeepAlive(line: Int, column: Int): Boolean

  /* verifica se uma celula deve (re)nascer */
  def shouldRevive(line: Int, column: Int): Boolean

}
