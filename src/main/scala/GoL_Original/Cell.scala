package GoL_Original

/**
 * Rerepsentacao de uma celula do GoL 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
class Cell {
  
  private var alive = false

  /**
    * Setter for att .alive
    * @return (boolean) alive
    */
  def isAlive = alive

  def kill = alive = false
  def revive = alive = true
}