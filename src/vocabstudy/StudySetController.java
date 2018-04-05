package vocabstudy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count) + "Press enter to continue");
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
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count) + "Press enter to continue");
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
						if (posresponse == words.get(count).pos) {
							outputtxt.setText("Correct! Press enter to continue");
						}
						else {
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count) + "Press enter to continue");
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
							outputtxt.setText("Incorrect. The correct answer was: " + words.get(count) + "Press enter to continue");
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
	
	private void studysyn() {
		
	}
	
	private void studyant() {
		
	}
	
	private void studysentence() {
		
	}
	private void exit() {
		Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
		primaryStage.close(); 
	}
}
