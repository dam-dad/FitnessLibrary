package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class RoutineController implements Initializable {

	@FXML
    private Button backButton;
	
	@FXML
    private Button beforeExerciseButton;

    @FXML
    private ListView<Exercise> ejerciciosRoutineListView;

    @FXML
    private ImageView exerciseImageView;

    @FXML
    private Button nextExerciseButton;

    @FXML
    private Label repsTimerLabel;

    @FXML
    private GridPane root;

    @FXML
    private Button startPauseButton;
	
	public RoutineController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RutinaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public Button getBackButton() {
		return backButton;
	}

	public GridPane getRoot() {
		return root;
	}
}
