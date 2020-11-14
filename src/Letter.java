public class Letter {
	public char letter;
	public boolean wordEnd;
	public Letter prev;
	public Letter[] nextLetter;
	
	public Letter(char letter, boolean wordEnd, Letter prev) {
		this.letter = letter;
		this.wordEnd = wordEnd;
		this.prev = prev;
		nextLetter = new Letter[26];
	}

}
