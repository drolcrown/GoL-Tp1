package GoL_Stategy

import javafx.scene.text.{Font, Text}

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
import scalafx.geometry.Pos
import scalafx.scene.layout.GridPane
import scalafx.scene.text.FontWeight

/**
  * Created by RafaelSouza on 14/10/2017.
  */

object View extends JFXApp {
  val height = Main.height
  val width = Main.width

  val cell = Array.ofDim[Rectangle](View.height, View.width)

  def updateChart: Unit = {
    for (i <- 0 until View.height) {
      for (j <- 0 until View.width) {
        cell(i)(j).fill = if (CellsRepository(i, j).isAlive) Color.Blue else Color.Gray
      }
    }
  }

  startChart()
  def startChart(): Unit = {
    stage = new JFXApp.PrimaryStage {
      /** anonymus interclass **/
      title = "Game of Life"
      scene = new Scene(650, 570) {
        //Menus
        val menuBar = new MenuBar

        val fileMenu = new Menu("File")
        val newIt = new MenuItem("New")
        /** Partially Implemented **/
        val openIt = new MenuItem("Open")
        /** To Implement **/
        val exitIt = new MenuItem("Exit") /** Implemented **/
        fileMenu.items = List(newIt, exitIt)

        val ruleMenu = new Menu("Rules")
        val stratIt = new MenuItem("Strategy")
        /** To Implement **/
        val templIt = new MenuItem("Template") /** To Implement **/
        ruleMenu.items = List(stratIt)

        menuBar.menus = List(fileMenu, ruleMenu)
        menuBar.prefWidth = 650

        newIt.onAction = (event: ActionEvent) => startHomeChart(true)

        exitIt.onAction = (event: ActionEvent) => sys.exit(0)

        stratIt.onAction = (event: ActionEvent) => startStrategy(true)

        val manualB = new RadioButton("Manual")
        manualB.layoutX = 10
        manualB.layoutY = 380

        manualB.onAction = (event: ActionEvent) => {
          CellsRepository.clear
          //TODO
          startHomeChart(true)
        }

        val autoB = new RadioButton("Automatic")
        autoB.layoutX = 10
        autoB.layoutY = 400

        autoB.onAction = (event: ActionEvent) => startHomeChart(false)
        /** Update later to calling a makeCells method **/

        val toggle = new ToggleGroup
        toggle.toggles = List(manualB, autoB)

        //Buttons
        def mainText(): Button = {
          val titleB = new Button("Game of Life")
          titleB.layoutX = 120
          titleB.layoutY = 150
          titleB.setFont(Font.font("Tahoma", FontWeight.Black, 50))

          return titleB
        }

        val playB = new Button("PLAY")
        playB.layoutX = 10
        playB.layoutY = 440

        playB.onAction = (event: ActionEvent) => GameController.nextGeneration

        val haltB = new Button("HALT")
        haltB.layoutX = 60
        haltB.layoutY = 440

        haltB.onAction = (event: ActionEvent) => sys.exit(0)

        val undoB = new Button("UNDO")
        undoB.layoutX = 5
        undoB.layoutY = 470

        undoB.onAction = (event: ActionEvent) => GameController.goBack

        val redoB = new Button("REDO")
        redoB.layoutX = 60
        redoB.layoutY = 470
        redoB.onAction = (event: ActionEvent) => GameController.goFoward

        val infB = new Button("AUTOGENERATE")
        infB.layoutX = 0
        infB.layoutY = 500
        infB.setFont(Font.font("Tahoma", FontWeight.Black, 20))
        infB.onAction = (event: ActionEvent) => while(GameEngine.numberOfAliveCells > 0){GameController.nextGeneration}

        content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB, mainText())

        def startStrategy(mode: Boolean) {
          stage = new JFXApp.PrimaryStage {
            title.value = "Game of Life"
            scene = new Scene(650, 570) {
              val titleB = new ToggleButton("Strategies")
              titleB.layoutX = 120
              titleB.layoutY = 100
              titleB.setFont(Font.font("Tahoma", FontWeight.BLACK, 50))

              val conwayB = new RadioButton("Conway")
              conwayB.layoutX = 250
              conwayB.layoutY = 250
              conwayB.onAction = (event: ActionEvent) => View.startChart()

              val highLifeB = new RadioButton("HighLife")
              highLifeB.layoutX = 250
              highLifeB.layoutY = 300
              highLifeB.onAction = (event: ActionEvent) => View.startChart()

              val immortalB = new RadioButton("Immortal")
              immortalB.layoutX = 250
              immortalB.layoutY = 350
              immortalB.onAction = (event: ActionEvent) => View.startChart()

              val newSetupB = new RadioButton("New Strategy")
              newSetupB.layoutX = 250
              newSetupB.layoutY = 400
              newSetupB.onAction = (event: ActionEvent) => View.startChart()

              content = List(menuBar, titleB, newSetupB, immortalB, highLifeB, conwayB)
            }
          }
        }

        def startHomeChart(mode: Boolean) {
          content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB, infB)
          for (i <- 0 until View.height) {
            for (j <- 0 until View.width) {
              cell(i)(j) = Rectangle(120 + (30 * i), 40 + (30 * j), 29, 29)
              cell(i)(j).fill = Color.Gray

              if (mode) {
                cell(i)(j).onMouseClicked = (event: MouseEvent) => {
                  if (CellsRepository(i, j).isAlive) {
                    //TODO
                    CellsRepository(i, j).kill
                    cell(i)(j).fill = Color.Gray
                  } else {
                    CellsRepository(i, j).revive
                    cell(i)(j).fill = Color.Blue
                  }
                }
              } else {
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
                      CellsRepository(l, c).kill
                      //TODO
                      cell(l)(c).fill = Color.Gray
                    } else {
                      CellsRepository(l, c).revive
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
  }
}