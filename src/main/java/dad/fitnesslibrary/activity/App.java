package dad.fitnesslibrary.activity;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private ListViewController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		controller = new ListViewController();
		
		Scene scene = new Scene(controller.getRoot());
		
		primaryStage.setTitle("APIREST_Trials");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
