package dad.fitnesslibrary.routine;

import dad.fitnesslibrary.classes.ExerciseTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
/**
 * Clase de la rutina para mostrar los objetos 
 *
 */
public class RoutineModel {

	private ObjectProperty<ExerciseTime> exerciseSelected;

	private ListProperty<ExerciseTime> exercises;

	private ObjectProperty<Image> image;

	private IntegerProperty minutos;

	private IntegerProperty segundos;

	private StringProperty nombre;

	public RoutineModel() {
		exerciseSelected = new SimpleObjectProperty<>();
		image = new SimpleObjectProperty<>();
		minutos = new SimpleIntegerProperty(0);
		segundos = new SimpleIntegerProperty(0);
		nombre = new SimpleStringProperty();
		exercises = new SimpleListProperty<>(FXCollections.observableArrayList());
		
//		exerciseSelected.addListener((obv, ov, nv) -> {
//			if (ov != nv && Objects.nonNull(nv)) {
//				
//			}
//		});
	}
	/**
	 * Cambia los segundos sobrantes a minutos
	 */

	public final ObjectProperty<ExerciseTime> exerciseSelectedProperty() {
		return this.exerciseSelected;
	}

	public final ExerciseTime getExerciseSelected() {
		return this.exerciseSelectedProperty().get();
	}

	public final void setExerciseSelected(final ExerciseTime exerciseSelected) {
		this.exerciseSelectedProperty().set(exerciseSelected);
	}

	public final ObjectProperty<Image> imageProperty() {
		return this.image;
	}

	public final Image getImage() {
		return this.imageProperty().get();
	}

	public final void setImage(final Image imageProperty) {
		this.imageProperty().set(imageProperty);
	}

	public final IntegerProperty minutosProperty() {
		return this.minutos;
	}

	public final int getMinutos() {
		return this.minutosProperty().get();
	}

	public final void setMinutos(final int minutos) {
		this.minutosProperty().set(minutos);
	}

	public final IntegerProperty segundosProperty() {
		return this.segundos;
	}

	public final int getSegundos() {
		return this.segundosProperty().get();
	}

	public final void setSegundos(final int segundos) {
		this.segundosProperty().set(segundos);
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final ListProperty<ExerciseTime> exercisesProperty() {
		return this.exercises;
	}

	public final ObservableList<ExerciseTime> getExercises() {
		return this.exercisesProperty().get();
	}

	public final void setExercises(final ObservableList<ExerciseTime> exercises) {
		this.exercisesProperty().set(exercises);
	}
}
