import java.util.Random;
import java.util.Scanner;

public class Monopoly {

	private static int playersCount;
	private static String[] playerNames = new String[4];
	private static int[] playersCurrentPosition = { 0, 0, 0, 0 };
	private static int[] playersMoney = { 0, 0, 0, 0 };
	private static boolean[] hasMoney = { false, false, false, false };
	private static boolean[] isInJail = { false, false, false, false };
	private static int[] outOfJailCard = { 0, 0, 0, 0 };
	private static Scanner sc = new Scanner(System.in);
	private static Scanner sc2 = new Scanner(System.in);
	private static Scanner sc3 = new Scanner(System.in);
	private static String[] board = { "Beginning", "Mediterranean Avenue", "Community Chest", "Baltic Avenue",
			"Incoming tax, you must pay 200$", "Reading Railrod", "Oriental Avenue", "CHANCE", "Vermont Avenue",
			"Connecticut Avenue", "IN JAIL!", "St. Charles Place", "Electric Company", "States Avenue",
			"Virginia Avenue", "Pennsylvania Railroad", "St. James Place", "Community Chest", "Tennessee Avenue",
			"New York Avenue", "FREE PARKING", "Kentucky Avenue", "CHANCE", "Indiana Avenue", "Illinois Avenue",
			"B. & O. Railroad", "Aplantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens", "IN JAIL!",
			"Pacific Avenue", "North Carolina Avenue", "Community Chest", "Pennsylvania Avenue", "Short Line", "CHANCE",
			"Park Place", "Luxury Tax, you must pay 100$", "Boardwalk" };
	private static int[] pricesAndTaxes = { 200, 60, -1, 60, -200, 200, 100, -1, 100, 120, -1, 140, 150, 140, 160, 200,
			180, -1, 180, 200, -1, 220, -1, 220, 240, 200, 260, 260, 150, 280, -1, 300, 300, -1, 320, 200, -1, 350,
			-100, 400 };
	private static int[] ownedProperties = { -3, -1, -2, -1, -200, -1, -1, -2, -1, -1, -4, -1, -1, -1, -1, -1, -1, -2,
			-1, -1, -2, -1, -2, -1, -1, -1, -1, -1, -1, -1, -4, -1, -1, -2, -1, -1, -2, -1, -100, -1 };

