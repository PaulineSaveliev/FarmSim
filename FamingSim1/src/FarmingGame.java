import java.util.ArrayList;
import java.util.Scanner;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public class FarmingGame {
	
	public int coins;
	public Plant[][] field;
	private ArrayList<Plant> inv;
	private Scanner p;
	private boolean unlocked;
	private boolean offended;
	private boolean askedForMoney;
	private int debt;
	private boolean paidBack;
	
	private Double VERSION = 1.17;
	
	public FarmingGame(int c, Plant[][] f, ArrayList<Plant> i, Scanner in) {
		coins = c;
		field = f;
		inv = i;
		p = in;
		unlocked = false;
		offended = false;
		debt = 1500;
		askedForMoney = false;
		paidBack = false;
	}
	
	public void StartSim() {
		cueLore(p);
		String action = "onwards!";
		PopulateField();
		boolean pchanged = true;
		while (!action.equals("stop")){
			if(pchanged) {
				System.out.println();
				System.out.println(colorize("Type what you'd like to do!", TEXT_COLOR(159)));
				drawField();
				System.out.println();
				System.out.println(colorize("'shop' to go to the shop", TEXT_COLOR(230)));
				System.out.println(colorize("'plant' to plant something", TEXT_COLOR(230)));
				System.out.println(colorize("'sleep' to go to bed", TEXT_COLOR(230)));
				System.out.println(colorize("'harvest' to harvest a crop!", TEXT_COLOR(230)));
				System.out.println(colorize("'examine' to examine a spot!", TEXT_COLOR(230)));
				System.out.println(colorize("'pay back' to pay back your loan!", TEXT_COLOR(230)));
				System.out.println(colorize("'ask for loan' to get some money...", TEXT_COLOR(230)));
				System.out.println();
			}
			pchanged = false;
			action = p.nextLine();
			if(action.equals("sleep")) {
				GoToBed();
				pchanged = true;
			}else if(action.equals("shop")) {
				OpenShop();
				pchanged = true;
			}else if(action.equals("plant")){
				PlantSeeds();
				pchanged = true;
			}else if(action.equals("harvest")) {
				if(checkIfCanHarvest()) {
					int row = checkRow(p, action, 0, "harvest");
					int col = checkCol(p, action, 0, "harvest");
					harvest(row-1, col-1);
				} else {
					System.out.println(colorize("You don't have any crops ready for harvest!", TEXT_COLOR(203)));
				}
				pchanged = true;
			} else if(action.equals("examine")) {
				int row = checkRow(p, action, 0, "examine");
				int col = checkCol(p, action, 0, "examine");
				examine(row-1, col-1);
				pchanged = true;
			} else if(action.equals("pay back")) {
				tryToPay(p);
				pchanged = true;
			} else if(action.equals("ask for loan")) {
				askForLoan(p);
				pchanged = true;
			}
		}
	}
	
	public void PlantSeeds() {
		if(inv.size() == 0) {
			System.out.println("you have nothing to plant!");
			return;
		}
		System.out.println(colorize("What would you like to plant? Please type a number.", TEXT_COLOR(159)));
		int count = 1;
		for(Plant p : inv) {
			System.out.print(colorize(count + ") " + p.getType() + " ", TEXT_COLOR(230)));
			count++;
		}
		System.out.println();
		boolean validAns = false;
		String answers = p.next();
		int plantingLoc = 0;
		while(!validAns) {
			try {
				plantingLoc = Integer.parseInt(answers);
				if(plantingLoc <= inv.size() && plantingLoc > 0) {
					validAns = true;
				} else {
					System.out.println(colorize("Please type a valid number.", TEXT_COLOR(203)));
					answers = p.nextLine();
				}
			} catch (NumberFormatException e) {
				System.out.println(colorize("Please type a number.", TEXT_COLOR(203)));
				answers = p.nextLine();
			}
		}
		Plant planting = inv.remove(plantingLoc-1);
		int rowNum = 0;
		int spaceNum = 0;
		rowNum = checkRow(p, answers, rowNum, "plant");
		spaceNum = checkCol(p, answers, spaceNum, "plant");
		if(checkOverride(p, rowNum, spaceNum)) {
			field[rowNum-1][spaceNum-1] = planting;
			System.out.println(colorize("Planted!", TEXT_COLOR(159)));
		} else {
			System.out.println(colorize("Nothing planted!", TEXT_COLOR(159)));
			inv.add(planting);
		}
	}
	
	public int checkRow(Scanner p, String answers, int rowNum, String action) {
		boolean rowLegit = false;
		System.out.println(colorize("What row would you like to " + action + " in?", TEXT_COLOR(159)));
		while(!rowLegit) {
			try {
				answers = p.next();
				rowNum = Integer.parseInt(answers);
				if(unlocked && rowNum==2) rowLegit = true;
				if(rowNum == 1) rowLegit = true;
				if((rowNum == 2) && !unlocked) {
					System.out.println(colorize("You haven't unlocked the second row yet!", TEXT_COLOR(203)));
				}
				if((rowNum <1) || (rowNum > 2)) {
					System.out.println(colorize("Please enter a valid row number.", TEXT_COLOR(159)));
				}
			} catch (NumberFormatException e) {
				System.out.println(colorize("Please type a number.", TEXT_COLOR(203)));
				answers = p.nextLine();
			}
		}
		return rowNum;
	}
	
	public int checkCol(Scanner p, String answers, int spaceNum, String action) {
		boolean colLegit = false;
		System.out.println(colorize("What space would you like to " + action + " on?", TEXT_COLOR(159)));
		while(!colLegit) {
			try {
				answers = p.next();
				spaceNum = Integer.parseInt(answers);
				if((spaceNum >= 1) && (spaceNum <= 9)) {
					colLegit = true;
				} else {
					System.out.println(colorize("Please enter a valid space number.", TEXT_COLOR(203)));
				}
			} catch(NumberFormatException e) {
				System.out.println(colorize("Please type a number.", TEXT_COLOR(203)));
				answers = p.nextLine();
			}
		}
		return spaceNum;
	}
	
	public void drawField() {
		int numRows = 1;
		if(unlocked) numRows++;
		for(int i = 0; i < numRows; i++) {
			for(int p = 0; p < 9; p++) {
				field[i][p].show();
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public boolean checkLegit(int row, int col) {
		if(row == 2 && !unlocked) {
			System.out.println(colorize("You haven't unlocked that row yet!", TEXT_COLOR(203)));
			return false;
		}
		if(row <= 1 && col <= 8) {
			if(field[row][col].getType().compareTo("dirt") == 0) {
				return true;
			}
			System.out.println(colorize("You can't plant there!", TEXT_COLOR(203)));
			return false;
		}
		System.out.println(colorize("Please type a valid space!", TEXT_COLOR(203)));
		return false;
	}
	
	public void PopulateField() {
		for(int i = 0; i < 2; i++) {
			for(int p = 0; p < 9; p++) {
				field[i][p] = new Dirt();
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public int OpenShop() {
		System.out.println();
		System.out.println(colorize("Welcome to the shop! You have " + coins + " gold. "
				+ "Type what you want to buy, or 'leave' to leave shop.", TEXT_COLOR(159)));
		System.out.println();
		System.out.println(colorize("carrot seeds: 5c", TEXT_COLOR(230)));
		System.out.println(colorize("cabbage seeds: 10c", TEXT_COLOR(230)));
		System.out.println(colorize("tomato seeds: 20c", TEXT_COLOR(230)));
		System.out.println(colorize("mushroom log: 80c", TEXT_COLOR(230)));
		System.out.println(colorize("banana sapling: 150c", TEXT_COLOR(230)));
		if(!unlocked) System.out.println(colorize("new row: 100c", TEXT_COLOR(230)));
		String purchase = "";
		System.out.println(purchase);
		while (!purchase.equals("leave")) {
			purchase = p.nextLine();
			makePurchase(purchase);
		}
		System.out.println();
		return coins;
	}
	
	public void makePurchase(String purchase) {
		if(purchase.equals("carrot seeds")) {
			if(coins >= 5) {
				inv.add(new Carrot());
				coins -= 5;
				System.out.println(colorize("carrot seeds purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			} else {
				System.out.println(colorize("you cannot afford carrot seeds!", TEXT_COLOR(203)));
			}
		} else if(purchase.equals("cabbage seeds")) {
			if(coins >= 10) {
				inv.add(new Cabbage());
				coins -= 10;
				System.out.println(colorize("cabbage seeds purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			} else {
				System.out.println(colorize("you cannot afford cabbage seeds!", TEXT_COLOR(203)));
			}
		} else if(purchase.equals("tomato seeds")) {
			if(coins >= 20) {
				inv.add(new Tomato());
				coins -= 20;
				System.out.println(colorize("tomato seeds purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			} else {
				System.out.println(colorize("you cannot afford tomato seeds!", TEXT_COLOR(203)));
			}
		} else if(purchase.equals("mushroom log")) {
			if(coins >= 80) {
				inv.add(new Mushroom());
				coins -= 80;
				System.out.println(colorize("mushroom log purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			} else {
				System.out.println(colorize("you cannot afford mushroom log!", TEXT_COLOR(203)));
			}
		} else if(purchase.equals("banana sapling")) {
			if(coins >= 150) {
				inv.add(new Banana());
				coins -= 150;
				System.out.println(colorize("banana sapling purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			} else {
				System.out.println(colorize("you cannot afford banana sapling!", TEXT_COLOR(203)));
			}
		} else if(!unlocked && purchase.equals("new row")) {
			if(coins >= 100) {
				coins -=100;
				unlocked = true;
				System.out.println(colorize("new row purchased! Coins remaining: " + coins, TEXT_COLOR(230)));
			}else {
				System.out.println(colorize("you cannot afford a new row!", TEXT_COLOR(203)));
			}
		}
	}
	
	public Boolean GoToBed() {
		System.out.println(colorize("Good night!", TEXT_COLOR(159)));
		for(int i = 0; i < 2; i++) {
			for(int p = 0; p < 9; p++) {
				field[i][p].grow();
			}
		}
		spread();
		return true;
	}
	
	public void spread() {
		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 9; col++) {
				if(field[row][col].getType().equals("mushroom") && field[row][col].getStage() >= 8) {
					if(row == 0) {
						if(!field[1][col].getType().equals("mushroom") && unlocked) field[1][col] = new Mushroom();
					}else {
						if(!field[0][col].getType().equals("mushroom")) field[0][col] = new Mushroom();
					}
					if(col != 0 && !field[row][col-1].getType().equals("mushroom")) field[row][col-1] = new Mushroom();
					if(col != 8 && !field[row][col+1].getType().equals("mushroom")) field[row][col+1] = new Mushroom();
				}
			}
		}
	}

	public void harvest(int row, int col) {
		int harvestPrice = 0;
		if(field[row][col].getStage() >= 4) {
			if(!field[row][col].getType().equals("dirt")) {
				harvestPrice = field[row][col].getCost();
				System.out.println(colorize(field[row][col].getType() + " successfully harvested! You earned " + harvestPrice + " coins!", TEXT_COLOR(159)));
				if(!field[row][col].getBush()) {
					field[row][col] = new Dirt();
				} else {
					field[row][col].grow();
				}
			} else {
				System.out.println(colorize("Whoops, you can't harvest dirt!", TEXT_COLOR(203)));
			}
		} else {
			System.out.println(colorize("Whoops, that plant isn't ready for harvest yet!", TEXT_COLOR(203)));
		}
		coins += harvestPrice;
	}
	
	public boolean checkIfCanHarvest() {
		boolean can = false;
		for(int i = 0; i < 2; i++) {
			for(int c = 0; c < 9; c++) {
				if(field[i][c].canHarvest()) can = true;
			}
		}
		return can;
	}
	
	public void examine(int row, int col) {
		System.out.println(colorize(field[row][col].examine(), TEXT_COLOR(225)));;
	}
	
	public void cueLore(Scanner p) {
		System.out.println(colorize("Welcome to Ascii FS, " + VERSION + "! (type anything to continue)", TEXT_COLOR(159)));
		p.nextLine();
		System.out.println(colorize("This isn't a very serious game.", TEXT_COLOR(225)));;
		p.nextLine();
		System.out.println(colorize("...", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("In this game, you are poor.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("You have no money. Shockingly realistic, isn't it.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("All you have is a little bit of land.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("Ok, and a little bit of money. From me. To get you started.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("You're welcome.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("You have to pay me back, though.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("I need to make money somehow, too, you know.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("Listen, just take these coins. There's a shop down the road.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("Let me know when you've earned 1,500 coins.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("Don't make that face at me. It's called interest.", TEXT_COLOR(225)));
		p.nextLine();
		System.out.println(colorize("Go!", TEXT_COLOR(225)));
		p.nextLine();
	}
	
	public String tryToPay(Scanner p) {
		if(!paidBack) {
			if(coins >= debt) {
				System.out.println(colorize("Oh, you actually did it.", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("Thanks, I guess...", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("I'm going to be honest, I just wanted you to go away for a while.", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("I'll still take the money, though!", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("You can go back to watever you were doing before this, I guess...", TEXT_COLOR(225)));
				p.nextLine();
				coins -= debt;
				debt = 0;
				paidBack = true;
				return "stop";
			} else if(!offended){
				System.out.println(colorize("Is this some kind of joke?", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("You don't have " + debt + " coins.", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("Do you think I'm stupid?", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("I'm taking this money, so you can learn to count from zero.", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("I'll give you this carrot so you dont starve in the meantime.", TEXT_COLOR(225)));
				p.nextLine();
				inv.add(new Carrot());
				coins = 0;
			} else {
				System.out.println(colorize("Are you serious?", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("Again?", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("Did you not learn your lesson last time?", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("Clearly, you need more time to learn basic numbers.", TEXT_COLOR(225)));
				p.nextLine();
				System.out.println(colorize("I'm going to go jump on your crops now.", TEXT_COLOR(225)));
				p.nextLine();
				PopulateField();
				coins = 5;
			}
			offended = true;
			return "no";
		} else {
			System.out.println(colorize("You already paid me back...", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("I mean- good, you have the money!", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("Thanks.", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("See ya!", TEXT_COLOR(225)));
			p.nextLine();
			coins = 5;
			return "oop";
		}
		
	}
	
	public void askForLoan(Scanner p) {
		if(!askedForMoney) {
			System.out.println(colorize("Did you run out of money?", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("HAHAHAHAHAHHAHAHAH", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("But it's not really funny, is it.", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("That was my money.", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("How can you just throw it away like that?", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("I'll give you 10 more coins for now.", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("But you'll owe me another 1,000.", TEXT_COLOR(225)));
		} else {
			System.out.println(colorize("Really? Again?", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("Why do I even bother.", TEXT_COLOR(225)));
			p.nextLine();
			System.out.println(colorize("You know the drill. More money, more debt.", TEXT_COLOR(225)));
			p.nextLine();
		}
		coins += 10;
		debt += 1000;
		askedForMoney = true;
		if(debt > 0) paidBack = false;
		System.out.println();
		System.out.println(colorize("You now owe " + debt + ".", TEXT_COLOR(159)));
		System.out.println();
	}
	
	public Boolean checkOverride(Scanner p, int row, int col) {
		String answer;
		if(field[row-1][col-1].getType().equals("dirt")) {
			return true;
		}else {
			System.out.println(colorize("You already have something growing there! Do you want to plant over it? (type 'yes' or 'no')", TEXT_COLOR(159)));
			answer = p.nextLine();
			answer = p.nextLine();
		}
		Boolean done = false;
		while(!done) {
			if(answer.equals("yes")) {
				return true;
			}else if(answer.equals("no")){
				return false;
			}else {
				System.out.println(colorize("Please enter a valid answer.", TEXT_COLOR(203)));
				answer = p.nextLine();
			}
		}
		return false;
	}
}
