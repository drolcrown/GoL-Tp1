package GoL_Original

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {

  def start {
    clear
    CellsCaretaker.persist
    View.updateChart
  }

  def repManager(line: Int, column: Int, veredict: Boolean ): Unit = {
    if (veredict) {
      CellsRepository(line, column).revive
    } else {
      CellsRepository(line, column).kill
    }
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
			View.updateChart
		}
		catch {
		  case ex: IllegalArgumentException => {
		    println(ex.getMessage)
		  }
		}
  }

  def makeCellDead(line: Int, column: Int) {
    try {
      GameEngine.makeCellDead(line, column)
      View.updateChart
    }
    catch {
      case ex: IllegalArgumentException => {
        println(ex.getMessage)
      }
    }
  }

  def checkCellAlive(line: Int, column: Int): Boolean = {
    GameEngine.isCellAlive(line, column)
  }

  def nextGeneration {
    GameEngine.nextGeneration
    CellsCaretaker.persist
    View.updateChart
  }

  def goBack: Unit = {
    CellsCaretaker.undo
    View.updateChart
  }

  def goFoward: Unit = {
    CellsCaretaker.redo
    View.updateChart
  }

  def clear: Unit = {
    CellsCaretaker.clear
    CellsRepository.clear
    View.updateChart
  }

  def memory: Unit ={
    CellsCaretaker.persist
  }


}