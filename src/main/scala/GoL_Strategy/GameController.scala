package GoL_Strategy

import GoL_Stategy.View

import scalafx.scene.paint.Color

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {
  
  def start {
    Caretaker.persist
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

  def nextGeneration(color : Color) {
    Caretaker.persist
    GameEngine.nextGeneration
    View.updateChart(color)
  }

  def goBack(color: Color): Unit = {
    Caretaker.undo
    View.updateChart(color)
  }

  def goFoward(color: Color): Unit ={
    Caretaker.redo
    View.updateChart(color)
  }
  
}