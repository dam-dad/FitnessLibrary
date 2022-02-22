package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.Routine;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ListRoutinesController implements Initializable {

	@FXML
    private Button ExportRoutineButton;

    @FXML
    private Button SaveRoutineButton;

    @FXML
    private Button deleteRoutineButton;

    @FXML
    private Button importRoutineButton;

    @FXML
    private Button newRoutineButton;

    @FXML
    private ListView<Routine> rutinasListView;
    
    @FXML
    private GridPane root;
    
    private RoutineController routineController;
	
    private ObjectProperty<Routine> routine;
    
	public ListRoutinesController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListaRutinaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		routineController = new RoutineController();
		
		routine = new SimpleObjectProperty<>(new Routine());
		
		routineController.getNameRoutineTextField().textProperty().bindBidirectional(routine.get().nameProperty());
		
		routineController.getEjerciciosRoutineListView().itemsProperty().bindBidirectional(routine.get().exercisesListProperty());
		
		routine.bind(rutinasListView.getSelectionModel().selectedItemProperty());
	}
	
	@FXML
    void onDeleteRoutineAction(ActionEvent event) {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		rutinasListView.getItems().remove(selectedRoutine);
    }

    @FXML
    void onExportRoutineAction(ActionEvent event) {
    	
    }

    @FXML
    void onImportRoutineAction(ActionEvent event) {
    	
    }

    @FXML
    void onNewRoutineAction(ActionEvent event) {
    	rutinasListView.getItems().add(new Routine());
    }

    @FXML
    void onSaveRoutineAction(ActionEvent event) {
    	
    }
    
	public RoutineController getRoutineController() {
		return routineController;
	}

	public ListView<?> getRutinasListView() {
		return rutinasListView;
	}

	public GridPane getRoot() {
		return root;
	}
}
