import java.util.ArrayList;
import java.util.Scanner;

public class test {

	public static void main(String[] args) {
		Scanner player = new Scanner(System.in);
		Plant[][] field = new Plant[2][9];
		ArrayList<Plant> inventory = new ArrayList<Plant>();
		int coins = 10;
		FarmingGame game = new FarmingGame(coins, field, inventory, player);
		game.StartSim();
		player.close();
		System.out.println("thanks for playing!");
	}
}
