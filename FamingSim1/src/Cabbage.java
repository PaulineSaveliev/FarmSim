import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;
import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public class Cabbage extends Plant{
	private String type;
	private int cost;
	private Boolean isBush;
	
	public Cabbage() {
		super("cabbage", 10, 20);
		type = "cabbage";
		isBush = false;
	}
	
	public Boolean grow() {
		if(stage > 6) {
			return false;
		}
		stage++;
		return true;
	}
	
	public int getStage() {
		return this.stage;
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
		}else if (stage >= 6){
			System.out.print(colorize("o", TEXT_COLOR(34)));
		}else{
			System.out.print(colorize("Y", GREEN_TEXT()));
		}
	}
	
	public String examine() {
		if(stage == 0){
			return "A freshly planted cabbage seed. Boring.";
		}else if (stage <= 2) {
			return "A growing cabbage plant. This might take a while to grow.";
		}else if (stage >= 6){
			return "A fully grown cabbage! Wonder how much this is worth...";
		}else{
			return "A cabbage plant. Looks to be almost ready.";
		}
	}
}
