package vocabstudy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML; 
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class StudySetController {
	@FXML
	private TextField inputtxt; 

	@FXML
	private Label outputtxt;  
	
	@FXML
	private Spinner<String> inputspinner; 
	
	@FXML
	private Button enterbtn; 
	
	@FXML
	private Button exitbtn; 
	
	File dir = new File("D:\\Files\\Java Projects\\VocabsStudy"); 
	ObservableList<String> sets = FXCollections.observableArrayList();
	String set; 
	ArrayList<Word> words = new ArrayList<Word>(); 
	Random rand = new Random(); 
	
	String studychoice; 
	String studymethod; 
	int count, posresponse; 
	String response, posstring; 
	
	// easier reference integers for parts of speech
	public int noun = 0; 
	public int pronoun = 1; 
	public int verb = 2; 
	public int adjective = 3; 
	public int adverb = 4; 
	public int preposition = 5; 
	public int conjunction = 6;  
	public int interjection = 7; 
	
	@FXML
	private void initialize() {
		studyset(); 
	}
	
	private void studyset() {
		chooseset(); 
	}
	
	private void chooseset() {
		outputtxt.setText("Select the set to study");
		enterbtn.setText("Enter");
		exitbtn.setText("Exit");
		
		exitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				exit();
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
							choosestudy(); 
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
	
	private void choosestudy() {
		outputtxt.setText("Choose what you want to study");
		ObservableList<String> choices = FXCollections.observableArrayList();
		choices.add("Definitions"); 
		choices.add("Parts of Speech"); 
		choices.add("Synonyms"); 
		choices.add("Antonyms"); 
		choices.add("Example Sentences"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory);
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				studychoice = inputValueFactory.getValue(); 
				choosestudymethod(); 
			}
		});
		exitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				exit(); 
			}
		});
	}
	
	private void choosestudymethod() {
		outputtxt.setText("Choose how to study");
		ObservableList<String> choices = FXCollections.observableArrayList(); 
		choices.add("Multiple Choice"); 
		choices.add("Written"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory);
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				studymethod = inputValueFactory.getValue(); 
				launchstudymethod(); 
			}
		});
		exitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				exit(); 
			}
		});
	}
	
	private void launchstudymethod() {
		count = 0; 
		switch (studychoice) {
		case "Definitions":
			studydef(); 
			break; 
		case "Parts of Speech":
			studypos(); 
			break; 
		case "Synonyms":
			studysyn(); 
			break; 
		case "Antonyms":
			studyant(); 
			break; 
		case "Example Sentences":
			studysentence(); 
			break; 
		
		}
	}
	
	private void studydef() {
		if (count < words.size()) {
			if (studymethod.equals("Written")) {
				outputtxt.setText(words.get(count).definition);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent Event) {
						response = inputtxt.getText(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studydef(); 
							}
						});
					}
				});
			}
			else {
				outputtxt.setText(words.get(count).definition);
				ObservableList<String> choices = FXCollections.observableArrayList(); 
				for (int i = 0; i < words.size(); i++) {
					choices.add(words.get(i).word); 
				}
				SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
				inputValueFactory.setValue(choices.get(0));
				inputspinner.setValueFactory(inputValueFactory);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						response = inputspinner.getValue(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studydef(); 
							}
						});
					}
				});
			}
		}
		else {
			outputtxt.setText("Set study finished.");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
	}
	
	private void studypos() {
		if (count < words.size()) {
			if (studymethod.equals("Written")) {
				outputtxt.setText(words.get(count).word);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent Event) {
						response = inputtxt.getText(); 
						switch (response) {
						case "noun": 
							posresponse = noun; 
							break; 
						case "pronoun":
							posresponse = pronoun; 
							break; 
						case "verb":
							posresponse = verb;
							break; 
						case "adjective":
							posresponse = adjective; 
							break; 
						case "adverb":
							posresponse = adverb; 
							break; 
						case "conjunction":
							posresponse = conjunction; 
							break; 
						case "interjection":
							posresponse = interjection; 
							break; 
						case "preposition":
							posresponse = preposition; 
							break; 
						}
						switch (words.get(count).pos) {
						case 0:
							posstring = "noun"; 
							break;
						case 1:
							posstring = "pronoun"; 
							break;
						case 2:
							posstring = "verb"; 
							break;
						case 3:
							posstring = "adjective"; 
							break;
						case 4:
							posstring = "adverb"; 
							break;
						case 5:
							posstring = "preposition"; 
							break;
						case 6:
							posstring = "conjunction"; 
							break;
						case 7:
							posstring = "interjection"; 
							break;
						}
						if (posresponse == words.get(count).pos) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + posstring + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studypos(); 
							}
						});
					}
				});
			}
			else {
				outputtxt.setText(words.get(count).word);
				ObservableList<String> choices = FXCollections.observableArrayList(); 
				choices.add("noun"); 
				choices.add("pronoun"); 
				choices.add("verb"); 
				choices.add("adjective"); 
				choices.add("adverb"); 
				choices.add("conjunction"); 
				choices.add("interjection"); 
				choices.add("preposition"); 
				SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
				inputValueFactory.setValue(choices.get(0));
				inputspinner.setValueFactory(inputValueFactory);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						response = inputspinner.getValue(); 
						switch (response) {
						case "noun": 
							posresponse = noun; 
							break; 
						case "pronoun":
							posresponse = pronoun; 
							break; 
						case "verb":
							posresponse = verb;
							break; 
						case "adjective":
							posresponse = adjective; 
							break; 
						case "adverb":
							posresponse = adverb; 
							break; 
						case "conjunction":
							posresponse = conjunction; 
							break; 
						case "interjection":
							posresponse = interjection; 
							break; 
						case "preposition":
							posresponse = preposition; 
							break; 
						}
						switch (words.get(count).pos) {
						case 0:
							posstring = "noun"; 
							break;
						case 1:
							posstring = "pronoun"; 
							break;
						case 2:
							posstring = "verb"; 
							break;
						case 3:
							posstring = "adjective"; 
							break;
						case 4:
							posstring = "adverb"; 
							break;
						case 5:
							posstring = "preposition"; 
							break;
						case 6:
							posstring = "conjunction"; 
							break;
						case 7:
							posstring = "interjection"; 
							break;
						}
						if (posresponse == words.get(count).pos) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + posstring + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studypos(); 
							}
						});
					}
				});
			}
		}
		else {
			outputtxt.setText("Set study finished.");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
	}
	
	private void studysyn() {
		if (words.get(count).synonyms.size() > 0) {
		if (count < words.size()) {
			if (studymethod.equals("Written")) {
				System.out.println(words.get(count).synonyms.size());
				//outputtxt.setText(words.get(count).synonyms.get(rand.nextInt(words.get(count).synonyms.size())));
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent Event) {
						response = inputtxt.getText(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studysyn(); 
							}
						});
					}
				});
			}
			else {
				outputtxt.setText(words.get(count).synonyms.get(rand.nextInt(words.get(count).synonyms.size())));
				ObservableList<String> choices = FXCollections.observableArrayList(); 
				for (int i = 0; i < words.size(); i++) {
					choices.add(words.get(i).word); 
				}
				SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
				inputValueFactory.setValue(choices.get(0));
				inputspinner.setValueFactory(inputValueFactory);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						response = inputspinner.getValue(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studyant(); 
							}
						});
					}
				});
			}
		}
		else {
			outputtxt.setText("Set study finished.");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
		}
		else {
			outputtxt.setText("No synonyms");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
	}
	
	private void studyant() {
		if (words.get(count).antonyms.size() > 0) {
			if (count < words.size()) {
				if (studymethod.equals("Written")) {
					outputtxt.setText(words.get(count).antonyms.get(rand.nextInt(words.get(count).synonyms.size())));
					enterbtn.setOnAction(new EventHandler<ActionEvent>() {
						@Override 
						public void handle(ActionEvent Event) {
							response = inputtxt.getText(); 
							if (response.equals(words.get(count).word)) {
								outputtxt.setText("Correct! Press enter to continue");
							}
							else {
								outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
							}
							enterbtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent Event) {
									count++; 
									studyant(); 
								}
							});
						}
					});
				}
				else {
					outputtxt.setText(words.get(count).antonyms.get(rand.nextInt(words.get(count).synonyms.size())));
					ObservableList<String> choices = FXCollections.observableArrayList(); 
					for (int i = 0; i < words.size(); i++) {
						choices.add(words.get(i).word); 
					}
					SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
					inputValueFactory.setValue(choices.get(0));
					inputspinner.setValueFactory(inputValueFactory);
					enterbtn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							response = inputspinner.getValue(); 
							if (response.equals(words.get(count).word)) {
								outputtxt.setText("Correct! Press enter to continue");
							}
							else {
								outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + " Press enter to continue");
							}
							enterbtn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent Event) {
									count++; 
									studyant(); 
								}
							});
						}
					});
				}
			}
			else {
				outputtxt.setText("Set study finished.");
				exitbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						exit(); 
					}
				});
			}
		}
		else {
			outputtxt.setText("No antonyms");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
	}
	
	private void studysentence() {
		if (count < words.size()) {
			if (studymethod.equals("Written")) {
				outputtxt.setText(words.get(count).sentence.replaceAll(words.get(count).word, " ----- "));
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override 
					public void handle(ActionEvent Event) {
						response = inputtxt.getText(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + "Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studysentence(); 
							}
						});
					}
				});
			}
			else {
				outputtxt.setText(words.get(count).sentence.replaceAll(words.get(count).word, " ----- "));
				ObservableList<String> choices = FXCollections.observableArrayList(); 
				for (int i = 0; i < words.size(); i++) {
					choices.add(words.get(i).word); 
				}
				SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices);
				inputValueFactory.setValue(choices.get(0));
				inputspinner.setValueFactory(inputValueFactory);
				enterbtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						response = inputspinner.getValue(); 
						if (response.equals(words.get(count).word)) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count).word + "Press enter to continue");
						}
						enterbtn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent Event) {
								count++; 
								studysentence(); 
							}
						});
					}
				});
			}
		}
		else {
			outputtxt.setText("Set study finished.");
			exitbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					exit(); 
				}
			});
		}
	}
	private void exit() {
		Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
		primaryStage.close(); 
	}
}
