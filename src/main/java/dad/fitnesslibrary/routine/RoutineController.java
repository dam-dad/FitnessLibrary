package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import dad.fitnesslibrary.app.App;
import dad.fitnesslibrary.classes.ExerciseTime;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private Button startButton;
	
	@FXML
    private Button pauseButton;
	
	@FXML
    private Button stopButton;
    
    @FXML
	private GridPane root;
	
	private Timer countDownTimer;
	
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
		
		countDownTimer = new Timer();
		
		model.segundosProperty().addListener((obv, ov, nv) -> {
			if (nv.intValue() < 0) {
				model.setMinutos(model.getMinutos()-1);
				model.setSegundos(59);
			}
			if (nv.intValue() == 0 && model.getMinutos() == 0 ) {
				if (model.exercisesProperty().indexOf(model.getExerciseSelected()) != model.exercisesProperty().size()-1) {
					countDownTimer.cancel();
					ejerciciosRoutineListView.getSelectionModel().selectNext();
					countDownTimer = new Timer();
					countDownTimer.schedule(new TimerTask() {
				        @Override
				        public void run() {
				            Platform.runLater(() -> model.setSegundos(model.getSegundos()-1));
				        }
				    }, 0, 1000);
				}
				else {
					countDownTimer.cancel();
					countDownTimer.purge();
					ejerciciosRoutineListView.getSelectionModel().select(0);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Congratulations!");
					alert.setContentText("You have completed the routine");
					alert.showAndWait();
				}
			}
		});
		
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
		
		model.exerciseSelectedProperty().addListener((obv, ov, nv) -> {
			if (ov != nv && nv != null) {
				if (Objects.nonNull(nv.getGifUrl())) {
					exerciseImageView.setImage(new Image(nv.getGifUrl()));
				}
				model.setNombre(nv.getName());
				model.setMinutos(nv.getMinutos());
				model.setSegundos(nv.getSegundos());
			}
		});
		
//		exerciseImageView.imageProperty().bindBidirectional(model.imageProperty());
		minutosTextField.textProperty().bindBidirectional(model.minutosProperty(), new NumberStringConverter()); //, new NumberStringConverter()
		segundosTextField.textProperty().bindBidirectional(model.segundosProperty(), new NumberStringConverter());
		exerciseLabel.textProperty().bindBidirectional(model.nombreProperty());
		
		model.exercisesProperty().bindBidirectional(ejerciciosRoutineListView.itemsProperty());
	}
	
	@FXML
    void onDeleteExerciseAction(ActionEvent event) {
		ExerciseTime exerciseToDelete = ejerciciosRoutineListView.getSelectionModel().getSelectedItem();
		try {
			ejerciciosRoutineListView.getItems().remove(exerciseToDelete);
			App.info("El ejercicio con id " + exerciseToDelete.getName() + " ha sido eliminado.");
		} catch (Exception e) {
			App.error("El ejercicio con id " + exerciseToDelete.getName() + " no podido ser eliminado.", e);
			e.printStackTrace();
		}
    }
	
	@FXML
    void onAfterExerciseAction(ActionEvent event) {
		ejerciciosRoutineListView.getSelectionModel().selectNext();
    }

    @FXML
    void onBeforeExerciseAction(ActionEvent event) {
    	ejerciciosRoutineListView.getSelectionModel().selectPrevious();
    }

    @FXML
    void onStartButtonAction(ActionEvent event) {
    	deleteExerciseButton.setDisable(true);
    	afterExerciseButton.setDisable(true);
    	beforeExerciseButton.setDisable(true);
    	countDownTimer = new Timer();
    	countDownTimer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            Platform.runLater(() -> model.setSegundos(model.getSegundos()-1));
	        }
	    }, 0, 1000);
    }
    
    @FXML
    void onPauseButtonAction(ActionEvent event) {
    	countDownTimer.cancel();
    	deleteExerciseButton.setDisable(false);
    	afterExerciseButton.setDisable(false);
    	beforeExerciseButton.setDisable(false);
    }
    
    @FXML
    void onStopButtonAction(ActionEvent event) {
    	countDownTimer.cancel();
    	countDownTimer.purge();
    	model.setMinutos(model.getExerciseSelected().getMinutos());
    	model.setSegundos(model.getExerciseSelected().getSegundos());
    	ejerciciosRoutineListView.getSelectionModel().select(0);
    	deleteExerciseButton.setDisable(false);
    	afterExerciseButton.setDisable(false);
    	beforeExerciseButton.setDisable(false);
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
	
	public Label getExerciseLabel() {
		return exerciseLabel;
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
