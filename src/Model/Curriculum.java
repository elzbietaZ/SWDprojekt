package Model;

import java.awt.List;
import java.util.ArrayList;

public class Curriculum {
	
	private int id;
	private String name;
	private int nrOfCourses;
	private ArrayList<Course> courses;
	
	public Curriculum(String name, int nrOfCourses, ArrayList<Course> courses) {
		this.id=Model.curriculaCounter++;
		this.name = name;
		this.nrOfCourses = nrOfCourses;
		this.courses = courses;
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

	public int getNrOfCourses() {
		return nrOfCourses;
	}

	public void setNrOfCourses(int nrOfCourses) {
		this.nrOfCourses = nrOfCourses;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Curriculum [id=" + id + ", name=" + name + ", nrOfCourses="
				+ nrOfCourses + ", courses=" + courses + "]";
	}
	
	
	
	
	
	

}
