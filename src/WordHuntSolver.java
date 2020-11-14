public class WordHuntSolver {
	final Letter root; // root of dictionary trie
	final char[][] board;
	final int[][] dirs; // eight possible directions
	Word[] wordLengthStarts; // first element of array is the head of a linked list containing all words of length 3, next element of length 4, etc.

	public WordHuntSolver(String filename, char[][] board) {
		root = new Dictionary(filename).root;
		this.board = board;
		dirs = new int[][] { { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
		wordLengthStarts = new Word[13];
		solve();
	}

	public void solve() {
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				analyzeAt(root, r, c, 0);
	}

	public void analyzeAt(Letter curL, int r, int c, int boardNum) {
		if (isValidChar(board[r][c]) && curL.nextLetter[board[r][c] - 'a'] != null) {
			curL = curL.nextLetter[board[r][c] - 'a'];
			boardNum += pow(2, 4 * r + c);
			for (int i = 0; i < 8; i++) {
				if (curL.wordEnd)
					add(curL);
				if (inBoundsAndSpotEmpty(r + dirs[i][0], c + dirs[i][1], boardNum))
					analyzeAt(curL, r + dirs[i][0], c + dirs[i][1], boardNum);
			}
		}
	}

	public void add(Letter curL) {
		StringBuilder s = new StringBuilder();
		int wordLength = 0;
		for (; curL != root; curL = curL.prev, wordLength++)
            s.append(curL.letter);
        s.reverse();

		Word newW = new Word(s.toString());
		if (wordLengthStarts[wordLength - 3] == null)
			wordLengthStarts[wordLength - 3] = newW;
		else {
			Word curW = wordLengthStarts[wordLength - 3];
			while (curW.next != null && !curW.word.equals(newW.word))
				curW = curW.next;
			if (!curW.word.equals(newW.word)) {
				curW = wordLengthStarts[wordLength - 3];
				curW.prev = newW;
				wordLengthStarts[wordLength - 3] = curW.prev;
				wordLengthStarts[wordLength - 3].next = curW;
			}
		}
	}

	public static boolean isValidChar(int c) {
		return c >= 97 && c <= 122;
	}

	public int pow(int base, int exp) {
		int pow = 1;
		for (int i = 0; i < exp; i++)
			pow *= base;

		return pow;
	}

	public boolean inBoundsAndSpotEmpty(int r, int c, int boardNum) {
		return r >= 0 && r <= 3 && c >= 0 && c <= 3 && boardNum / pow(2, 4 * r + c) % 2 == 0;
	}

	public void print(int stop) {
		boolean donePrinting = false;
		int start = 12;
		while (start >= 0 && wordLengthStarts[start] == null)
			start--;

		if (start == -1)
			System.out.println("NO OUTPUT");
		else {
			for (int i = start; i >= 0; i--)
				System.out.printf("%-" + (i + 3) + "d ", i + 3);
			System.out.println();

			int k = 0;
			while (!donePrinting && k++ < stop) {
				donePrinting = true;
				for (int i = start; i >= 0; i--)
					if (wordLengthStarts[i] != null) {
						donePrinting = false;
						System.out.printf("%s ", wordLengthStarts[i].word);
						wordLengthStarts[i] = wordLengthStarts[i].next;
					} else
						System.out.print(" ".repeat(i + 4));
				System.out.println();
			}
		}
	}

	public void print() {
		print(10);
	}

}
