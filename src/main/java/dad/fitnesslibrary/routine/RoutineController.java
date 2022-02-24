package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.app.App;
import dad.fitnesslibrary.classes.ExerciseTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;
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

	private Timeline countdownTimer;
	
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
		
		
		countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				model.setSegundos(model.getSegundos()-1);
			}
		}));
		countdownTimer.setAutoReverse(true);
		countdownTimer.setOnFinished(e -> {
			int index = ejerciciosRoutineListView.getSelectionModel().getSelectedIndex();
			if (!(index >= ejerciciosRoutineListView.getItems().size() - 1)) {
				ejerciciosRoutineListView.getSelectionModel().selectNext();
				int cantidadSegundos = model.getMinutos()*60 + model.getSegundos();
				countdownTimer.setCycleCount(cantidadSegundos);
				countdownTimer.play();
			}
			else {
				countdownTimer.setAutoReverse(false);
				System.out.println("It has finished");
			}
		});
		
		model.segundosProperty().addListener((obv, ov, nv) -> {
			if (nv.intValue() < 0) {
				model.setMinutos(model.getMinutos()-1);
				model.setSegundos(59);
			}
		});
		
//		.bind(
//				Bindings.when(model.segundosProperty().lessThan(0))
//					.then(segundosMenosCero())
//					.otherwise(model.getSegundos())
//		);
		
		
		
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
		
		BooleanBinding disableBefore = Bindings.when(model.exercisesProperty().emptyProperty()).then(true).otherwise(false);
		beforeExerciseButton.disableProperty().bind(disableBefore);
		
		BooleanBinding disableAfter = Bindings.when(ejerciciosRoutineListView.getSelectionModel().selectedIndexProperty().isEqualTo(model.exercisesProperty().size()-1)).then(true).otherwise(false);
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
		
		exerciseImageView.imageProperty().bindBidirectional(model.imageProperty());
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
		int segundos = model.getSegundos()+(model.getMinutos()*60);
    	countdownTimer.setCycleCount(segundos);
		countdownTimer.play();
    }
    
    @FXML
    void onPauseButtonAction(ActionEvent event) {
    	countdownTimer.pause();
    	System.out.println("Ha sido pausado");
    }
    
    @FXML
    void onStopButtonAction(ActionEvent event) {
    	countdownTimer.stop();
    	System.out.println("Ha sido parado");
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
