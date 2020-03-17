package wiki.wisuggest;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WiSuggest extends Application
{
	public static HostServices hostService;
    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
            Parent root = FXMLLoader.load(getClass().getResource("/wiki/wisuggest/Index.fxml"));
            primaryStage.setTitle("WiSuggest");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            hostService = getHostServices();
        } catch(Exception e) {
            e.printStackTrace();
        }		
	}
}