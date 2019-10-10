
public class Move {

	private Coord coordOld;
	private Coord coordNew;
	
	public Move(Coord coordOld, Coord coordNew) {
		this.coordOld=coordOld;
		this.coordNew=coordNew;
	}
	
	public Coord getCoordOld() {
		return coordOld;
	}
	
	public Coord getCoordNew() {
		return coordNew;
	}
	
	
}
