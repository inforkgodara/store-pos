/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
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

    @FXML
    private Button addPurchase, purchaseDetail, addPurchaseReturn, purchaseReturnDetail, addSales, salesDetail, addSalesReturn, salesReturnDetail;
    
    private List<Button> menus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menus = Arrays.asList(addPurchase, purchaseDetail, addPurchaseReturn, purchaseReturnDetail, addSales,
                salesDetail, addSalesReturn, salesReturnDetail);
    }

    private void changeButtonBackground(ActionEvent e) {
        Iterator<Button> iteratorMenus = menus.iterator();

        while (iteratorMenus.hasNext()) {
            Button clickedButton = (Button) e.getSource();
            Button OtherButton = iteratorMenus.next();
            if (clickedButton == OtherButton) {
              clickedButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#2b2a26;");
            } else {
              OtherButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#404040;");
            }
        }

    }

    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private void loadFXML(String fileName) {
        Parent parent;
        try {
//            parent = FXMLLoader.load(getClass().getResource("/test/FXML.fxml"));
            parent = FXMLLoader.load(getClass().getResource("/view/" + fileName + ".fxml"));
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
    private void loadAddSalesView(ActionEvent e) {
        loadFXML("AddSalesView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadListSalesView(ActionEvent e) {
        loadFXML("ListSalesView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadAddSalesReturnView(ActionEvent e) {
        loadFXML("AddSalesReturnView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadListSalesReturnView(ActionEvent e) {
        loadFXML("ListSalesReturnView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadAddPurchaseView(ActionEvent e) {
        loadFXML("AddPurchaseView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadListPurchaseView(ActionEvent e) {
        loadFXML("ListPurchaseView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadAddPurchaseReturnView(ActionEvent e) {
        loadFXML("AddPurchaseReturnView");
        changeButtonBackground(e);
    }

    @FXML
    private void loadListPurchaseReturnView(ActionEvent e) {
        loadFXML("ListPurchaseReturnView");
        changeButtonBackground(e);
    }

    public void loadEditSalesView(ActionEvent e) {
        loadFXML("ListSalesView");
        changeButtonBackground(e);
    }

    public void loadEditPurchaseView(ActionEvent e) {
        loadFXML("ListPurchaseView");
        changeButtonBackground(e);
    }
}
