package vocabstudy;

/*
 * Asher Anand
 * Java controller for vocabstudy create set page
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreateSetController {
	//Class variable and UI object declaration
	@FXML
	private Label outputtxt; 
	
	@FXML
	private TextField inputtxt; 
	
	@FXML
	private Button stopbtn; 
	
	@FXML
	private Button okbtn; 
	
	private Scene mainScene; 
	
	private String setname; 
	
	private ArrayList<Word> words = new ArrayList<Word>(); 
	private String word; 
	private String definition; 
	private int pos; 
	private ArrayList<String> synonyms = new ArrayList<String>(); 
	private ArrayList<String> antonyms = new ArrayList<String>(); 
	private String sentence; 
	
	//Easier reference integers for parts of speech
	public int noun = 0; 
	public int pronoun = 1; 
	public int verb = 2; 
	public int adjective = 3; 
	public int adverb = 4; 
	public int preposition = 5; 
	public int conjunction = 6;  
	public int interjection = 7; 
	
	//Sets up UI and text for all attributes
	@FXML
	private void initialize() {
		createset(); 
	}
	
	//Injection of mainscene object
    public void setMainScene(Scene scene) {
        mainScene = scene;
    }
	
    //Begins set creation
	private void createset() {
		getsetname(); 
	}
	
	private void getsetname() {
		outputtxt.setText("Enter a name for the set");
		inputtxt.clear(); 
		stopbtn.setText("Exit");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setname = inputtxt.getText(); 
				getwords(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
    	        primaryStage.setScene(mainScene);
			}
		});
	}
	
	private void getwords() {
		outputtxt.setText("Enter words?"); 
		inputtxt.clear();
		stopbtn.setText("No"); 
		okbtn.setText("Yes"); 
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getword();   
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					saveset(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	
	private void getword() {
		outputtxt.setText("Enter the word");
		inputtxt.clear();
		stopbtn.setText("Exit");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				word = inputtxt.getText(); 
				getpos();  
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					saveset(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	
	private void getpos() {
		outputtxt.setText("Enter its part of speech");
		inputtxt.clear();
		stopbtn.setText("Exit");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (inputtxt.getText().toLowerCase()) {
				case "noun":
					pos = noun;
					break; 
				case "verb":
					pos = verb; 
					break; 
				case "adjective":
					pos = adjective; 
					break;
				case "adverb":
					pos = adverb; 
					break;
				case "preposition":
					pos = preposition; 
					break;
				case "interjection":
					pos = noun; 
					break;
				case "conjunction":
					pos = conjunction; 
					break;
				}
				getdefinition(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
    	        try {
					saveset(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	
	private void getdefinition() {
		outputtxt.setText("Enter the definition");
		inputtxt.clear();
		stopbtn.setText("Exit");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				definition = inputtxt.getText(); 
				getsynonyms(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					saveset(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	
	private void getsynonyms() {
		outputtxt.setText("Enter synonyms?");
		inputtxt.clear();
		stopbtn.setText("No");
		okbtn.setText("Yes");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getsynonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getantonyms(); 
			}
		});
	}
	
	private void getsynonym() {
		outputtxt.setText("Enter the synonym");
		inputtxt.clear();
		stopbtn.setText("Stop");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				synonyms.add(inputtxt.getText());
				getsynonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getantonyms(); 
			}
		});
	}
	
	private void getantonyms() {
		outputtxt.setText("Enter antonyms?");
		inputtxt.clear();
		stopbtn.setText("No");
		okbtn.setText("Yes");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getantonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getsentence(); 
			}
		});
	}
	
	private void getantonym() {
		outputtxt.setText("Enter the antonym");
		inputtxt.clear();
		stopbtn.setText("Stop");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				antonyms.add(inputtxt.getText()); 
				getantonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getsentence(); 
			}
		});
	}
	
	private void getsentence() {
		outputtxt.setText("Enter an example sentence");
		inputtxt.clear();
		stopbtn.setText("Exit");
		okbtn.setText("Enter");
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sentence = inputtxt.getText(); 
				addword(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					saveset(3);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	
	private void addword() {
		Word wordtoadd = new Word(word, definition, pos, synonyms, antonyms, sentence); 
		words.add(wordtoadd); 
		getword(); 
	}
	
	private void saveset(int stage) throws IOException {
		FileOutputStream fout = new FileOutputStream(setname + ".ser"); 
		ObjectOutputStream oos = new ObjectOutputStream(fout); 
		Word wordtoadd; 
		
		switch (stage) {
		case 0:
			oos.writeObject(words);
			break; 
		case 1:
			wordtoadd = new Word(word, definition, -1, null, null, null); 
			words.add(wordtoadd); 
			oos.writeObject(words);
			break;
		case 2: 
			wordtoadd = new Word(word, definition, pos, null, null, null);
			words.add(wordtoadd); 
			oos.writeObject(words);
			break;
		case 3: 
			wordtoadd = new Word(word, definition, -1, synonyms, antonyms, null); 
			words.add(wordtoadd); 
			oos.writeObject(words);
			break;
		}
		
		oos.close();
		
		outputtxt.setText("Set created. Click ok to return.");
		inputtxt.clear();
		okbtn.setText("OK"); 
		stopbtn.setVisible(false); 
		okbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.setScene(mainScene); 
			}	
		});
	}
}
