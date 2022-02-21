package dad.fitnesslibrary.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	RoutineActivityController raC;

	@Override
	public void start(Stage primaryStage) throws Exception {

		raC = new RoutineActivityController();
		
		primaryStage.setTitle("Fitness Library");
		primaryStage.setScene(new Scene(raC.getView()));
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
