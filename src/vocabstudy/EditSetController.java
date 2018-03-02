package vocabstudy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
		
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".ser") && file.getName() != null) {
				sets.add(file.getName().replaceAll(".ser", ""));
			}
		}	
		
		if (sets.isEmpty()) {
			outputtxt.setText("No sets found");
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
				else {
					//error handling
				}
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
				saveset(0); 
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
				saveset(0); 
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
				saveset(3); 
			}
		});
	}
	
	private void addword(int stage) {
		Word wordtoadd = new Word(word, definition, pos, synonyms, antonyms, sentence); 
		words.add(wordtoadd); 
		getword(); 
		switch (stage) {
		case 0: 
			break; 
		case 1: 
			break;
		case 2: 
			break; 
		case 3: 
			break; 
		case 4: 
			break; 
		}
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
				}
			}
		});
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
					}
				}
			}
		});
	}
	
	private void editdefinition() {
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
					}
				}
			}
		});
	}
	
	private void editsentence() {
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
					}
				}
			}
		});
	}
	
	private void editpos() {
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
					}
				}
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
									}
								}
							}
						}
					}
				});
			
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
							}
						}
					}
				}
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
									}
								}
							}
						}
					}
				});
			
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
							}
						}
					}
				}
			}
		});
	}
	
	private void saveset(int Stage) {
		
	}
	
	private void deleteset() {
		File filetodelete = new File(set); 
		filetodelete.delete(); 
	}
}
