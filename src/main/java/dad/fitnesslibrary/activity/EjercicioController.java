package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.fitnesslibrary.classes.Exercise;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class EjercicioController implements Initializable {

	//Model
	private ObjectProperty<Exercise> Ejercicio = new SimpleObjectProperty<>();
	
	private Task<Void> cargarImagen;
	
	@FXML
	private ImageView gifImage;

	@FXML
	private Label nameExerciseLabel;

	@FXML
	private Label textExerciseLabel;

	@FXML
	private VBox view;
	
    @FXML
    private Button returnButton;

	public EjercicioController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EjercicioView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Ejercicio.addListener((o, ov, nv) -> onEjercicioChanged(o, ov, nv));

	}
	private void onEjercicioChanged(ObservableValue<? extends Exercise> o, Exercise ov, Exercise nv) {
		
		if(nv != ov)
		{
			nameExerciseLabel.setText(nv.getName());
			textExerciseLabel.setText("Muscle Group: "+nv.getBodyPart() +"\n"+
									"Equipment: "+nv.getEquipment()+"\n"+
									"Muscle: "+nv.getTarget());
			cargarImagen = new Task<Void>() {
				@Override
	    		protected Void call() throws Exception {
					gifImage.setImage(new Image(nv.gifUrl));
					return null;
				}
				
			};
			
			cargarImagen.setOnFailed(e -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fallo al cargar la imagen");
				alert.setHeaderText("A ocurrido un error al cargar la imagen del ejercicio");
				alert.setContentText(e.getSource().getException().getMessage());
				alert.showAndWait();
			});
			
			new Thread(cargarImagen).start();
		}
	
	}

	public VBox getView() {
		return view;
	}
	
	public Button getReturnButton() {
		return returnButton;
	}

	public final ObjectProperty<Exercise> EjercicioProperty() {
		return this.Ejercicio;
	}
	

	public final Exercise getEjercicio() {
		return this.EjercicioProperty().get();
	}
	

	public final void setEjercicio(final Exercise Ejercicio) {
		this.EjercicioProperty().set(Ejercicio);
	}
	

}
