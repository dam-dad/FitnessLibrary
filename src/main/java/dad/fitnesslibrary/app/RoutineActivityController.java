package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.activity.ListViewController;
import dad.fitnesslibrary.activity.MenuLeftController;
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
    private BorderPane activityRoot;
	
	@FXML
    private Button buscarButton;

    @FXML
    private TextField busquedaText;

    @FXML
    private ComboBox<String> grupoMuscularCombo;

    @FXML
    private TabPane mainView;

    @FXML
    private ComboBox<String> musculoConcretoCombo;

    @FXML
    private ComboBox<String> parteCuerpoCombo;

    @FXML
    private BorderPane view;
    
    private ListViewController ListController;
    
    private MenuLeftController menuLeftController;
    
	public RoutineActivityController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoutineActivityView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		ListController = new ListViewController();
		menuLeftController = new MenuLeftController();
		
		activityRoot.setCenter(ListController.getRoot());
		activityRoot.setLeft(menuLeftController.getLeftMenuView());
	}
	
    @FXML
    void onBuscarButtonAction(ActionEvent event) {

    }
	
	public BorderPane getView() {
		return view;
	}

}
