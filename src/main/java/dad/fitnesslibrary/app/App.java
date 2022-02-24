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

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		raC = new RoutineActivityController();

		primaryStage.setTitle("Fitness Library");
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/img/Logo.png")));
		primaryStage.setScene(new Scene(raC.getView(), 800, 600));
		primaryStage.show();

	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static boolean confirm(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait().get().equals(ButtonType.OK);
	}

	public static void error(String header, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("FitnessLibrary-Error");
		alert.setHeaderText(header);
		if (e.getMessage() == null && e.getCause() != null) {
			alert.setContentText(e.getCause().getMessage());
		} else {
			alert.setContentText(e.getMessage());
		}
		alert.showAndWait();
	}

	public static void info(String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("FitnessLibrary-Info");
		alert.setHeaderText(header);
		alert.setContentText("");
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
