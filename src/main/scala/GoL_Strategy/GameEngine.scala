package GoL_Strategy

import java.security.InvalidParameterException

import scala.collection.mutable.ListBuffer

/**
 * Representa a Game Engine do GoL 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameEngine {
  
  val height = Main.height
  val width = Main.width

  var rule = OriginalStrategy

  /**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
	 * uma celula viva.
	 * 
	 * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
	 * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
  
  def nextGeneration {
    
    val mustRevive = new ListBuffer[Cell]
    val mustKill = new ListBuffer[Cell]

    
    for(line <- (0 until height)) {
      for(column <- (0 until width)) {
        if(rule.shouldRevive(line, column)) {
          mustRevive += CellsRepository(line,column)
        }
        else if((!rule.shouldKeepAlive(line, column)) && CellsRepository(line,column).isAlive) {
          mustKill += CellsRepository(line,column)
        }
      }
    }
    
    
    for(cell <- mustRevive) {
      cell.revive
      Statistics.recordRevive
    }
    
    for(cell <- mustKill) {
      cell.kill
      Statistics.recordKill
    }
  }

  /*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
  private def validPosition(line: Int, column: Int) = true

  /**
	 * Torna a celula de posicao (i, j) viva
	 * 
	 * @param line posicao vertical da celula
	 * @param column posicao horizontal da celula
	 * 
	 * @throws InvalidParameterException caso a posicao (i, j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def makeCellAlive(line: Int, column: Int) = {
    if(validPosition(line, column)){
      CellsRepository(line,column).revive
      Statistics.recordRevive
    } else {
      throw new IllegalArgumentException
    }
  }
  
  /**
	 * Verifica se uma celula na posicao (line, column) estah viva.
	 * 
	 * @param line Posicao vertical da celula
	 * @param column Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (line,column) esteja viva.
	 * 
	 * @throws InvalidParameterException caso a posicao (i,j) nao seja valida. 
	 */
  @throws(classOf[IllegalArgumentException])
  def isCellAlive(line: Int, column: Int): Boolean = {
    if(validPosition(line, column)) {
      CellsRepository(line,column).isAlive
    } else {
      throw new IllegalArgumentException
    }
  }
  
  
  /**
	 * Retorna o numero de celulas vivas no ambiente. 
	 * Esse metodo eh particularmente util para o calculo de 
	 * estatisticas e para melhorar a testabilidade.
	 * 
	 * @return  numero de celulas vivas.
	 */
  def numberOfAliveCells : Int = {
    var aliveCells = 0
    for(line <- (0 until height)) {
      for(column <- (0 until width)) {
        if(isCellAlive(line, column)) aliveCells += 1
      }
    }
    return aliveCells
  }
  
  /*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 */
  def numberOfNeighborhoodAliveCells(line: Int, column: Int): Int = {
    var alive = 0
    for(adj_line <- (line - 1 to line + 1)) {
      for(adj_column <- (column - 1 to column + 1)) {
        if (validPosition(adj_line, adj_column)  &&
              (!(adj_line==line && adj_column == column)) &&
               CellsRepository(adj_line,adj_column).isAlive) {
					alive += 1
				}
      }
    }
    alive
  }

}