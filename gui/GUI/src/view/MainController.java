package view;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MainController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eraItem"
    private HBox eraItem; // Value injected by FXMLLoader

    @FXML // fx:id="eventItem"
    private HBox eventItem; // Value injected by FXMLLoader

    @FXML // fx:id="festivalItem"
    private HBox festivalItem; // Value injected by FXMLLoader

    @FXML // fx:id="historicSiteItem"
    private HBox historicSiteItem; // Value injected by FXMLLoader

    @FXML // fx:id="historicalFigureItem"
    private HBox historicalFigureItem; // Value injected by FXMLLoader

    @FXML // fx:id="searchInfo"
    private TextField searchInfo; // Value injected by FXMLLoader

    @FXML // fx:id="tableData"
    private TableView<?> tableData; // Value injected by FXMLLoader

    //private HBox[] items;
	private HBox selectedItem;
	

    @FXML
    void ClearSearch(ActionEvent event) {
        searchInfo.setText("");
		String type = selectedItem.getId();
		tableData.getColumns().clear();
		switch (type) {
		case "Nhân vật lịch sử":
			break;
		case "Di tích lịch sử":
			break;
		case "Sự kiện lịch sử":
			break;
		case "Lễ hội văn hóa":
			break;
		case "Triều đại lịch sử":
			break;
		default:
			System.out.println("Loi");
		}
    }

    @FXML
    public void SearchByName(ActionEvent event) {
       String keyword = searchInfo.getText();
		String type = selectedItem.getId();

		tableData.getColumns().clear();
		switch (type) {
		case "Nhân vật lịch sử":
			break;
		case "Di tích lịch sử":
			break;
		case "Sự kiện lịch sử":
			break;
		case "Lễ hội văn hóa":
			break;
		case "Triều đại lịch sử":
			break;
		default:
			System.out.println("Loi");
		}
    }

    @FXML
    public void handleItemClicked(MouseEvent event) {
        HBox clickedItem = (HBox) event.getSource();
		selectedItem.setStyle("-fx-cursor: hand");
		selectedItem = clickedItem;
		selectedItem.setStyle("-fx-background-color: #ccc");

		// change table
		tableData.getColumns().clear();
		String labelText = "";
		for (Node node : selectedItem.getChildren()) {
			if (node instanceof Label) {
				Label label = (Label) node;
				labelText = label.getText();
			}
		}
		switch (labelText) {
		case "Nhân vật lịch sử":
			break;
		case "Di tích lịch sử":
			break;
		case "Sự kiện lịch sử":
			break;
		case "Lễ hội văn hóa":
			break;
		case "Triều đại lịch sử":
			break;
		default:
			System.out.println("Error");
		}
    }

    
		// TODO set field element with TableField.fxml
		
		// TODO set link element with TableItem.fxml
		

		

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    
    }

}
