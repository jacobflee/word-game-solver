import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
	public Letter root;

	public Dictionary(String filename) {
		root = new Letter(' ', false, null);
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String word;
			while ((word = br.readLine()) != null)
				if (word.length() >= 3) // minimum word length
					add(word);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(String word) {
		Letter cur = root;
    	for (int i = 0; i < word.length(); i++) {
    		char c = word.charAt(i);
    		if (cur.nextLetter[c - 'a'] == null)
    			cur.nextLetter[c - 'a'] = new Letter(c, i == word.length() - 1, cur);
    		cur = cur.nextLetter[c - 'a'];
    	}
	}
	
	public boolean isValidWord(String word) {
		Letter cur = root;
		for (int i = 0; i < word.length(); i++)
			if (cur.nextLetter[word.charAt(i) - 'a'] != null)
				cur = cur.nextLetter[word.charAt(i) - 'a'];
			else
				return false;
		return cur.wordEnd;
	}

}
