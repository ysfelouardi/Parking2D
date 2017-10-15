
public class Place {
	private int y ;
	private boolean empty;
	
	public Place(int y,boolean empty){
		this.y = y;
		this.empty = empty;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
}
