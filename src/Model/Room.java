package Model;

public class Room {
	
	private int id;
	private String name;
	private int capacity;
	
	
	public Room(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		this.id=name.hashCode();
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
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", capacity=" + capacity
				+ "]";
	}
	
	
	
	
	

}
