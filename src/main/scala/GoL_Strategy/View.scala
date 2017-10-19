package GoL_Stategy

import java.util.{Timer, TimerTask}
import javafx.scene.text.{Font, Text}

import GoL_Strategy.{CellsRepository, GameController, GameEngine, Main}
import Rules.{HighLife, Immortal, NewSetup, OriginalStrategy}

import scala.util.Random
import scalafx.application.JFXApp
import scalafx.scene.{Node, Scene}
import scalafx.scene.control._
import scalafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.layout.HBox
import scalafx.scene.text.FontWeight

/**
  * Created by RafaelSouza on 14/10/2017.
  */

object View extends JFXApp {
  val height = Main.height
  val width = Main.width

  val cell = Array.ofDim[Rectangle](View.height, View.width)

  startHome()

  def updateChart(color : Color): Unit = {
    for (i <- 0 until View.height) {
      for (j <- 0 until View.width) {
        cell(i)(j).fill = if (CellsRepository(i, j).isAlive) color else Color.Gray
      }
    }
  }

  //TODO: Implementar regra direito
  def newRule() {
    stage = new JFXApp.PrimaryStage {
      title.value = "Game of Life"
      scene = new Scene(650, 570) {

        var label = new Button("Number of Neigborhs alive")
        label.setFont(Font.font("Tahoma", FontWeight.BLACK, 30))
        label.layoutX = 80
        label.layoutY = 50

        var label2 = new Button("Submit")
        label2.layoutX = 250
        label2.layoutY = 300

        var textField = new TextField ()
        textField.layoutX = 200
        textField.layoutY = 150

        var textField2 = new TextField ()
        textField2.layoutX = 200
        textField2.layoutY = 200

        var textField3 = new TextField ()
        textField3.layoutX = 200
        textField3.layoutY = 250

        label2.onAction = (event: ActionEvent) =>  {
          var val3 = textField3.getText()
          var val2 = textField2.getText()
          var val1 = textField.getText()
          startChart()
          GameEngine.rule =  new NewSetup(val1.toInt, val2.toInt, val3.toInt)
        }

        content = List(label, label2, textField, textField2, textField3)
      }
    }
  }


  def startHome() {
    stage = new JFXApp.PrimaryStage {
      title.value = "Game of Life"
      scene = new Scene(650, 570) {
        val titleB = new Button("Game of Life")
        titleB.layoutX = 120
        titleB.layoutY = 100
        titleB.setFont(Font.font("Tahoma", FontWeight.BLACK, 50))

        val startB = new Button("Start")
        startB.layoutX = 250
        startB.layoutY = 250
        startB.setFont(Font.font("Tahoma", FontWeight.BLACK, 30))
        startB.onAction = (event: ActionEvent) => View.startChart()

        val exitB = new Button("Exit")
        exitB.layoutX = 260
        exitB.layoutY = 320
        exitB.setFont(Font.font("Tahoma", FontWeight.BLACK, 30))
        exitB.onAction = (event: ActionEvent) => sys.exit(0)

        GameEngine.rule = OriginalStrategy
        content = List(titleB, startB, exitB)
      }
    }
  }

  def colorRandom() : Color = {
    var rand = new Random()
    return rand.nextInt(5) match {
      case 1 => Color.Red
      case 2 => Color.Aquamarine
      case 3 => Color.Green
      case 4 => Color.DarkOrange
      case 0 => Color.Gold
    }
  }

