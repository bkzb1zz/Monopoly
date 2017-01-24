import java.util.Random;
import java.util.Scanner;

public class Monopoly {

	private static int playersCount;
	private static String[] playerNames = new String[4];
	private static int[] playersCurrentPosition = { 0, 0, 0, 0 };
	private static int[] diceThrown = new int[4];
	private static int[] playersMoney = {1000, 1000, 1000, 1000};
	private static Scanner sc = new Scanner(System.in);
	private static Scanner sc2 = new Scanner(System.in);
	private static String[] board = { "Beginning", "Mediterranean Avenue", "Community Chest", "Baltic Avenue",
			"Incoming tax, you must pay 200$", "Reading Railrod", "Oriental Avenue", "CHANCE", "Vermont Avenue",
			"Connecticut Avenue", "IN JAIL!", "St. Charles Place", "Electric Company", "States Avenue",
			"Virginia Avenue", "Pennsylvania Railroad", "St. James Place", "Community Chest", "Tennessee Avenue",
			"New York Avenue", "FREE PARKING", "Kentucky Avenue", "CHANCE", "Indiana Avenue", "Illinois Avenue",
			"B. & O. Railroad", "Aplantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens", "IN JAIL",
			"Pacific Avenue", "North Carolina Avenue", "Community Chest", "Pennsylvania Avenue", "Short Line", "CHANCE",
			"Park Place", "Luxury Tax, you must pay 100$", "Boardwalk" };
	private static int[] pricesAndTaxes = { 200, 60, -1, 60, -200, 200, 100, -1, 100, 120, -1, 140, 150, 140, 160, 200,
			180, -1, 180, 200, -1, 220, -1, 220, 240, 200, 260, 260, 150, 280, -1, 300, 300, -1, 320, 200, -1, 350,
			-100, 400 };

	public static void main(String[] args) {

		StartGame();
		// System.out.println(board.length);
		// System.out.println(pricesAndTaxes.length);
	}

	private static void StartGame() {
		PlayersCount();
		PlayerNames();
		while (true) {

			ThrowDice();
			//there are still money or players on the field
		}

	}

	private static void ThrowDice() {

		Random rd = new Random();

		for (int player = 0; player < playersCount; player++) {
			System.out.printf("%s press enter to throw the dice!\n", playerNames[player]);
			sc.nextLine();
			int moves = rd.nextInt(6) + 1;
			System.out.printf("You have thrown: %d!\n", moves);
			// diceThrown[i] = n;
			// method for the board
			Move(player, moves);
			System.out.printf("%s you are on %s and it costs %d\n\n", playerNames[player], board[playersCurrentPosition[player]], pricesAndTaxes[playersCurrentPosition[player]]);
		}
	}

	private static int Move(int player, int moves) {
		int temp = playersCurrentPosition[player] += moves;
		if (temp < 40) {
			return playersCurrentPosition[player];
		} else {
			return playersCurrentPosition[player] = playersCurrentPosition[player] - 40;
		}
	}

	private static void PlayerNames() {

		for (int i = 0; i < playersCount; i++) {
			System.out.printf("Enter player N:%d name: ", i + 1);
			playerNames[i] = sc.nextLine();
			if (CorrectNameInput(playerNames[i])) {
				continue;
			} else {
				System.out.println("Incorrect name has been inputted, please try again");
				i--;
			}
		}
		System.out.println();
	}

	private static boolean CorrectNameInput(String name) {

		String regex = "^[a-zA-Z._-]{2,}$";

		if (name.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	private static void PlayersCount() {

		boolean correctInput = true;

		while (correctInput) {
			System.out.print("Enter number of players between 1 and 4: ");
			playersCount = sc2.nextInt();
			if (playersCount >= 1 && playersCount <= 4) {
				correctInput = false;
			} else {
				correctInput = true;
			}
		}
	}
}
