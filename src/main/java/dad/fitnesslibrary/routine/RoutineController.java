package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.ExerciseTime;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class RoutineController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private Button beforeExerciseButton;
	
	@FXML
    private Button deleteExerciseButton;

	@FXML
	private ListView<ExerciseTime> ejerciciosRoutineListView;

	@FXML
	private ImageView exerciseImageView;

	@FXML
	private Button afterExerciseButton;

	@FXML
	private TextField nameRoutineTextField;

	@FXML
    private Label exerciseLabel;

    @FXML
    private TextField minutosTextField;
    
    @FXML
    private TextField segundosTextField;

	@FXML
	private GridPane root;

	@FXML
	private Button startPauseButton;
	
	private RoutineModel model;

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
		model = new RoutineModel();
		
		ejerciciosRoutineListView.setCellFactory(param -> new ListCell<ExerciseTime>() {
			@Override
		    protected void updateItem(ExerciseTime item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		model.exerciseSelectedProperty().bind(ejerciciosRoutineListView.getSelectionModel().selectedItemProperty());
		
		BooleanBinding disableBefore = Bindings.when(ejerciciosRoutineListView.getSelectionModel().selectedIndexProperty().isEqualTo(0)).then(true).otherwise(false);
		beforeExerciseButton.disableProperty().bind(disableBefore);
		
		BooleanBinding disableAfter = Bindings.when(ejerciciosRoutineListView.getSelectionModel().selectedIndexProperty().isEqualTo(ejerciciosRoutineListView.getItems().size()-1)).then(true).otherwise(false);
		afterExerciseButton.disableProperty().bind(disableAfter);
		
		model.exerciseSelectedProperty().addListener((obv, ov, nv) -> {
			if (ov != nv && nv != null) {
//				if (Objects.nonNull(nv.getGifUrl())) {
//					model.setImage(new Image(getClass().getResourceAsStream(nv.getGifUrl())));
//				}
				model.setNombre(nv.getName());
				model.setMinutos(nv.getMinutos());
				model.setSegundos(nv.getSegundos());
			}
		});
		
//		exerciseImageView.imageProperty().bindBidirectional(model.imageProperty());
		minutosTextField.textProperty().bindBidirectional(model.minutosProperty(), new NumberStringConverter());
		segundosTextField.textProperty().bindBidirectional(model.segundosProperty(), new NumberStringConverter());
		exerciseLabel.textProperty().bindBidirectional(model.nombreProperty());
	}
	
	@FXML
    void onDeleteExerciseAction(ActionEvent event) {
		ExerciseTime exerciseToDelete = ejerciciosRoutineListView.getSelectionModel().getSelectedItem();
		ejerciciosRoutineListView.getItems().remove(exerciseToDelete);
    }

	public Button getBeforeExerciseButton() {
		return beforeExerciseButton;
	}

	public ImageView getExerciseImageView() {
		return exerciseImageView;
	}

	public Button getNextExerciseButton() {
		return afterExerciseButton;
	}

	public TextField getNameRoutineTextField() {
		return nameRoutineTextField;
	}

	public ListView<ExerciseTime> getEjerciciosRoutineListView() {
		return ejerciciosRoutineListView;
	}

	public Button getBackButton() {
		return backButton;
	}
	
	public RoutineModel getModel() {
		return model;
	}

	public GridPane getRoot() {
		return root;
	}
}
