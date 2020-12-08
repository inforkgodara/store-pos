/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ramesh Godara
 */
public class MainPanelController implements Initializable {

    @FXML
    private BorderPane borderPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
 
    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }
    
    @FXML
    private void loadFXML(String fileName) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/view/" +fileName + ".fxml"));
            borderPane.setCenter(parent);
            
        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void close() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void loadAddSalesView() {
        loadFXML("AddSalesView");
    }
    
    @FXML
    private void loadListSalesView() {
        loadFXML("ListSalesView");
    }
    
    @FXML
    private void loadAddSalesReturnView() {
        loadFXML("AddSalesReturnView");
    }
    
    @FXML
    private void loadListSalesReturnView() {
        loadFXML("ListSalesReturnView");
    }
    
    @FXML
    private void loadAddPurchaseView() {
        loadFXML("AddPurchaseView");
    }
    
    @FXML
    private void loadListPurchaseView() {
        loadFXML("ListPurchaseView");
    }
    
    @FXML
    private void loadAddPurchaseReturnView() {
        loadFXML("AddPurchaseReturnView");
    }
    
    @FXML
    private void loadListPurchaseReturnView() {
        loadFXML("ListPurchaseReturnView");
    }
    
    public void loadEditSalesView() {
        loadFXML("ListSalesView");
    }
    
    public void loadEditPurchaseView() {
        loadFXML("ListPurchaseView");
    }
}
