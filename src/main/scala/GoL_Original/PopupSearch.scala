package GoL_Original

import Rules.NewSetup

import scala.io.Source
import scalafx.scene.control._

/**
  * Created by RafaelSouza on 04/10/2017.
  */

class PopupSearch {

  val dialog = new TextInputDialog(){
    initOwner(View.stage)
    title = "Open rule"
    headerText = "Enter file path."
    contentText = "Path: "
  }

  val result = dialog.showAndWait()
  //var fileContents: String

  result match{
    case Some(filePath) => {
      println("File path: " + filePath)
      try {
        val fileContents = Source.fromFile(filePath).getLines.mkString
        println(fileContents)
        GameEngine.rule = new NewSetup(fileContents(0).asDigit, fileContents(1).asDigit, fileContents(2).asDigit)
      } catch {
        case ex: Exception => {val ppError = new PopupError(filePath)}
      }
    }
    case _              => println("Canceled or Closed.")
  }

}
