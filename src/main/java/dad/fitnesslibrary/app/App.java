package dad.fitnesslibrary.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	RoutineActivityController raC;

	@Override
	public void start(Stage primaryStage) throws Exception {

		raC = new RoutineActivityController();

		primaryStage.setTitle("Fitness Library");
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/img/Logo.png")));
		primaryStage.setScene(new Scene(raC.getView(), 800, 600));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
