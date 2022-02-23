package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.ExerciseTime;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class RoutineController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private Button beforeExerciseButton;

	@FXML
	private ListView<ExerciseTime> ejerciciosRoutineListView;

	@FXML
	private ImageView exerciseImageView;

	@FXML
	private Button nextExerciseButton;

	@FXML
	private TextField nameRoutineTextField;

	@FXML
	private Label repsTimerLabel;

	@FXML
	private GridPane root;

	@FXML
	private Button startPauseButton;

	public RoutineController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RutinaView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Button getBeforeExerciseButton() {
		return beforeExerciseButton;
	}

	public ImageView getExerciseImageView() {
		return exerciseImageView;
	}

	public Button getNextExerciseButton() {
		return nextExerciseButton;
	}

	public TextField getNameRoutineTextField() {
		return nameRoutineTextField;
	}

	public Label getRepsTimerLabel() {
		return repsTimerLabel;
	}

	public ListView<ExerciseTime> getEjerciciosRoutineListView() {
		return ejerciciosRoutineListView;
	}

	public Button getBackButton() {
		return backButton;
	}

	public GridPane getRoot() {
		return root;
	}
}
