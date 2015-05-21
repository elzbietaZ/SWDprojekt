package Model;

public class Teacher {

	private int id;
	private String name;
	
	public Teacher(String name) {
		this.id=name.hashCode();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
}
