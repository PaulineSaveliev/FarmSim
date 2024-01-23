import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public class Banana extends Plant{
	private int stage;
	private String type;
	private int cost;
	private Boolean isBush;
	
	public Banana() {
		super("banana", 1, 2);
		type = "banana";
		stage = 0;
		cost = 30;
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
		if(stage % 5 == 0) return true;
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
		}else if (stage <= 3) {
			System.out.print(colorize("|", GREEN_TEXT()));
		}else if (stage <= 6) {
			System.out.print(colorize("Y", GREEN_TEXT()));
		}else if(stage % 5 == 0){
			System.out.print(colorize(")", TEXT_COLOR(226)));
		} else {
			System.out.print(colorize("╣", TEXT_COLOR(172)));
		}
	}
	
	public String examine() {
		if(stage == 0){
			return "A freshly planted banana sapling.";
		}else if(stage == 1) {
			return "A banana sapling. Can it even grow here?";
		}else if (stage <= 3) {
			return "A medium sized banana sapling. Don't step on it.";
		}else if (stage <= 6) {
			return "A large banana sapling. This is taking a while, huh...";
		}else if(stage % 5 == 0){
			return "A banana tree with a ripe banana!";
		} else {
			return "A fully grown banana tree. Smells fruity!";
		}
	}
}
