package vocabstudy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * Asher Anand
 * Edit set controller class for vocabstudy
 */


public class EditSetController {
	@FXML
	private TextArea outputtxt; 
	
	@FXML
	private TextField inputtxt; 
	
	@FXML
	private Spinner<String> inputspinner; 
	
	@FXML
	private Button stopbtn; 
	
	@FXML 
	private Button enterbtn; 
	
	private String word, sentence, definition;
	private ArrayList<String> synonyms = new ArrayList<String>(); 
	private ArrayList<String> antonyms = new ArrayList<String>(); 
	private int pos;
	private boolean cont;  
	
	private String wordtoedit; 
	
	//Easier reference integers for parts of speech
	public int noun = 0; 
	public int pronoun = 1; 
	public int verb = 2; 
	public int adjective = 3; 
	public int adverb = 4; 
	public int preposition = 5; 
	public int conjunction = 6;  
	public int interjection = 7; 
	
	File dir = new File("D:\\Files\\Java Projects\\VocabsStudy"); 
	ObservableList<String> sets = FXCollections.observableArrayList();
	String set; 
	ArrayList<Word> words = new ArrayList<Word>(); 
	
	@FXML
	private void initialize() {
		editset(); 
	}
	
	private void editset() {
		getset(); 
	}
	
	@SuppressWarnings("unchecked")
	private void getset() {
		outputtxt.setText("Select the set to edit");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
		
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".ser") && file.getName() != null) {
				sets.add(file.getName().replaceAll(".ser", ""));
			}
		}	
		
		if (sets.isEmpty()) {
			outputtxt.setText("No sets found. Press exit to exit");
		}
		
		else {
			SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(sets); 		
			inputValueFactory.setValue(sets.get(0)); 
			inputspinner.setValueFactory(inputValueFactory);
			enterbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					set = (String) inputspinner.getValue() + ".ser"; 
					FileInputStream fis = null;
					ObjectInputStream ois = null; 
					try{
						fis = new FileInputStream(set); 
						ois = new ObjectInputStream(fis);
						
						words = (ArrayList<Word>) ois.readObject(); 
						ois.close();
						fis.close();
						
						if (words.isEmpty()) {
							outputtxt.setText("Set is empty");
						}
						
						else {
							getedit();
						}
										
					}
					catch(IOException e) {
					   e.printStackTrace();
					}
					catch(ClassNotFoundException c) {
						c.printStackTrace();
					}
				}
			});
		}
	}
	
	private void getedit() {
		outputtxt.setText("What do you want to change?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit"); 
		ObservableList<String> choices =  FXCollections.observableArrayList();
		choices.add("Add word"); 
		choices.add("Edit word"); 
		choices.add("Delete set"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String choice = (String) inputspinner.getValue(); 
				if (choice.equals("Add word")) {
					getwords(); 
				}
				else if (choice.equals("Edit word")) {
					chooseword(); 
				}
				else if (choice.equals("Delete set")) {
					deleteset(); 
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void getwords() {
		outputtxt.setText("Enter words?"); 
		inputtxt.clear();
		stopbtn.setText("No"); 
		enterbtn.setText("Yes"); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getword();   
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveset(); 
			}
		});
	}
	
	private void getword() {
		outputtxt.setText("Enter the word");
		inputtxt.clear();
		stopbtn.setText("Exit");
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				word = inputtxt.getText(); 
				getpos();  
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveset(); 
			}
		});
	}
	
	private void getpos() {
		outputtxt.setText("Enter its part of speech");
		inputtxt.clear();
		stopbtn.setText("Exit");
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
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
    	        addword(0); 
			}
		});
	}
	
	private void getdefinition() {
		outputtxt.setText("Enter the definition");
		inputtxt.clear();
		stopbtn.setText("Exit");
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				definition = inputtxt.getText(); 
				getsynonyms(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addword(1); 
			}
		});
	}
	
	private void getsynonyms() {
		outputtxt.setText("Enter synonyms?");
		inputtxt.clear();
		stopbtn.setText("No");
		enterbtn.setText("Yes");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getsynonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				synonyms = null;
				getantonyms(); 
			}
		});
	}
	
	private void getsynonym() {
		outputtxt.setText("Enter the synonym");
		inputtxt.clear();
		stopbtn.setText("Stop");
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
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
		enterbtn.setText("Yes");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getantonym(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				antonyms = null; 
				getsentence(); 
			}
		});
	}
	
	private void getantonym() {
		outputtxt.setText("Enter the antonym");
		inputtxt.clear();
		stopbtn.setText("Stop");
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
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
		enterbtn.setText("Enter");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sentence = inputtxt.getText(); 
				addword(2); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addword(3); 
			}
		});
	}
	
	private void addword(int stage) {
		Word wordtoadd = null; 
		switch (stage) {
		case 0: 
			wordtoadd = new Word(word, null, -1, null, null, null); 
			break; 
		case 1: 
			wordtoadd = new Word(word, null, pos, null, null, null); 
			break;
		case 2: 
			wordtoadd = new Word(word, definition, pos, synonyms, antonyms, sentence); 
			break; 
		case 3:
			wordtoadd = new Word(word, definition, pos, synonyms, antonyms, null); 
			break; 
		}
		words.add(wordtoadd); 
		getwords(); 	
	}
	
	private void chooseword() {
		outputtxt.setText("Choose the word to edit");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices =  FXCollections.observableArrayList();
		for (Word word : words) {
			choices.add(word.word);
		}
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				wordtoedit = (String) inputspinner.getValue(); 
				choosewordedit(); 
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
		
	}
	
	private void choosewordedit() {
		outputtxt.setText("Choose what about the word to edit");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices =  FXCollections.observableArrayList();
		choices.add("Word"); 
		choices.add("Definition"); 
		choices.add("Part of Speech");
		choices.add("Synonyms"); 
		choices.add("Antonyms"); 
		choices.add("Example Sentence"); 
		choices.add("Delete Word"); 
		
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String choice = (String) inputspinner.getValue(); 
				switch (choice) {
				case "Word":
					editword(); 
					break; 
				case "Definition":
					editdefinition(); 
					break; 
				case "Part of Speech":
					editpos(); 
					break; 
				case "Synonyms":
					editsynonyms(); 
					break; 
				case "Antonyms":
					editantonyms(); 
					break; 
				case "Example Sentence":
					editsentence(); 
					break; 
				case "Delete Word":
					delword(); 
					break; 
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void delword() {
		for (Word word : words) {
			if (word.word.equals(wordtoedit)) {
				words.remove(word); 
			}
		}
		saveset(); 
	}
	
	private void editword() {
		outputtxt.setText("What do you want to change the word to?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String newword = inputtxt.getText(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						word.word = newword; 
						saveset(); 
					}
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void editdefinition() {
		outputtxt.setText("What do you want to change the definition to?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String newdefinition = inputtxt.getText(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						word.definition = newdefinition; 
						saveset(); 
					}
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void editsentence() {
		outputtxt.setText("What do you want to change the sentence to?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String newsentence = inputtxt.getText(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						word.sentence = newsentence; 
					}
				}
			}
		});
	}
	
	private void editpos() {
		outputtxt.setText("What do you want to change the part of speech to?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String newpos = inputtxt.getText(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						saveset(); 
						switch (newpos) {
						case "noun":
							word.pos = noun;
							break; 
						case "verb":
							word.pos = verb; 
							break; 
						case "adjective":
							word.pos = adjective; 
							break;
						case "adverb":
							word.pos = adverb; 
							break;
						case "preposition":
							word.pos = preposition; 
							break;
						case "interjection":
							word.pos = noun; 
							break;
						case "conjunction":
							word.pos = conjunction; 
							break;
						}
					}
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void editsynonyms() {
		outputtxt.setText("What do you want to do to the synonyms?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit"); 
		ObservableList<String> choices = FXCollections.observableArrayList();
		choices.add("Add synonyms");
		choices.add("Change synonym"); 
		choices.add("Remove Synonym"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String choice = (String) inputspinner.getValue(); 
				switch (choice) {
				case "Add synonyms":
					addsynonym(); 
					break; 
				case "Change synonym":
					changesynonym();
					break; 
				case "Remove Synonym":
					removesynonym(); 
					break; 
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void addsynonym() {
		outputtxt.setText("Enter the synonym to add. Click stop when finished");
		enterbtn.setText("Enter");
		stopbtn.setText("Stop");
		cont = true; 	
		while (cont) {
			enterbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String newsynonym = inputtxt.getText(); 
					for (Word word : words) {
						if (word.word.equals(wordtoedit)) {
							word.synonyms.add(newsynonym);
						}
					}
				}
			});
		
			stopbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					cont = false; 
				}
			});
		}
		saveset(); 
	}
	
	private void changesynonym() {
		outputtxt.setText("Which synonym do you want to change?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices = FXCollections.observableArrayList();
		
		for (Word word : words) {
			if (word.word.equals(wordtoedit)) {
				for (String synonym : word.synonyms) {
					choices.add(synonym); 
				}
			}
		}
		
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String synonymtochange = (String) inputspinner.getValue(); 
				outputtxt.setText("What do you want to change the synonym to?");
				enterbtn.setText("Enter");
				stopbtn.setText("Exit");
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String synonymchanged = inputtxt.getText(); 
						for (Word word : words) {
							if (word.word.equals(wordtoedit)) {
								for (String synonym : word.synonyms) {
									if (synonym.equals(synonymtochange)) {
										synonym = synonymchanged; 
										saveset(); 
									}
								}
							}
						}
					}
				});
				stopbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
						primaryStage.close();
					}
				});
			}
		});	
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void removesynonym() {
		outputtxt.setText("Which synonym do you want to change?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices = FXCollections.observableArrayList();
		
		for (Word word : words) {
			if (word.word.equals(wordtoedit)) {
				for (String synonym : word.synonyms) {
					choices.add(synonym); 
				}
			}
		}
		
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String synonymtoremove = (String) inputspinner.getValue(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						for (String synonym : word.synonyms) {
							if (synonym.equals(synonymtoremove)) {
								word.synonyms.remove(synonymtoremove);
								saveset(); 
							}
						}
					}
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void editantonyms() {
		outputtxt.setText("What do you want to do to the antonyms?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit"); 
		ObservableList<String> choices = FXCollections.observableArrayList();
		choices.add("Add antonyms");
		choices.add("Change antonym"); 
		choices.add("Remove Antonym"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String choice = (String) inputspinner.getValue(); 
				switch (choice) {
				case "Add antonym":
					addantonym(); 
					break; 
				case "Change antonym":
					changeantonym();
					break; 
				case "Remove Antonym":
					removeantonym(); 
					break; 
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void addantonym() {
		outputtxt.setText("Enter the antonym to add. Click stop when finished");
		enterbtn.setText("Enter");
		stopbtn.setText("Stop");
		cont = true; 
		
		while (cont) {
			enterbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String newantonym = inputtxt.getText(); 
					for (Word word : words) {
						if (word.word.equals(wordtoedit)) {
							word.antonyms.add(newantonym);
						}
					}
				}
			});
		
			stopbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					cont = false; 
				}
			});
		}
		saveset(); 
	}
	
	private void changeantonym() {
		outputtxt.setText("Which antonym do you want to change?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices = FXCollections.observableArrayList();
		
		for (Word word : words) {
			if (word.word.equals(wordtoedit)) {
				for (String antonym : word.antonyms) {
					choices.add(antonym); 
				}
			}
		}
		
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String antonymtochange = (String) inputspinner.getValue(); 
				outputtxt.setText("What do you want to change the antonym to?");
				enterbtn.setText("Enter");
				stopbtn.setText("Exit");
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String antonymchanged = inputtxt.getText(); 
						for (Word word : words) {
							if (word.word.equals(wordtoedit)) {
								for (String antonym : word.antonyms) {
									if (antonym.equals(antonymtochange)) {
										antonym = antonymchanged; 
										saveset(); 
									}
								}
							}
						}
					}
				});
				stopbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
						primaryStage.close();
					}
				});
			}
		});	
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void removeantonym() {
		outputtxt.setText("Which antonym do you want to change?");
		enterbtn.setText("Enter");
		stopbtn.setText("Exit");
		ObservableList<String> choices = FXCollections.observableArrayList();
		
		for (Word word : words) {
			if (word.word.equals(wordtoedit)) {
				for (String antonym : word.antonyms) {
					choices.add(antonym); 
				}
			}
		}
		
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory); 
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String antonymtoremove = (String) inputspinner.getValue(); 
				for (Word word : words) {
					if (word.word.equals(wordtoedit)) {
						for (String antonym : word.antonyms) {
							if (antonym.equals(antonymtoremove)) {
								word.antonyms.remove(antonymtoremove);
								saveset(); 
							}
						}
					}
				}
			}
		});
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void saveset() {
		try {
			FileOutputStream fos = new FileOutputStream(set);
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			oos.writeObject(words); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		outputtxt.setText("Edit complete. Click exit to quit.");
		enterbtn.setText("Enter"); 
		stopbtn.setText("Exit");
		stopbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
				primaryStage.close();
			}
		});
	}
	
	private void deleteset() {
		File filetodelete = new File(set); 
		filetodelete.delete(); 
	}
}
