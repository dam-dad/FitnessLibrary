package dad.fitnesslibrary.routine;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dad.fitnesslibrary.classes.ExerciseTime;
import dad.fitnesslibrary.classes.Routine;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	private ListRoutineModel model = new ListRoutineModel();

	public static final String JRXML_FILE = "/reports/routine.jrxml";

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

		model.routineProperty().bind(rutinasListView.getSelectionModel().selectedItemProperty());
		model.routineProperty().addListener((obv, ov, nv) -> onRoutineSeleccionado(obv, ov, nv));

//		rutinasListView.getSelectionModel().selectedItemProperty().addListener((obv,ov,nv) -> {
//			if (ov != null) {
//				
//			}
//			
//			if (nv != null) {
//				routineController.getNameRoutineTextField().setText(nv.getName());
//				routineController.getEjerciciosRoutineListView().setItems(nv.getExercisesList());
//			}
//		});
//		
//		routineController.getNameRoutineTextField().textProperty().addListener((obv, ov, nv) -> {
//			if (ov != nv && nv != null) {
//				rutinasListView.getSelectionModel().getSelectedItem().setName(nv);
//			}
//		});
	}

	private void onRoutineSeleccionado(ObservableValue<? extends Routine> obv, Routine ov, Routine nv) {
		if (ov != null) {
			routineController.getNameRoutineTextField().textProperty().unbindBidirectional(ov.nameProperty());
			routineController.getEjerciciosRoutineListView().itemsProperty()
					.unbindBidirectional(ov.exercisesListProperty());
		}

		if (nv != null) {
			routineController.getNameRoutineTextField().textProperty().bindBidirectional(nv.nameProperty());
			routineController.getEjerciciosRoutineListView().itemsProperty()
					.bindBidirectional(nv.exercisesListProperty());
		}
	}

	@FXML
	void onDeleteRoutineAction(ActionEvent event) {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		rutinasListView.getItems().remove(selectedRoutine);
	}

	@FXML
	void onExportRoutineAction(ActionEvent event) throws IOException {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(selectedRoutine);

		File file = new File("json/" + selectedRoutine.getName() + ".json");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(json);
		bw.close();
	}

	@FXML
	void onImportRoutineAction(ActionEvent event) {
		String jsonData = null;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Importar Rutina");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json", "*.json"));
		fileChooser.setInitialDirectory(new File("C:\\Users\\gardo\\eclipse-workspace\\DAD\\FitnessLibrary\\json"));
		File json = fileChooser.showOpenDialog(null);

		Routine importRoutine = new Routine();
		ExerciseTime importExercise = new ExerciseTime();
		ObservableList<ExerciseTime> exercises = null;

		JSONObject ob = new JSONObject(json);
		String value = ob.getJSONObject("name").getString("value");
		importRoutine.setName(value);
		JSONArray arr = ob.getJSONArray("exercisesList");
		for (int i = 0; i < arr.length(); i++) {
			int min = arr.getJSONObject(i).getInt("minutos");
			importExercise.setMinutos(min);
			int seg = arr.getJSONObject(i).getInt("segundos");
			importExercise.setSegundos(seg);
			String id = arr.getJSONObject(i).getString("id");
			importExercise.setId(id);
			String gifUrl = arr.getJSONObject(i).getString("gifUrl");
			importExercise.setGifUrl(gifUrl);
			String name = arr.getJSONObject(i).getString("name");
			importExercise.setName(name);
			String equipment = arr.getJSONObject(i).getString("equipment");
			importExercise.setEquipment(equipment);
			String bodyPart = arr.getJSONObject(i).getString("bodyPart");
			importExercise.setBodyPart(bodyPart);
			String target = arr.getJSONObject(i).getString("target");
			importExercise.setTarget(target);
			exercises.add(importExercise);
		}
		importRoutine.setExercisesList(exercises);
		rutinasListView.getItems().add(importRoutine);

	}

	@FXML
	void onNewRoutineAction(ActionEvent event) {
		rutinasListView.getItems().add(new Routine());
	}

	@FXML
	void onSaveRoutineAction(ActionEvent event) throws JRException, IOException {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		JasperReport report = JasperCompileManager
				.compileReport(ListRoutinesController.class.getResourceAsStream(JRXML_FILE));
		Map<String, Object> parameters = new HashMap<String, Object>();
		JasperPrint print = JasperFillManager.fillReport(report, parameters,
				new JRBeanCollectionDataSource(selectedRoutine.getExercisesList()));
		JasperExportManager.exportReportToPdfFile(print, "pdf/" + selectedRoutine.getName() + ".pdf");
		Desktop.getDesktop().open(new File("pdf/" + selectedRoutine.getName() + ".pdf"));
	}

	public RoutineController getRoutineController() {
		return routineController;
	}

	public ListView<Routine> getRutinasListView() {
		return rutinasListView;
	}

	public GridPane getRoot() {
		return root;
	}
}
