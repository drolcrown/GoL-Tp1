package GoL_Original

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Created by RafaelSouza on 04/10/2017.
  */

class PopupError (path: String) {

  new Alert(AlertType.Error) {
    initOwner(View.stage)
    title = "Error"
    headerText = "No such file or directory found."
    contentText = "Exception in: " + path
  }.showAndWait()
}
