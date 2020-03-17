package wiki.wisuggest.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import wiki.wisuggest.*;
import wiki.wisuggest.service.WiService;

public class WiController {
	
	@FXML
	private Button btnSubmit;
	@FXML
	private TextField txtLink;
	@FXML
	private Hyperlink lblLink;
	
	private WiService service = new WiService();
	private String wiSuggestUrl;
	
	@FXML
	public void initialize() {
		lblLink.setDisable(true);
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					wiSuggestUrl = service.processArticles(txtLink.getText().toString());
					lblLink.setText(wiSuggestUrl);
					lblLink.setDisable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		lblLink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				WiSuggest.hostService.showDocument(wiSuggestUrl);
			}
			
		});
	}
}
