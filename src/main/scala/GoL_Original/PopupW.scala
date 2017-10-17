package GoL_Original

import Rules.NewSetup

import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.GridPane

/**
  * Created by RafaelSouza on 04/10/2017.
  */


class PopupW {

  case class Result(letslive: String, kills: String, revives: String)

  val dialog = new Dialog[Result] {
    initOwner(View.stage)
    title = "Your rule"
    headerText = "Number of Neighbours to:"
    contentText = "Game of Life Rule"
  }

  val finishButtonType = new ButtonType("Finish", ButtonData.OKDone)
  dialog.dialogPane().getButtonTypes.addAll(finishButtonType, ButtonType.Cancel)

  val minLive = new TextField(){promptText = "Minimum"}
  val maxLive = new TextField(){promptText = "Maximum"}
  val toRevive = new TextField(){promptText = "Revive"}

  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(30, 100, 10, 10)

    add(new Label("Minimum to Stay Alive:"), 0, 0)
    add(minLive, 1, 0)
    add(new Label("Maximum to Stay Alive:"), 0, 1)
    add(maxLive, 1, 1)
    add(new Label("Revive:"), 0, 2)
    add(toRevive, 1, 2)
  }

  dialog.dialogPane().setContent(grid)

  Platform.runLater(minLive.requestFocus())

  dialog.resultConverter = dialogButton =>
    if (dialogButton == finishButtonType) Result(minLive.text(), maxLive.text(), toRevive.text())
    else null

  val result = dialog.showAndWait()

  result match  {
    case Some(Result(min, max, revive))       => {
      println("Minimum: " + min + " Maximum: " + max + " Revive: " + revive)
      GameEngine.rule = new NewSetup(min.toInt, max.toInt, revive.toInt)
    }
    case _                                => println("Cancel or closed")
  }

}
