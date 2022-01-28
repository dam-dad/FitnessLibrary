package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class RoutineActivityController implements Initializable {


    @FXML
    private BorderPane view;
	
    @FXML
    private TabPane mainView;

    @FXML
    private Tab activityTab;
    
    @FXML
    private Tab routineTab;

    @FXML
    private Button buscarButton;

    @FXML
    private TextField busquedaText;

    @FXML
    private ComboBox<?> parteCuerpoCombo;
    
	public RoutineActivityController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoutineActivityView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
    @FXML
    void onBuscarButtonAction(ActionEvent event) {

    }
	
	public BorderPane getView() {
		return view;
	}

}
