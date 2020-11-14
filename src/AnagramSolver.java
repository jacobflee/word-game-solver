public class AnagramSolver {
    final Letter root; // root of dictionary trie
    final char[] board;
    Word[] wordLengthStarts; // first element of array is the head of a linked list containing all words of length 3, next element of length 4, etc.

    public AnagramSolver(String filename, char[] board) {
        root = new Dictionary(filename).root;
        this.board = board;
        wordLengthStarts = new Word[board.length - 2];
        solve();
    }

    public void solve() {
        int[] indices = new int[board.length];
        for (int i = 0; i < indices.length; i++)
            indices[i] = i;
        Letter curL;
        do {
            curL = root;
            for (int i = 0; i < indices.length; i++) {
                if (curL.nextLetter[board[indices[i]] - 'a'] != null)
                    curL = curL.nextLetter[board[indices[i]] - 'a'];
                if (curL.wordEnd)
                    add(curL);
            }
        } while (nextPermutation(indices));
    }

    public boolean nextPermutation(int[] array) {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
            i--;

        if (i <= 0)
            return false;

        int j = array.length - 1;
        while (array[j] <= array[i - 1])
            j--;

        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
        return true;
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

    public void print(int stop) {
        boolean donePrinting = false;
        int start = board.length - 3;
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
