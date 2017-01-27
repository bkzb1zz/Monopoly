import java.util.Random;
import java.util.Scanner;

public class Monopoly {

	private static int playersCount;
	private static String[] playerNames = new String[4];
	private static int[] playersCurrentPosition = { 0, 0, 0, 0 };
	// private static int[] diceThrown = new int[4];
	private static int[] playersMoney = { 0, 0, 0, 0 };
	private static boolean[] hasMoney = { false, false, false, false };
	private static boolean[] isInJail = { false, false, false, false };
	private static Scanner sc = new Scanner(System.in);
	private static Scanner sc2 = new Scanner(System.in);
	private static Scanner sc3 = new Scanner(System.in);
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
	private static int[] ownedProperties = { -3, -1, -2, -1, -200, -1, -1, -2, -1, -1, -2, -1, -1, -1, -1, -1, -1, -2,
			-1, -1, -2, -1, -2, -1, -1, -1, -1, -1, -1, -1, -2, -1, -1, -2, -1, -1, -2, -1, -100, -1 };

	public static void main(String[] args) {

		// to add
		// Jail
		// Cards
		// Sell properties
		// Update tax
		// Fix money updates - to show money after update
		StartGame();
		// System.out.println(board.length);
		// System.out.println(pricesAndTaxes.length);
		// System.out.println(ownedProperties.length);
	}

	private static void StartGame() {

		PlayersCount();
		PlayerNames();
		StartingMoney();
		while (hasMoney[0] || hasMoney[1] || hasMoney[2] || hasMoney[3]) {
			Engine();
		}
	}

	private static void StartingMoney() {
		for (int i = 0; i < playersCount; i++) {
			playersMoney[i] = 2000;
			hasMoney[i] = true;
		}
	}

	private static void Engine() {

		Random rd = new Random();

		for (int player = 0; player < playersCount; player++) {
			if (hasMoney[player] == true) {
				System.out.printf("\n%s press enter to throw the dice!\n", playerNames[player]);
				sc.nextLine();
				int moves = rd.nextInt(6) + 1;
				System.out.printf("You have thrown: %d!\n", moves);
				Move(player, moves);
				MoneyLending(player);
				ManageProperties(player);

			}
		}
	}

	private static void MoneyLending(int player) {
		int temp = ownedProperties[playersCurrentPosition[player]];

		if (temp != player && (temp <= 3 && temp >= 0)) {
			int tax = pricesAndTaxes[playersCurrentPosition[player]] / 5;
			System.out.printf("You are on %s which belongs to %s and you must pay %d$\n",
					board[playersCurrentPosition[player]], playerNames[ownedProperties[playersCurrentPosition[player]]],
					tax);
			playersMoney[player] = playersMoney[player] - tax;
			playersMoney[ownedProperties[playersCurrentPosition[player]]] += tax;
			if (playersMoney[player] <= 0) {
				hasMoney[player] = false;
			}
		}
	}

	private static void ManageProperties(int player) {
		int owned = ownedProperties[playersCurrentPosition[player]];
		System.out.printf("%s you have %d$!\n", playerNames[player], playersMoney[player]);

		if (pricesAndTaxes[playersCurrentPosition[player]] < -5) {
			System.out.printf("You are on %s!\n", board[playersCurrentPosition[player]]);
			playersMoney[player] = playersMoney[player] + pricesAndTaxes[playersCurrentPosition[player]];
			System.out.printf("%s you have %d$!\n\n", playerNames[player], playersMoney[player]);
			if (playersMoney[player] <= 0) {
				hasMoney[player] = false;
				System.out.printf("%s has bankrupt!\n\n", playerNames[player]);
			}
		} else if (owned == -1 && playersMoney[player] > pricesAndTaxes[playersCurrentPosition[player]]) {
			System.out.printf("You are on %s and it costs %d$.\n", board[playersCurrentPosition[player]],
					pricesAndTaxes[playersCurrentPosition[player]]);
			System.out.print("Would you like to buy this property? ");

			if (sc3.nextLine().equalsIgnoreCase("yes")) {
				ownedProperties[playersCurrentPosition[player]] = player;
				playersMoney[player] = playersMoney[player] - pricesAndTaxes[playersCurrentPosition[player]];
				System.out.println("Property bought!");
				System.out.printf("%s you have %d$!\n\n", playerNames[player], playersMoney[player]);
			} else {
				System.out.println("You have not bought this property!\n");
			}
		} else if ((owned == -2)) {
			// to implement
			System.out.println("Card!\n");
		} else if (owned == -3) {
			System.out.printf("You are on the %s.\n", board[playersCurrentPosition[player]]);
		} else if (owned != 0 && owned != 1 && owned != 2 && owned != 3) {
			System.out.printf("You are on %s, but you don't have enough money to buy this property!\n\n",
					board[playersCurrentPosition[player]]);
		}
		else if(ownedProperties[playersCurrentPosition[player]] == player){
			System.out.printf("You are on %s which belongs to you. Would you like to sell it for half it's price? ", board[playersCurrentPosition[player]]);
			if (sc3.nextLine().equalsIgnoreCase("yes")) {
				ownedProperties[playersCurrentPosition[player]] = -1;
				playersMoney[player] += pricesAndTaxes[playersCurrentPosition[player]] / 2;
				System.out.printf("You have sold %s for %d$\n\n", board[playersCurrentPosition[player]], pricesAndTaxes[playersCurrentPosition[player]] / 2);
			}
		}
		else {
			System.out.println();
		}
	}

	private static int Move(int player, int moves) {

		int temp = playersCurrentPosition[player] += moves;
		if (temp < 40) {
			return playersCurrentPosition[player];
		} else {
			System.out.println("You have passed the start and you get 200$");
			playersMoney[player] += 200;
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
