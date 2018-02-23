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
	//Class variable and UI object declaration
    @FXML
    private Label outputtxt;
    
    @FXML
    private Button createsetbtn; 
    
    @FXML
    private Button editsetbtn; 
    
    @FXML
    private Button studysetbtn; 
    
    private Scene createSetScene, editSetScene, studySetScene;

    public void setCreateSetScene(Scene scene) {
        createSetScene = scene;
    }
    
    public void setEditSetScene(Scene scene) {
    	editSetScene = scene; 
    }
    
    public void setStudySetScene(Scene scene) {
    	studySetScene = scene; 
    }
    
    //Sets up UI and text for all attributes
    @FXML
    private void initialize() {
        outputtxt.setText("What do you want to do?");
        createsetbtn.setText("Create set");
        editsetbtn.setText("Edit a set");
        studysetbtn.setText("Study words");
        userchoice(); 
    }
    
    //Switches to appropriate scene when user presses a button
    private void userchoice() {    	 
    	createsetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow());
    	        primaryStage.setScene(createSetScene);
    	        //System.out.println("Create set clicked");
    		}
    	});
    	
    	editsetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow()); 
    			primaryStage.setScene(editSetScene);
    			//System.out.println("Edit set clicked"); 
    		}
    	});
    	
    	studysetbtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			Stage primaryStage = (Stage)(outputtxt.getScene().getWindow()); 
    			primaryStage.setScene(studySetScene);
    			//System.out.println("Study set clicked"); 
    		}
    	});
    }
}




