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
	int termordef; 
	int count; 
	
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
				choosetermordef(); 
			}
		});
		exitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				exit(); 
			}
		});
	}
	
	private void choosetermordef() {
		outputtxt.setText("Choose whether to study by each term or respond with the term");
		ObservableList<String> choices = FXCollections.observableArrayList(); 
		choices.add("Study by term"); 
		choices.add("Respond with term"); 
		choices.add("Mix of both"); 
		SpinnerValueFactory<String> inputValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(choices); 
		inputValueFactory.setValue(choices.get(0));
		inputspinner.setValueFactory(inputValueFactory);
		enterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				switch (inputspinner.getValue()) {
				case "Study by term":
					termordef = 0; 
					break; 
				case "Respond with term":
					termordef = 1; 
					break; 
				case "Mix of both":
					termordef = 2; 
					break; 
				}
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
	}
	
	private void exit() {
		Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
		primaryStage.close(); 
	}
}
