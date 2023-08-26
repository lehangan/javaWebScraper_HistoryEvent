package Entity;

public abstract class Entity {
	protected String id;
	protected String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return id;
	}

	public void setName(String name) {
		this.id = name;
	}
	

	public abstract String toString();
}