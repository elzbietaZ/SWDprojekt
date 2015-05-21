package Model;

/**
 * Unavailability_Constraints: <CourseID> <Day> <Day_Period>
 * All IDs are strings without blanks starting with a letter. 
 * Days and periods start from 0. 
 * For example, the constraint TecCos 3 2 states that course TecCos cannot be scheduled in the third (2) period of Thursdays (3).
 * @author Elzbieta
 *
 */

public class UnavailabilityConstraint {
	
	private Course course;
	private int day;
	private int dayPeriod;
	
	public UnavailabilityConstraint(Course course, int day, int dayPeriod) {
		this.course = course;
		this.day = day;
		this.dayPeriod = dayPeriod;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDayPeriod() {
		return dayPeriod;
	}

	public void setDayPeriod(int dayPeriod) {
		this.dayPeriod = dayPeriod;
	}

	@Override
	public String toString() {
		return "UnavailabilityConstraint [course=" + course + ", day=" + day
				+ ", dayPeriod=" + dayPeriod + "]";
	}
	
	
	

}