  def startChart(): Unit = {
    var newColor = colorRandom()
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

        exitIt.onAction = (event: ActionEvent) => View.startHome()

        stratIt.onAction = (event: ActionEvent) => startStrategy(true)

        val manualB = new RadioButton("Manual")
        manualB.layoutX = 10
        manualB.layoutY = 380
        manualB.onAction = (event: ActionEvent) => {CellsRepository.clear; startHomeChart(true)}

        val autoB = new RadioButton("Automatic")
        autoB.layoutX = 10
        autoB.layoutY = 400
        autoB.onAction = (event: ActionEvent) => {CellsRepository.clear; startHomeChart(false)}
        /** Update later to calling a makeCells method **/

        val toggle = new ToggleGroup
        toggle.toggles = List(manualB, autoB)

        //Buttons
        val playB = new Button("PLAY")
        playB.layoutX = 10
        playB.layoutY = 440
        playB.setFont(Font.font("Tahoma", FontWeight.Black, 12))

        playB.onAction = (event: ActionEvent) => GameController.nextGeneration(newColor)

        val haltB = new Button("HALT")
        haltB.layoutX = 65
        haltB.layoutY = 440
        haltB.setFont(Font.font("Tahoma", FontWeight.Black, 12))

        haltB.onAction = (event: ActionEvent) => View.startHome()

        val undoB = new Button("UNDO")
        undoB.layoutX = 5
        undoB.layoutY = 470
        undoB.setFont(Font.font("Tahoma", FontWeight.Black, 12))

        undoB.onAction = (event: ActionEvent) => GameController.goBack(newColor)

        val redoB = new Button("REDO")
        redoB.layoutX = 65
        redoB.layoutY = 470
        redoB.setFont(Font.font("Tahoma", FontWeight.Black, 12))
        redoB.onAction = (event: ActionEvent) => GameController.goFoward(newColor)

        val infB = new Button("AUTOGENERATE")
        infB.layoutX = 7
        infB.layoutY = 500
        infB.setFont(Font.font("Tahoma", FontWeight.Black, 10))
        infB.onAction = (event: ActionEvent) => autoGenerate()

        val stopB = new Button("STOP")
        stopB.layoutX = 23
        stopB.layoutY = 250
        stopB.setFont(Font.font("Tahoma", FontWeight.Black, 15))

        val pulsB = new Button("PULSAR")
        pulsB.layoutX = 20
        pulsB.layoutY = 240
        pulsB.setFont(Font.font("Tahoma", FontWeight.Black, 10))
        pulsB.onAction = (event: ActionEvent) => {updateChart(newColor)}

        val glidB = new Button("GLIDER")
        glidB.layoutX = 65
        glidB.layoutY = 340
        glidB.setFont(Font.font("Tahoma", FontWeight.Black, 10))
        glidB.onAction = (event: ActionEvent) => autoGenerate()

        def autoGenerate() {
          title.value = "Game of Life"
          var timer = new Timer();
          //Criando um timer para o generate
          timer.schedule(new TimerTask() {
            def run() {
              GameController.nextGeneration(newColor)
            }
          }, 0, 800)
          content = List(menuBar, stopB)
          for (i <- 0 until View.height) {
            for (j <- 0 until View.width) {
              cell(i)(j) = Rectangle(120 + (30 * i), 40 + (30 * j), 29, 29)
              cell(i)(j).fill = Color.Gray
              content += cell(i)(j)
            }
          }
          stopB.onAction = (event: ActionEvent) => {timer.cancel(); View.startChart()}
        }

        content = List(menuBar, playB, haltB, undoB, redoB, manualB, autoB, infB, pulsB)

        //Matrix
        for (i <- 0 until View.height) {
          for (j <- 0 until View.width) {
            cell(i)(j) = Rectangle(120 + (30 * i), 40 + (30 * j), 29, 29)
            cell(i)(j).fill = Color.Gray
            content += cell(i)(j)
          }
        }

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
              conwayB.onAction = (event: ActionEvent) => {View.startChart(); GameEngine.rule = OriginalStrategy}

              val highLifeB = new RadioButton("HighLife")
              highLifeB.layoutX = 250
              highLifeB.layoutY = 300
              highLifeB.onAction = (event: ActionEvent) => {View.startChart(); GameEngine.rule = HighLife}

              val immortalB = new RadioButton("Immortal")
              immortalB.layoutX = 250
              immortalB.layoutY = 350
              immortalB.onAction = (event: ActionEvent) => {View.startChart(); GameEngine.rule = Immortal}

              val newSetupB = new RadioButton("New Strategy")
              newSetupB.layoutX = 250
              newSetupB.layoutY = 400
              newSetupB.onAction = (event: ActionEvent) => {newRule()}
              //newRule(ev1, ev2, ev3, ev4)

              manualB.onAction = (event: ActionEvent) => {CellsRepository.clear; startHomeChart(true)}
              newIt.onAction = (event: ActionEvent) => View.startChart()
              content = List(menuBar, titleB, newSetupB, immortalB, highLifeB, conwayB)
            }
          }
        }

        def startHomeChart(mode: Boolean) {
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