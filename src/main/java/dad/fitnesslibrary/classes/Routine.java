package dad.fitnesslibrary.classes;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Routine {

	private StringProperty name;

	private ListProperty<ExerciseTime> exercisesList;

	public Routine() {
		name = new SimpleStringProperty("Untitled");
		exercisesList = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final ListProperty<ExerciseTime> exercisesListProperty() {
		return this.exercisesList;
	}

	public final ObservableList<ExerciseTime> getExercisesList() {
		return this.exercisesListProperty().get();
	}

	public final void setExercisesList(final ObservableList<ExerciseTime> exercisesList) {
		this.exercisesListProperty().set(exercisesList);
	}

	@Override
	public String toString() {
		return name.get();
	}
}
