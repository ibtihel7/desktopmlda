package tn.corp.mlda.mlda.model;

public class Category {
	private String name;
	private String description;

	public Category() {
		// TODO Auto-generated constructor stub
	}


	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}



	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
}