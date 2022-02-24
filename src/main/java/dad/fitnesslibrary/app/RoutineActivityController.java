package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.activity.EjercicioController;
import dad.fitnesslibrary.activity.MenuBarController;
import dad.fitnesslibrary.activity.MenuLeftController;
import dad.fitnesslibrary.activity.TableViewController;
import dad.fitnesslibrary.classes.Exercise;
import dad.fitnesslibrary.classes.ExerciseTime;
import dad.fitnesslibrary.mainMenu.MainMenuController;
import dad.fitnesslibrary.routine.AddExerciseController;
import dad.fitnesslibrary.routine.ListRoutinesController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
/**
 * Controlador de la rutina 
 *
 */
public class RoutineActivityController implements Initializable {

	@FXML
	private BorderPane activityRoot;

	@FXML
	private TabPane mainView;

	@FXML
	private Tab routineTab;

	@FXML
	private Tab activityTab;
	
	@FXML
    private Label TimeHiddenLabel;
	
	@FXML
    private Label exerciseHiddenLabel;
	
	@FXML
    private ImageView hiddenImageView;
	
	@FXML
    private HBox hiddenBox;

	@FXML
	private BorderPane view;

	private TableViewController TableViewController;

	private MenuLeftController menuLeftController;

	private MenuBarController menuBarController;

	private EjercicioController ejercicioController;

	private MainMenuController mainMenuController;

	private ListRoutinesController listRoutinesController;

	private AddExerciseController addExerciseController;

	public RoutineActivityController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoutineActivityView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		TableViewController = new TableViewController();
		menuLeftController = new MenuLeftController();
		menuBarController = new MenuBarController();
		ejercicioController = new EjercicioController();
		mainMenuController = new MainMenuController();
		listRoutinesController = new ListRoutinesController();
		addExerciseController = new AddExerciseController();

		routineTab.setContent(listRoutinesController.getRoot());

		view.setCenter(mainMenuController.getRootGridPane());

		activityRoot.setCenter(TableViewController.getRoot());
		activityRoot.setLeft(menuLeftController.getLeftMenuView());
		activityRoot.setTop(menuBarController.getView());

		ejercicioController.EjercicioProperty().bind(TableViewController.selectedExerciseProperty());

		ejercicioController.getAddToRoutineButton().disableProperty().bind(
				Bindings.when(routineTab.contentProperty().isEqualTo(listRoutinesController.getRoutineController().getRoot())).then(false).otherwise(true)
		);

		// onActions
		mainMenuController.getBtn_Start().setOnAction(e -> {
			view.setCenter(mainView);
		});

		listRoutinesController.getRoutineController().getBackButton().setOnAction(e -> {
			routineTab.setContent(listRoutinesController.getRoot());
		});

		listRoutinesController.getRutinasListView().setOnMouseClicked(e -> {
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				if (e.getClickCount() == 2) {
					if (!listRoutinesController.getRutinasListView().getItems().isEmpty()) {	
						routineTab.setContent(listRoutinesController.getRoutineController().getRoot());
					}
				}
			}
		});
		
		listRoutinesController.getRoutineController().getBackButton().setOnAction((e -> {
			routineTab.setContent(listRoutinesController.getRoot());
		}));

		ejercicioController.getAddToRoutineButton().setOnAction(e -> {
			addExerciseController.getExerciseLabel().setText(TableViewController.getSelectedExercise().getName());
			addExerciseController.getAddExerciseStage().showAndWait();
		});

		addExerciseController.getAddExerciseButton().setOnAction(e -> {
			Exercise exerciseSelected = TableViewController.getSelectedExercise();
			int minutos;
			int segundos;
			try {
				minutos = Integer.parseInt(addExerciseController.getMinutosTextField().getText());
				segundos = Integer.parseInt(addExerciseController.getSegundosTextField().getText());
				if (segundos >= 60) {
					minutos += (int) segundos / 60;
					segundos = (int) (segundos % 60)*60;
				}
				ExerciseTime exerciseWithTimer = new ExerciseTime(exerciseSelected, minutos, segundos);
				listRoutinesController.getRoutineController().getModel().exercisesProperty().add(exerciseWithTimer);
				listRoutinesController.getRoutineController().getEjerciciosRoutineListView().getSelectionModel().select(exerciseWithTimer);
				addExerciseController.getAddExerciseStage().close();
			} catch (NumberFormatException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("No se ha introducido un valor adecuado");
				alert.setContentText("No se ha introducido un valor entero valido \n"
						+ e1.getMessage());
				alert.showAndWait();
			}
		});

		TableViewController.getRoot().setOnMouseClicked(e -> {
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				if (e.getClickCount() == 2) {
					activityRoot.setCenter(ejercicioController.getView());
				}
			}
		});

		ejercicioController.getReturnButton().setOnAction(e -> {
			activityRoot.setCenter(TableViewController.getRoot());
		});

		menuBarController.getBuscarButton().setOnAction(e -> onBuscarButtonAction(e));
		
		listRoutinesController.getRoutineController().getExerciseImageView().imageProperty().addListener((obv, ov, nv) -> {
			if (ov != nv) {
				hiddenImageView.setImage(nv);
			}
		});
		
		
		hiddenImageView.visibleProperty().bind(
				Bindings.when(mainView.getSelectionModel().selectedItemProperty().isEqualTo(activityTab))
				.then(true)
				.otherwise(false)
		);
		
		exerciseHiddenLabel.textProperty().bind(listRoutinesController.getRoutineController().getExerciseLabel().textProperty());
		
		StringExpression timeExpression = listRoutinesController.getRoutineController().getModel().minutosProperty().asString().concat(":").concat(listRoutinesController.getRoutineController().getModel().segundosProperty().asString());
		TimeHiddenLabel.textProperty().bind(timeExpression);
		
		NumberBinding sizeHiddenBoxBinding = Bindings.when(mainView.getSelectionModel().selectedItemProperty().isEqualTo(activityTab))
			.then(95)
			.otherwise(0);
		
		hiddenBox.prefHeightProperty().bind(sizeHiddenBoxBinding);
		
		// Listeners
	}

	private void onBuscarButtonAction(ActionEvent e) {
		try {
			TableViewController.getByName(menuBarController.getBusquedaText().getText());
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No se ha introducido un valor adecuado");
			alert.setContentText("No se ha introducido un valor de búsqueda valido \n"
					+ e1.getMessage());
			alert.showAndWait();
		}
	}

	public MainMenuController getMainMenuController() {
		return mainMenuController;
	}

	public BorderPane getView() {
		return view;
	}
}
