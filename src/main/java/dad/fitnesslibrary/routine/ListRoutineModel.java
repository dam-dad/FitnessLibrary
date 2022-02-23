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

	public ListRoutineModel() {
		routine = new SimpleObjectProperty<>(new Routine());
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
}
