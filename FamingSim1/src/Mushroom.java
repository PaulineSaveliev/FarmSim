import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;



public class Mushroom extends Plant {
	private int stage;
	private String type;
	private int cost;
	private Boolean isBush;
	
	public Mushroom() {
		super("mushroom", 1, 2);
		type = "mushroom";
		stage = 0;
		cost = 50;
		isBush = false;
	}
	
	public Boolean grow() {
		stage++;
		return true;
	}
	
	public Boolean canHarvest() {
		if(stage >= 6) return true;
		return false;
	}
	
	public Boolean getBush() {
		return isBush;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getStage() {
		return this.stage;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void show() {
		if(stage < 6){
			System.out.print(".");
		}else {
			System.out.print(colorize("n", TEXT_COLOR(172)));
		}
	}
	
	public String examine() {
		if(stage < 6){
			return "A mushroom. It's still growing, though.";
		}else {
			return "A fully grown mushroom! Better harvest before it spreads.";
		}
	}
	
}
