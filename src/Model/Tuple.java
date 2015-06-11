package Model;

public class Tuple<X, Y> {
	
	public final X x;
	public final Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object other){
		return ((Tuple)other).x != null && ((Tuple)other).y != null && ((Tuple)other).x.equals(this.x) && ((Tuple)other).y.equals(this.y);
	}



}
