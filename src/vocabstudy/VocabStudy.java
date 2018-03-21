package vocabstudy;
/*
 * Asher Anand
 * App to help students study vocabulary terms
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VocabStudy extends Application {
	//Class variable declaration
	public static Stage primaryStage;
	
	//Method to start the first scene
	@Override
	public void start(Stage primaryStage) throws Exception {
	    // getting loader and a pane for the first scene. 
        // loader will then give a possibility to get related controller
        FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent mainPane = mainPaneLoader.load();
        Scene mainScene = new Scene(mainPane, 600, 400);

        // getting loader and a pane for the createset scene
        FXMLLoader createSetPaneLoader = new FXMLLoader(getClass().getResource("CreateSet.fxml"));
        Parent createSetPane = createSetPaneLoader.load();
        Scene createSetScene = new Scene(createSetPane, 600, 400);

        // getting loader and a pane for the editset scene
        FXMLLoader editSetPaneLoader = new FXMLLoader(getClass().getResource("EditSet.fxml")); 
        Parent editSetPane = editSetPaneLoader.load(); 
        Scene editSetScene = new Scene(editSetPane, 600, 400); 
        
        // getting loader and a pane for the studyset scene
        FXMLLoader studySetPaneLoader = new FXMLLoader(getClass().getResource("StudySet.fxml")); 
        Parent studySetPane = studySetPaneLoader.load(); 
        Scene studySetScene = new Scene(studySetPane, 600, 400); 
        
        // injecting createset scene into the controller of the main scene
        MainPageController mainpagecontroller = (MainPageController) mainPaneLoader.getController();
        mainpagecontroller.setCreateSetScene(createSetScene);	
        
        // injecting editset scene into the controller of the main scene
        mainpagecontroller.setEditSetScene(editSetScene);
        
        // injecting studyset scene into the controller of the main scene
        mainpagecontroller.setStudySetScene(studySetScene); 
        
        // starting main scene
        primaryStage.setTitle("VocabStudy");
        primaryStage.setScene(mainScene);
        primaryStage.show();
	}
	
	// Main method, only calls start
	public static void main(String[] args) {
		launch(args); 
	}
}
