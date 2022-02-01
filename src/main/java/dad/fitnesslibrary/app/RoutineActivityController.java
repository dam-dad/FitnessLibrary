package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.activity.EjercicioController;
import dad.fitnesslibrary.activity.ListViewController;
import dad.fitnesslibrary.activity.MenuBarController;
import dad.fitnesslibrary.activity.MenuLeftController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Toggle;
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

	private ListViewController ListController;

	private MenuLeftController menuLeftController;

	private MenuBarController menuBarController;

	private EjercicioController ejercicioController;

	public RoutineActivityController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoutineActivityView.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		ListController = new ListViewController();
		menuLeftController = new MenuLeftController();
		menuBarController = new MenuBarController();
		ejercicioController = new EjercicioController();

		activityRoot.setCenter(ListController.getRoot());
		activityRoot.setLeft(menuLeftController.getLeftMenuView());
		activityRoot.setTop(menuBarController.getView());

		ejercicioController.EjercicioProperty().bind(ListController.selectedExerciseProperty());

		// onActions
		ListController.getRoot().setOnMouseClicked(e -> {
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				if (e.getClickCount() == 2) {
					activityRoot.setCenter(ejercicioController.getView());
				}
			}
		});
		
		ejercicioController.getReturnButton().setOnAction(e -> {
			activityRoot.setCenter(ListController.getRoot());
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
			ListController.getByName(menuBarController.getBusquedaText().getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}
}
