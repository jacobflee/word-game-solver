import java.io.IOException;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) throws IOException {
		// wordHuntInput();
		// anagramRandom();
		wordHuntRandom();
	}

	public static void anagram(String line) {
		new AnagramSolver("Collins Scrabble Words (2019).txt", line.toCharArray()).print();
	}

	public static void anagramRandom() {
		char[] board = new char[6];

		for (int c = 0; c < 4; c++)
			board[c] = (char) ((int) (26 * Math.random() + 97));

		new AnagramSolver("Collins Scrabble Words (2019).txt", board).print();
	}

	public static void anagramInput() {
		Scanner sc = new Scanner(System.in);

		String line = sc.nextLine();
		sc.close();
		System.out.println();

		new AnagramSolver("Collins Scrabble Words (2019).txt", line.toCharArray()).print();
	}

	public static void wordHunt(String line) {
		char[][] board = new char[4][4];

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				board[r][c] = line.charAt(4 * r + c);

		new WordHuntSolver("Collins Scrabble Words (2019).txt", board).print();
	}

	public static void wordHuntRandom() {
		char[][] board = new char[4][4];

		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				board[r][c] = (char) ((int) (26 * Math.random() + 97));

		new WordHuntSolver("Collins Scrabble Words (2019).txt", board).print();
	}

	public static void wordHuntInput() {
		char[][] board = new char[4][4];
		Scanner sc = new Scanner(System.in);

		String line;
		for (int r = 0; r < 4; r++) {
			line = sc.nextLine();
			for (int c = 0; c < 4; c++)
				board[r][c] = line.charAt(c);
		}
		sc.close();
		System.out.println();

		new WordHuntSolver("Collins Scrabble Words (2019).txt", board).print();
	}

}