	public static void main(String[] args) {

		// to add
		// Cards

		StartGame();

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

			playersMoney[i] = 1000;
			hasMoney[i] = true;

		}
	}

	private static void Engine() {

		Random rd = new Random();

		for (int player = 0; player < playersCount; player++) {

			if (hasMoney[player] == true) {

				System.out.printf("\n%s press enter to throw the dice!", playerNames[player]);
				sc.nextLine();
				int moves = rd.nextInt(6) + 1;
				System.out.printf("You have thrown: %d!\n", moves);
				System.out.printf("%s you have %d$!\n", playerNames[player], playersMoney[player]);
				if (isInJail[player] == true && moves == 6) {

					System.out.println("Congratulations, you get out of JAIL, because you rolled 6");
					isInJail[player] = false;

				}

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
			System.out.printf("%s you have %d$!\n\n", playerNames[player], playersMoney[player]);

			if (playersMoney[player] <= 0) {

				hasMoney[player] = false;

			}
		}
	}

	private static void ManageProperties(int player) {

		IsInJail(player);

		if (isInJail[player] == false) {

			if (pricesAndTaxes[playersCurrentPosition[player]] < -5) {

				System.out.printf("You are on %s!\n", board[playersCurrentPosition[player]]);
				playersMoney[player] = playersMoney[player] + pricesAndTaxes[playersCurrentPosition[player]];
				System.out.printf("%s you have %d$!\n\n", playerNames[player], playersMoney[player]);

				if (playersMoney[player] <= 0) {

					hasMoney[player] = false;
					System.out.printf("%s has bankrupt!\n\n", playerNames[player]);
					int save = SaveMe(player);

					if (save + playersMoney[player] > 0) {

						hasMoney[player] = true;
						playersMoney[player] += save;
						System.out.printf("%s's properties have been sold to the bank. He recieves half their price!",
								playerNames[player]);

					}

				}

			} else if (ownedProperties[playersCurrentPosition[player]] == -1
					&& playersMoney[player] > pricesAndTaxes[playersCurrentPosition[player]]) {

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

			} else if ((ownedProperties[playersCurrentPosition[player]] == -2)) {

				Random rd = new Random();
				System.out.print("Card!\n");
				int card = rd.nextInt(5) + 1;
				
				switch (card) {
				
				case 1:
					outOfJailCard[player] += 1;
					System.out.println("You have won OUT OF JAIL CARD!\n");
					break;
					
				case 2:
					playersMoney[player] += 100;
					System.out.println("You have won 100$!\n");
					break;
					
				case 3:
					playersMoney[player] += 200;
					System.out.println("You have won 200$!\n");
					break;
					
				case 4:
					playersMoney[player] += 200;
					playersCurrentPosition[player] = 0;
					System.out.printf("You go to the %s and you get 200$\n\n", board[0]);
					System.out.printf("%s you have %d$!", playerNames[player], playersMoney[player]);
					break;
					
				case 5:
					playersCurrentPosition[player] = 10;
					System.out.println("You go to JAIL\n!");
					break;
				default:
					break;
				}
				
				

			} else if (ownedProperties[playersCurrentPosition[player]] == -3) {

				System.out.printf("You are on the %s.\n\n", board[playersCurrentPosition[player]]);

			} else if (ownedProperties[playersCurrentPosition[player]] != 0
					&& ownedProperties[playersCurrentPosition[player]] != 1
					&& ownedProperties[playersCurrentPosition[player]] != 2
					&& ownedProperties[playersCurrentPosition[player]] != 3
					&& ownedProperties[playersCurrentPosition[player]] != -4) {

				System.out.printf("You are on %s, but you don't have enough money to buy this property!\n\n",
						board[playersCurrentPosition[player]]);

			} else if (ownedProperties[playersCurrentPosition[player]] == player) {

				System.out.printf(
						"You are on %s which belongs to you. Would you like to sell it for half it's price, which is %d$? ",
						board[playersCurrentPosition[player]], pricesAndTaxes[playersCurrentPosition[player]] / 2);

				if (sc3.nextLine().equalsIgnoreCase("yes")) {

					ownedProperties[playersCurrentPosition[player]] = -1;
					playersMoney[player] += pricesAndTaxes[playersCurrentPosition[player]] / 2;
					System.out.printf("%s you have %d$!\n", playerNames[player], playersMoney[player]);
					System.out.printf("You have sold %s for %d$\n\n", board[playersCurrentPosition[player]],
							pricesAndTaxes[playersCurrentPosition[player]] / 2);

				} else {

					System.out.println();

				}
			}
		}
	}

	private static int SaveMe(int player) {

		int sum = 0;

		for (int i = 0; i < ownedProperties.length; i++) {

			if (ownedProperties[i] == player) {

				sum = sum + (pricesAndTaxes[i] / 2);
				ownedProperties[i] = -1;

			}

		}
		return sum;

	}

	private static void IsInJail(int player) {

		if (ownedProperties[playersCurrentPosition[player]] == -4) {

			isInJail[player] = true;
			System.out.printf("%s you are in JAIL, in order to escape you must roll 6 on your next turn!\n\n",
					playerNames[player]);

		}
	}

	private static int Move(int player, int moves) {

		int temp;

		if (isInJail[player] == false) {
			temp = playersCurrentPosition[player] += moves;
		} else {
			temp = playersCurrentPosition[player] += 0;
		}

		if (temp < 40) {

			return playersCurrentPosition[player];

		} else {

			System.out.println("You have passed the start and you get 200$");
			playersMoney[player] += 200;
			System.out.printf("%s you have %d$!\n", playerNames[player], playersMoney[player]);
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
