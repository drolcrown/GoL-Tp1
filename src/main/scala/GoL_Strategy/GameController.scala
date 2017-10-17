package GoL_Strategy

import GoL_Stategy.View

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {
  
  def start {
    CellsCaretaker.persist
    //TODO
    GameView.update
  }
  
  def halt() {
    //oops, nao muito legal fazer sysout na classe Controller
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

  def makeCellAlive(line: Int, column: Int) {
    try {
			GameEngine.makeCellAlive(line, column)
      //TODO
			GameView.update
		}
		catch {
		  case ex: IllegalArgumentException => {
		    println(ex.getMessage)
		  }
		}
  }
  
  def nextGeneration {
    CellsCaretaker.persist
    GameEngine.nextGeneration
    View.updateChart
  }

  def goBack: Unit = {
    CellsCaretaker.undo
    View.updateChart
  }

  def goFoward: Unit ={
    CellsCaretaker.redo
    View.updateChart
  }
  
}