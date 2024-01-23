import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;

public class Tomato extends Plant{
	private int stage;
	private String type;
	private int cost;
	private Boolean isBush;
	
	public Tomato() {
		super("tomato", 1, 2);
		type = "tomato";
		stage = 0;
		cost = 10;
		isBush = true;
	}
	
	public Boolean grow() {
		stage++;
		return true;
	}
	
	public Boolean getBush() {
		return isBush;
	}
	
	public Boolean canHarvest() {
		if(stage > 3 && stage % 3 == 0) return true;
		return false;
	}
	
	public int getStage() {
		return this.stage;
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void show() {
		if(stage == 0){
			System.out.print(".");
		}else if(stage == 1) {
			System.out.print(colorize("v", GREEN_TEXT()));
		}else if (stage == 2) {
			System.out.print(colorize("|", GREEN_TEXT()));
		}else if (stage == 3) {
			System.out.print(colorize("Y", GREEN_TEXT()));
		}else if(stage % 3 == 0){
			System.out.print(colorize("o", RED_TEXT()));
		} else {
			System.out.print(colorize("#", GREEN_TEXT()));
		}
	}
	
	public String examine() {
		if(stage == 0){
			return "A freshly planted tomato plant.";
		}else if (stage <= 3) {
			return "A growing tomato plant.";
		}else if(stage % 3 == 0){
			return "A tomato plant with a ripe tomato on it! Better harvest before it rots.";
		} else {
			return "A fully grown tomato plant. Give it a while, and it'll grow a tomato.";
		}
	}
}
