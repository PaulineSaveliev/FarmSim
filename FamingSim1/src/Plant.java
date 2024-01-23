
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Plant {
	public String type;
	public int stage;
	private int cost;
	private Boolean isBush;
	
	public Plant(String type, int cost, int value) {
		stage = 0;
		this.type = type;
		this.cost = cost;
		isBush = false;
	}
	
	public String getType() {
		return type;
	}
	
	public Boolean getBush() {
		return isBush;
	}
	
	public Boolean canHarvest() {
		if(stage >= 4) return true;
		return false;
	}
	
	public Boolean grow() {
		if(stage > 4) {
			return false;
		}
		stage++;
		return true;
	}
	
	public int getStage() {
		return stage;
	}
	
	public int getCost() {
		return this.cost;
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
		}else{
			System.out.print(colorize("x", RED_TEXT()));
		}
	}
	
	public String examine() {
		if(stage == 0){
			return "A freshly planted plant! Let's see what it grows into...";
		}else if (stage <= 3) {
			return "A plant! It's still growing, though.";
		}else {
			return "A fully grown plant! No idea what it is, though.";
		}
	}
}
