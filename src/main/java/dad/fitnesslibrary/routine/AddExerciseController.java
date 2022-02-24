package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.Exercise;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * Controlador de la pantalla del ejercicio dentro de la rutina 
 *
 */
public class AddExerciseController implements Initializable {

	@FXML
    private Button CancelButton;

    @FXML
    private Button addExerciseButton;

    @FXML
    private Label exerciseLabel;

    @FXML
    private TextField minutosTextField;

    @FXML
    private TextField segundosTextField;
    
    @FXML
    private GridPane root;
    
    private Stage addExerciseStage;
	
	public AddExerciseController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddExerciseTimeView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addExerciseStage = new Stage();
		addExerciseStage.setTitle("Add Exercise");
		addExerciseStage.setScene(new Scene(root));
	}
	
	@FXML
    void onCancelButton(ActionEvent event) {
		addExerciseStage.close();
    }

	public Button getCancelButton() {
		return CancelButton;
	}

	public Button getAddExerciseButton() {
		return addExerciseButton;
	}

	public Label getExerciseLabel() {
		return exerciseLabel;
	}

	public TextField getMinutosTextField() {
		return minutosTextField;
	}

	public TextField getSegundosTextField() {
		return segundosTextField;
	}

	public Stage getAddExerciseStage() {
		return addExerciseStage;
	}
}
