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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

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
					routineTab.setContent(listRoutinesController.getRoutineController().getRoot());
				}
			}
		});
		
		listRoutinesController.getRoutineController().getBackButton().setOnAction((e -> {
			routineTab.setContent(listRoutinesController.getRoot());
		}));

		ejercicioController.getAddToRoutineButton().setOnAction(e -> {
			addExerciseController.getExerciseLabel().setText(TableViewController.getSelectedExercise().toString());
			addExerciseController.getAddExerciseStage().showAndWait();
		});

		addExerciseController.getAddExerciseButton().setOnAction(e -> {
			Exercise exerciseSelected = TableViewController.getSelectedExercise();
			int minutos;
			int segundos;
			try {
				minutos = Integer.parseInt(addExerciseController.getMinutosTextField().getText());
				segundos = Integer.parseInt(addExerciseController.getSegundosTextField().getText());
				ExerciseTime exerciseWithTimer = new ExerciseTime(exerciseSelected, minutos, segundos);
				listRoutinesController.getRoutineController().getEjerciciosRoutineListView().getItems().add(exerciseWithTimer);
			} catch (NumberFormatException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				e1.printStackTrace();
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

		// Listeners

		menuLeftController.getBodypartTG().selectedToggleProperty().addListener((obv, ov, nv) -> {
			if (ov != nv) {
				RadioButton selectedRadioButton = (RadioButton) nv.getToggleGroup().getSelectedToggle();
				System.out.println(selectedRadioButton.getText());
			}
		});

		menuLeftController.getEquipmentTG().selectedToggleProperty().addListener((obv, ov, nv) -> {
			if (ov != nv) {
				RadioButton selectedRadioButton = (RadioButton) nv.getToggleGroup().getSelectedToggle();
				System.out.println(selectedRadioButton.getText());
			}
		});

		menuLeftController.getTargetTG().selectedToggleProperty().addListener((obv, ov, nv) -> {
			if (ov != nv) {
				RadioButton selectedRadioButton = (RadioButton) nv.getToggleGroup().getSelectedToggle();
				System.out.println(selectedRadioButton.getText());
			}
		});
	}

	private void onBuscarButtonAction(ActionEvent e) {
		try {
			TableViewController.getByName(menuBarController.getBusquedaText().getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public MainMenuController getMainMenuController() {
		return mainMenuController;
	}

	public BorderPane getView() {
		return view;
	}
}
