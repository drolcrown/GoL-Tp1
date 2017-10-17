package GoL_Strategy

import scala.io.StdIn.{readInt, readLine}

/**
 * Representa o componente View do GoL
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameView {
  
	private final val LINE = "+-----+"
	private final val DEAD_CELL = "|     |"
	private final val ALIVE_CELL = "|  o  |"
	
	private final val INVALID_OPTION = 0
	private final val MAKE_CELL_ALIVE = 1
	private final val NEXT_GENERATION = 2
	private final val HALT = 3

  
  /**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualiza��o do jogo.
	 */
	def update {
		printFirstRow
		printLine
		
		for(line <- (0 until GameEngine.height)) {
		  for(column <- (0 until GameEngine.width)) {
		    print(if (GameEngine.isCellAlive(line, column))  ALIVE_CELL else DEAD_CELL);
		  }
			/**
				* TODO check better place to put the following print. It prints the line of the game
				*/
		  println("   " + line)
		  printLine
		}
		printOptions
	}
  
  private def printOptions {
	  
	  var option = 0
	  println("\n\n")
	  
	  do {
	    println("Select one of the options: \n \n"); 
			println("[1] Make a cell alive");
			println("[2] Next generation");
			println("[3] Halt");
		
			print("\n \n Option: ");
			
			option = parseOption(readLine)
	  } while(option == 0)
	  
	  option match {
      case MAKE_CELL_ALIVE => makeCellAlive
      case NEXT_GENERATION => nextGeneration
      case HALT => halt
    }
	}
  
  private def makeCellAlive {
	  
	  var line = 0
	  var column = 0
	  
	  do {
      print("\n Inform the row number (0 - " + (GameEngine.height - 1) + "): ")
      line = readInt
      
      print("\n Inform the column number (0 - " + (GameEngine.width - 1) + "): ")
      column= readInt
      
    } while(!validPosition(line, column))
      
    GameController.makeCellAlive(line, column)
	}

  private def nextGeneration = GameController.nextGeneration
  private def halt = GameController.halt
	
  private def validPosition(line: Int, column: Int): Boolean = {
		println(line);
		println(column);
		line >= 0 && line < GameEngine.height && column >= 0 && column < GameEngine.width
	}
  
	def parseOption(option: String): Int = option match {
    case "1" => MAKE_CELL_ALIVE
    case "2" => NEXT_GENERATION
    case "3" => HALT
    case _ => INVALID_OPTION
  }
	
  
  /* Imprime uma linha usada como separador das linhas do tabuleiro */
	private def printLine() {
	  for(column <- (0 until GameEngine.width)) {
	    print(LINE)
	  }
	  println()
	}
  
  /*
	 * Imprime os identificadores das colunas na primeira linha do tabuleiro
	 */
	private def printFirstRow {
		println("\n \n");
		
		for(column <- (0 until GameEngine.width)) {
		  print("   " + column + "   ")
		}
		println()
	}
  
}