package vocabstudy;

/*
 * Asher Anand
 * Java controller for vocabstudy main page
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainPageController {
	// Class variable and UI object declaration
    
	// UI vars
	@FXML
    private Label outputtxt;
    
    @FXML
    private Button createsetbtn; 
    
    @FXML
    private Button editsetbtn; 
    
    @FXML
    private Button studysetbtn; 
    
    // Class lvl vars
    private Scene createSetScene, editSetScene, studySetScene;

    // Receives create set scene 
    public void setCreateSetScene(Scene scene) {
        createSetScene = scene;
    }
    
    // Receives edit set scene
    public void setEditSetScene(Scene scene) {
    	editSetScene = scene; 
    }
    
    // Receives study set scene
    public void setStudySetScene(Scene scene) {
    	studySetScene = scene; 
    }
    
    // Sets text for all UI elements and calls for user to make choice
    @FXML
    private void initialize() {
        outputtxt.setText("What do you want to do?");
        createsetbtn.setText("Create set");
        editsetbtn.setText("Edit a set");
        studysetbtn.setText("Study words");
        userchoice(); 
    }
    
    // Switches to appropriate scene when user presses a button
    private void userchoice() {    	 
    	createsetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
    	        primaryStage.setScene(createSetScene);
    	        // System.out.println("Create set clicked"); (for debugging)
    		}
    	});
    	
    	editsetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow()); 
    			primaryStage.setScene(editSetScene);
    			// System.out.println("Edit set clicked"); (for debugging)
    		}
    	});
    	
    	studysetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow()); 
    			primaryStage.setScene(studySetScene);
    			// System.out.println("Study set clicked"); (for debugging)
    		}
    	});
    }
}




