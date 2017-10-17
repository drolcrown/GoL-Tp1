package GoL_Stategy

import GoL_Strategy.{CellsRepository, GameController, GameEngine, Main}

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.Includes._
import scalafx.event.ActionEvent

/**
  * Created by RafaelSouza on 14/10/2017.
  */

object View extends JFXApp {
  val height = Main.height
  val width = Main.width

  val cell = Array.ofDim[Rectangle](View.height, View.width)

  def updateChart: Unit ={
    for(i <- 0 until View.height){
      for(j <- 0 until View.width){
        cell(i)(j).fill = if(CellsRepository(i,j).isAlive) Color.Blue else Color.Gray
      }
    }
  }

  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(650, 570) {

      //Menus
      val menuBar = new MenuBar

      val fileMenu = new Menu("File")
      val newIt = new MenuItem("New")     /** Partially Implemented **/
      val openIt = new MenuItem("Open")   /** To Implement **/
      val exitIt = new MenuItem("Exit")   /** Implemented **/
      fileMenu.items = List(newIt, openIt, exitIt)

      val ruleMenu = new Menu("Rules")
      val stratIt = new MenuItem("Strategy")    /** To Implement **/
      val templIt = new MenuItem("Template")    /** To Implement **/
      ruleMenu.items = List(stratIt, templIt)

      menuBar.menus = List(fileMenu, ruleMenu)
      menuBar.prefWidth = 650

      newIt.onAction = (event: ActionEvent) => startChart(true)

      exitIt.onAction = (event: ActionEvent) => sys.exit(0)

      val manualB = new RadioButton("Manual")
      manualB.layoutX = 10
      manualB.layoutY = 400

      manualB.onAction = (event: ActionEvent) => {
        CellsRepository.clear
        //TODO
        startChart(true)}

      val autoB = new RadioButton("Automatic")
      autoB.layoutX = 10
      autoB.layoutY = 420

      autoB.onAction = (event: ActionEvent) => startChart(false)     /** Update later to calling a makeCells method **/

      val toggle = new ToggleGroup
      toggle.toggles = List(manualB, autoB)

      //Buttons
      val playB = new Button("PLAY")
      playB.layoutX = 10
      playB.layoutY = 470

      playB.onAction = (event: ActionEvent) => GameController.nextGeneration

      val haltB = new Button("HALT")
      haltB.layoutX = 60
      haltB.layoutY = 470

      val undoB = new Button("UNDO")
      undoB.layoutX = 5
      undoB.layoutY = 520

      undoB.onAction = (event: ActionEvent) => GameController.goBack

      val redoB = new Button("REDO")
      redoB.layoutX = 60
      redoB.layoutY = 520

      redoB.onAction = (event: ActionEvent) => GameController.goFoward

      content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB)

      def startChart(mode: Boolean) {
        for (i <- 0 until View.height) {
          for (j <- 0 until View.width) {
            cell(i)(j) = Rectangle(120 + (30 * i), 40 + (30 * j), 29, 29)
            cell(i)(j).fill = Color.Gray

            if (mode){
              cell(i)(j).onMouseClicked = (event: MouseEvent) => {
                if(CellsRepository(i,j).isAlive){
                  //TODO
                  CellsRepository(i,j).kill
                  cell(i)(j).fill = Color.Gray
                }else{
                  CellsRepository(i,j).revive
                  cell(i)(j).fill = Color.Blue
                }
              }
            }else{
              var pSet = Array.ofDim[Int](View.height, View.width)
              //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
              pSet = Array(Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
              Array(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0))

              for(l <- 0 until View.height){
                for(c <- 0 until View.width){
                  if(pSet(l)(c) == 0) {
                    CellsRepository(l,c).kill
                    //TODO
                    cell(l)(c).fill = Color.Gray
                  }else{
                    CellsRepository(l,c).revive
                    cell(l)(c).fill = Color.Blue
                  }
                }
              }
            }
            content += cell(i)(j)
          }
        }
      }
    }
  }
}/**
  * Created by Avell 1513 on 21/05/2017.
  */

