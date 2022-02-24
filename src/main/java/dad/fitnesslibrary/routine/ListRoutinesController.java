package dad.fitnesslibrary.routine;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import dad.fitnesslibrary.app.App;
import dad.fitnesslibrary.classes.Routine;
import javafx.beans.value.ObservableValue;
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
		try {
			rutinasListView.getItems().remove(selectedRoutine);
			App.info("La rutina " + selectedRoutine.getName() + " ha sido eliminada.");
		} catch (Exception e) {
			App.error("La rutina " + selectedRoutine.getName() + " no podido ser eliminada.", e);
			e.printStackTrace();
		}
	}

	@FXML
	void onExportRoutineAction(ActionEvent event) throws IOException {
		RoutineJson selectedRoutine = RoutineJson.fromRoutinetoJson(rutinasListView.getSelectionModel().getSelectedItem());
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(selectedRoutine);

			File file = new File("src\\main\\resources\\json\\" + selectedRoutine.getName() + ".json");

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json);
			bw.close();
			App.info("La rutina " + selectedRoutine.getName() + " ha sido exportada.");
		} catch (IOException e) {
			App.error("La rutina " + selectedRoutine.getName() + " no podido ser exportada.", e);
			e.printStackTrace();
		}
	}

	@FXML
	void onImportRoutineAction(ActionEvent event){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Importar Rutina");

			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json", "*.json"));
			fileChooser.setInitialDirectory(new File(getClass().getResource("/json").getFile()));
			File json = fileChooser.showOpenDialog(null);		
			
			RoutineJson importRoutine = new RoutineJson();

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String content = Files.readString(json.toPath());
			
			importRoutine = gson.fromJson(content, RoutineJson.class);
			rutinasListView.getItems().add(RoutineJson.fromJsontoRoutine(importRoutine));
			App.info("La rutina ha sido importada.");
		} catch (JsonSyntaxException | IOException e) {
			App.error("La rutina no podido ser importada.", e);
			e.printStackTrace();
		}		
	}

	@FXML
	void onNewRoutineAction(ActionEvent event) {
		try {
			rutinasListView.getItems().add(new Routine());
			App.info("La rutina ha sido creada.");
		} catch (Exception e) {
			App.error("La rutina no podido ser creada.", e);
			e.printStackTrace();
		}
	}

	@FXML
	void onSaveRoutineAction(ActionEvent event) {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		try {
			JasperReport report = JasperCompileManager.compileReport(ListRoutinesController.class.getResourceAsStream(JRXML_FILE));
			Map<String, Object> parameters = new HashMap<String, Object>();
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(selectedRoutine.getExercisesList()));
			JasperExportManager.exportReportToPdfFile(print, "pdf/" + selectedRoutine.getName() + ".pdf");
			Desktop.getDesktop().open(new File("pdf/" + selectedRoutine.getName() + ".pdf"));
			App.info("La rutina " + selectedRoutine.getName() + " ha sido guardada.");
		} catch (JRException | IOException e) {
			App.error("La rutina " + selectedRoutine.getName() + " no podido ser guardada.", e);
			e.printStackTrace();
		}
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
