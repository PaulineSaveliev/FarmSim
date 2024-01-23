
public class Dirt extends Plant{
	private String type;
	private int cost;
	
	public Dirt() {
		super("dirt", 0, 0);
		type = "dirt";
		cost = 0;
	}
	
	public void show() {
		System.out.print("_");
	}
	
	public Boolean grow() {
		return true;
	}
	
	public int getStage() {
		return 0;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String examine() {
		return "A patch of dirt. You can plant something in it, if you want to.";
	}
}
