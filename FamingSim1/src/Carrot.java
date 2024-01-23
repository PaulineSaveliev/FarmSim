import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.*;



public class Carrot extends Plant {
	private int stage;
	private String type;
	private int cost;
	private Boolean isBush;
	
	public Carrot() {
		super("carrot", 1, 2);
		type = "carrot";
		stage = 0;
		cost = 10;
		isBush = false;
	}
	
	public Boolean grow() {
		if(stage > 4) {
			return false;
		}
		stage++;
		return true;
	}
	
	public Boolean canHarvest() {
		if(stage >= 4) return true;
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
		if(stage == 0){
			System.out.print(".");
		}else if(stage == 1) {
			System.out.print(colorize("v", GREEN_TEXT()));
		}else if (stage == 2) {
			System.out.print(colorize("|", GREEN_TEXT()));
		}else if (stage == 3) {
			System.out.print(colorize("Y", GREEN_TEXT()));
		}else {
			System.out.print(colorize("^", TEXT_COLOR(208)));
		}
	}
	
	public String examine() {
		if(stage == 0){
			return "A freshly planted carrot seed.";
		}else if (stage <= 3) {
			return "A carrot plant! It's still growing, though. Give it a bit.";
		}else {
			return "A fully grown carrot! You can harvest it to make some money.";
		}
	}
	
}
