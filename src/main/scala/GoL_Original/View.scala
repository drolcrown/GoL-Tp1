package GoL_Original

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.Includes._
import scalafx.event.ActionEvent
import java.util.Timer

import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.GridPane
import scalafx.stage.{Popup, PopupWindow}

/**
  * Created by RafaelSouza on 04/10/2017.
  */

object View extends JFXApp {

  val height = Main.height
  val width = Main.width

  // Define rectangles that will change color accordinlgy to the state of the cell
  val cell = Array.ofDim[Rectangle](View.height, View.width)

  var aliveColor = Color.Blue

  /**
    * Uppdate view accordingly to information in CellsRepository
    */
  def updateChart: Unit = {
    for(line <- 0 until View.height) {
      for(column <- 0 until View.width) {
        cell(line)(column).fill = if (GameController.checkCellAlive(line, column)) aliveColor else Color.Gray
      }
    }
  }


  def initializeCells(line: Int, column: Int): Rectangle = {
    cell(line)(column) = Rectangle(120 + (30 * line), 40 + (30 * column), 29, 29)
    cell(line)(column).fill = Color.Gray
    // Make button clickable
    cell(line)(column).onMouseClicked = (event: MouseEvent) => {
      //if (CellsRepository(line, column).isAlive) {
      if (GameController.checkCellAlive(line, column)) {
        //TODO
        GameController.repManager(line,column,true)
        //CellsRepository(line, column).kill
        cell(line)(column).fill = Color.Gray
      } else {
        GameController.repManager(line,column,true)
        //CellsRepository(line, column).revive
        cell(line)(column).fill = aliveColor
      }
      GameController.memory
      //CellsCaretaker.persist
    }
    cell(line)(column)
  }

  stage = new JFXApp.PrimaryStage {
    /** anonymus interclass **/
    title = "Game of Life"
    scene = new Scene(650, 570) {

      val timer = new java.util.Timer()
      var task = new java.util.TimerTask {
        def run() = GameController.nextGeneration
      }

      // Menus
      val menuBar = new MenuBar

      val fileMenu = new Menu("File")
      val newIt = new MenuItem("New")     /** TODO Partially Implemented **/
      //val openIt = new MenuItem("Open")
      val exitIt = new MenuItem("Exit")   /** Implemented **/
      fileMenu.items = List(newIt, exitIt)

      val ruleMenu = new Menu("Rules")
      val originIt = new MenuItem("Original")
      val immortIt = new MenuItem("Immortal")
      val highIt = new MenuItem("High Life")
      val importIt = new MenuItem("Import Rule from file")
      val setRule = new MenuItem("Set your own rule!")

      ruleMenu.items = List(originIt, highIt, immortIt, importIt, setRule)

      menuBar.menus = List(fileMenu, ruleMenu)
      menuBar.prefWidth = 650

      originIt.onAction = (event: ActionEvent) => {GameEngine.rule = OriginalStrategy}

      highIt.onAction = (event: ActionEvent) => {GameEngine.rule = HighLife}

      immortIt.onAction = (event: ActionEvent) => {GameEngine.rule = Immortal}

      //TODO
      importIt.onAction = (event: ActionEvent) => {val ppSc = new PopupSearch}

      setRule.onAction = (event: ActionEvent) => {val ppUp = new PopupW}

      newIt.onAction = (event: ActionEvent) => {
        startChart
        GameController.start
      }

      exitIt.onAction = (event: ActionEvent) => GameController.halt

      val blueB = new ToggleButton("Blue")
      blueB.layoutX = 10
      blueB.layoutY = 50
      blueB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Blue
        updateChart}

      val redB = new ToggleButton("Red")
      redB.layoutX = 10
      redB.layoutY = 80
      redB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Red
        updateChart}

      val greenB = new ToggleButton("Green")
      greenB.layoutX = 10
      greenB.layoutY = 110

      greenB.onAction = (e: ActionEvent) => {
        aliveColor = Color.Green
        updateChart}

      val colors = new ToggleGroup
      colors.toggles = List(blueB, redB, greenB)


      //val autoB = new RadioButton("Seed")
      val pulsarB = new Button("Pulsar")
      pulsarB.layoutX = 10
      pulsarB.layoutY = 170
      pulsarB.onAction = (event: ActionEvent) => {
        GameController.clear
        seed1
      }

      val gliderB = new Button("Glider")
      gliderB.layoutX = 10
      gliderB.layoutY = 200
      gliderB.onAction = (event: ActionEvent) => {
        GameController.clear
        seed2
      }

      //Buttons
      val nextB = new Button("Next Generation")
      nextB.layoutX = 5
      nextB.layoutY = 420
      nextB.onAction = (event: ActionEvent) => GameController.nextGeneration

      val playB = new Button("PLAY")
      playB.layoutX = 10
      playB.layoutY = 470
      playB.onAction = (event: ActionEvent) => {
        task = new java.util.TimerTask {
          def run() = GameController.nextGeneration
        }
        timer.schedule(task, 500 ,500)
      }

      val stopB = new Button("STOP")
      stopB.layoutX = 60
      stopB.layoutY = 470
      stopB.onAction = (event: ActionEvent) => {
        task.cancel()
      }

      val undoB = new Button("UNDO")
      undoB.layoutX = 5
      undoB.layoutY = 520

      undoB.onAction = (event: ActionEvent) => GameController.goBack

      val redoB = new Button("REDO")
      redoB.layoutX = 60
      redoB.layoutY = 520

      redoB.onAction = (event: ActionEvent) => GameController.goFoward

      content = List(menuBar, nextB, playB, stopB, undoB, redoB, pulsarB, gliderB, blueB, redB, greenB)

      def seed1 {
        var pSet = Array.ofDim[Int](View.height, View.width)
        //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
        pSet = Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
          Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))


        for (l <- 0 until View.height) {
          for (c <- 0 until View.width) {
            if (pSet(l)(c) == 0) {
              GameController.repManager(l,c,false)
              //CellsRepository(l, c).kill
              //TODO
              cell(l)(c).fill = Color.Gray
            } else {
              GameController.repManager(l,c,true)
              //CellsRepository(l, c).revive
              cell(l)(c).fill = aliveColor
            }
          }
        }
        GameController.memory
        //TODO
        //CellsCaretaker.persist
      }

      def seed2 {
        var pSet = Array.ofDim[Int](View.height, View.width)
        //  1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
        pSet = Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                     Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))

        for (l <- 0 until View.height) {
          for (c <- 0 until View.width) {
            if (pSet(l)(c) == 0) {
              GameController.repManager(l,c,false)
              //CellsRepository(l, c).kill
              //TODO
              cell(l)(c).fill = Color.Gray
            } else {
              GameController.repManager(l,c,true)
              //CellsRepository(l, c).revive
              cell(l)(c).fill = aliveColor
            }
          }
        }
        GameController.memory
        //TODO
        //CellsCaretaker.persist
      }

      def startChart {
        for (line <- 0 until View.height) {
          for (column <- 0 until View.width) {
            content += initializeCells(line, column)
          }
        }
      }
    }
  }
}


