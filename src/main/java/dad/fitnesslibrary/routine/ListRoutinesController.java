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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dad.fitnesslibrary.classes.Routine;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
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
	
    private ObjectProperty<Routine> routine;
    
	public static final String JRXML_FILE = "/reports/routine.jrxml";
	public static final String PDF_FILE = "pdf/routine.pdf";
	public static final String RUTA = "json/routine.json";
    
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
		
		routine = new SimpleObjectProperty<>(new Routine());
		
		routineController.getNameRoutineTextField().textProperty().bindBidirectional(routine.get().nameProperty());
		
		routineController.getEjerciciosRoutineListView().itemsProperty().bindBidirectional(routine.get().exercisesListProperty());
		
		routine.bind(rutinasListView.getSelectionModel().selectedItemProperty());
	}
	
	@FXML
    void onDeleteRoutineAction(ActionEvent event) {
		Routine selectedRoutine = rutinasListView.getSelectionModel().getSelectedItem();
		rutinasListView.getItems().remove(selectedRoutine);
    }

    @FXML
    void onExportRoutineAction(ActionEvent event) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(routine.asString());
		
        File file = new File(RUTA);
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
    	
    }

    @FXML
    void onNewRoutineAction(ActionEvent event) {
    	rutinasListView.getItems().add(new Routine());
    }

    @FXML
    void onSaveRoutineAction(ActionEvent event) throws JRException, IOException {
		JasperReport report = JasperCompileManager.compileReport(ListRoutinesController.class.getResourceAsStream(JRXML_FILE));		
		Map<String, Object> parameters = new HashMap<String, Object>();
        JasperPrint print  = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(RoutineDataProvider.getRoutines()));
        JasperExportManager.exportReportToPdfFile(print, PDF_FILE);
		Desktop.getDesktop().open(new File(PDF_FILE));
    }
    
	public RoutineController getRoutineController() {
		return routineController;
	}

	public ListView<?> getRutinasListView() {
		return rutinasListView;
	}

	public GridPane getRoot() {
		return root;
	}
}
