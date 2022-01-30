package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.activity.ListViewController;
import dad.fitnesslibrary.activity.MenuLeftController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    
	public RoutineActivityController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoutineActivityView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		ListController = new ListViewController();
		menuLeftController = new MenuLeftController();
		menuBarController = new MenuBarController();
		
		activityRoot.setCenter(ListController.getRoot());
		activityRoot.setLeft(menuLeftController.getLeftMenuView());
		view.setTop(menuBarController.getView());
	}
		
	public BorderPane getView() {
		return view;
	}
}
