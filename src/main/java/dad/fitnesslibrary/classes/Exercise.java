package dad.fitnesslibrary.classes;

public class Exercise {

	public String bodyPart;
	public String equipment;
	public String gifUrl;
	public String id;
	public String name;
	public String target;

	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getGifUrl() {
		return gifUrl;
	}

	public void setGifUrl(String gifUrl) {
		this.gifUrl = gifUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public String toString() {
		return "Exercise [bodyPart=" + bodyPart + ", equipment=" + equipment + ", gifUrl=" + gifUrl + ", id=" + id
				+ ", name=" + name + ", target=" + target + "]";
	}
}
