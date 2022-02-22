package dad.fitnesslibrary.routine;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private ListView<?> rutinasListView;
    
    @FXML
    private GridPane root;
    
    private RoutineController routineController;
	
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
	}
	
	@FXML
    void onDeleteRoutineAction(ActionEvent event) {

    }

    @FXML
    void onExportRoutineAction(ActionEvent event) {

    }

    @FXML
    void onImportRoutineAction(ActionEvent event) {

    }

    @FXML
    void onNewRoutineAction(ActionEvent event) {

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
