package vocabstudy;

/*
 * Asher Anand
 * Word class for vocabstudy application
 */


import java.io.Serializable;
import java.util.ArrayList; 
public class Word implements Serializable{
	
	//Class variable declaration
	private static final long serialVersionUID = 1L;
	public String word; 
	public String definition; 
	public int pos; 
	public ArrayList<String> synonyms = new ArrayList<String>(); 
	public ArrayList<String> antonyms = new ArrayList<String>(); 
	public String sentence; 
	
	//Easier refrence integers for parts of speech
	public int noun = 0; 
	public int pronoun = 1; 
	public int verb = 2; 
	public int adjective = 3; 
	public int adverb = 4; 
	public int preposition = 5; 
	public int conjunction = 6;  
	public int interjection = 7; 
	
	//Default constructor for class
	public Word(String word, String definition, int pos, ArrayList<String> synonyms, ArrayList<String> antonyms, String sentence) {
		this.word = word; 
		this.definition = definition; 
		this.pos = pos; 
		this.synonyms = synonyms; 
		this.antonyms = antonyms; 
		this.sentence = sentence; 
	}
}
