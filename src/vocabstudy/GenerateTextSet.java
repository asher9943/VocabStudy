package vocabstudy;
/*
 * Asher Anand
 * Generates a test set for development of vocabstudy
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList; 
public class GenerateTextSet {

	public static void main(String[] args) {
		// Create test word
		ArrayList<Word> words = new ArrayList<Word>(); 
		ArrayList<String> synonyms = new ArrayList<String>(); 
		ArrayList<String> antonyms = new ArrayList<String>(); 
		String t = "test"; 
		int pos = 0; 
		synonyms.add(t); 
		antonyms.add(t); 
		
		Word word = new Word(t, t, pos, synonyms, antonyms, t); 
		
		words.add(word); 
		
		// Write test word to test file
		try {
			FileOutputStream fos = new FileOutputStream("test.ser"); 
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			oos.writeObject(words);
			oos.close();
			fos.close();
		}	catch (IOException i) {
			i.printStackTrace();
		}
	}

}
