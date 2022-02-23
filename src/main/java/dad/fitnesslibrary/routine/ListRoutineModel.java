package dad.fitnesslibrary.routine;

import dad.fitnesslibrary.classes.ExerciseTime;
import dad.fitnesslibrary.classes.Routine;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class ListRoutineModel {

	private ObjectProperty<Routine> routine;

	private ListProperty<ExerciseTime> listaEjercicios;

	public ListRoutineModel() {
		routine = new SimpleObjectProperty<>(new Routine());
		listaEjercicios = new SimpleListProperty<>();
	}

	public final ObjectProperty<Routine> routineProperty() {
		return this.routine;
	}

	public final Routine getRoutine() {
		return this.routineProperty().get();
	}

	public final void setRoutine(final Routine routine) {
		this.routineProperty().set(routine);
	}

	public final ListProperty<ExerciseTime> listaEjerciciosProperty() {
		return this.listaEjercicios;
	}

	public final ObservableList<ExerciseTime> getListaEjercicios() {
		return this.listaEjerciciosProperty().get();
	}

	public final void setListaEjercicios(final ObservableList<ExerciseTime> listaEjercicios) {
		this.listaEjerciciosProperty().set(listaEjercicios);
	}
}
