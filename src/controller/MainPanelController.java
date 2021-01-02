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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    
    @FXML
    private AreaChart<?, ?> chartPurchase;

    @FXML
    private AreaChart<?, ?> chartSale;
    
    @FXML
    private LineChart<?, ?> chartReceipt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menus = Arrays.asList(addPurchase, purchaseDetail, addPurchaseReturn, purchaseReturnDetail, addSales,
                salesDetail, addSalesReturn, salesReturnDetail);
        XYChart.Series purchaseSeries= new XYChart.Series();
        
        purchaseSeries.getData().add(new XYChart.Data("1", 8000));
        purchaseSeries.getData().add(new XYChart.Data("2", 7500));
        purchaseSeries.getData().add(new XYChart.Data("3", 9500));
        purchaseSeries.getData().add(new XYChart.Data("5", 3600));
        purchaseSeries.getData().add(new XYChart.Data("6", 4000));
        purchaseSeries.getData().add(new XYChart.Data("7", 9800));
        purchaseSeries.getData().add(new XYChart.Data("8", 12000));
        purchaseSeries.getData().add(new XYChart.Data("9", 5000));
        purchaseSeries.getData().add(new XYChart.Data("10", 6000));
        purchaseSeries.getData().add(new XYChart.Data("11", 6500));
        purchaseSeries.getData().add(new XYChart.Data("12", 8000));
        purchaseSeries.getData().add(new XYChart.Data("13", 9000));
        purchaseSeries.getData().add(new XYChart.Data("14", 10000));
        purchaseSeries.getData().add(new XYChart.Data("15", 7800));
        purchaseSeries.getData().add(new XYChart.Data("16", 14500));
        purchaseSeries.getData().add(new XYChart.Data("17", 12000));
        
        XYChart.Series purchaseReturnSeries= new XYChart.Series();
        
        purchaseReturnSeries.getData().add(new XYChart.Data("1", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("2", 100));
        purchaseReturnSeries.getData().add(new XYChart.Data("3", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("5", 80));
        purchaseReturnSeries.getData().add(new XYChart.Data("6", 110));
        purchaseReturnSeries.getData().add(new XYChart.Data("7", 75));
        purchaseReturnSeries.getData().add(new XYChart.Data("8", 1000));
        purchaseReturnSeries.getData().add(new XYChart.Data("9", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("10", 200));
        purchaseReturnSeries.getData().add(new XYChart.Data("11", 95));
        purchaseReturnSeries.getData().add(new XYChart.Data("12", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("13", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("14", 0));
        purchaseReturnSeries.getData().add(new XYChart.Data("15", 100));
        purchaseReturnSeries.getData().add(new XYChart.Data("16", 90));
        purchaseReturnSeries.getData().add(new XYChart.Data("17", 0));
        
        XYChart.Series saleSeries = new XYChart.Series();
        
        saleSeries.getData().add(new XYChart.Data("1", 12544));
        saleSeries.getData().add(new XYChart.Data("2", 10544));
        saleSeries.getData().add(new XYChart.Data("3", 9544));
        saleSeries.getData().add(new XYChart.Data("5", 5857));
        saleSeries.getData().add(new XYChart.Data("6", 6200));
        saleSeries.getData().add(new XYChart.Data("7", 8565));
        saleSeries.getData().add(new XYChart.Data("8", 13000));
        saleSeries.getData().add(new XYChart.Data("9", 9832));
        saleSeries.getData().add(new XYChart.Data("10", 6555));
        saleSeries.getData().add(new XYChart.Data("11", 8928));
        saleSeries.getData().add(new XYChart.Data("12", 6233));
        saleSeries.getData().add(new XYChart.Data("13", 7021));
        saleSeries.getData().add(new XYChart.Data("14", 8936));
        saleSeries.getData().add(new XYChart.Data("15", 7895));
        saleSeries.getData().add(new XYChart.Data("16", 8954));
        saleSeries.getData().add(new XYChart.Data("17", 7224));
        
        XYChart.Series saleReturnSeries = new XYChart.Series();
        
        saleReturnSeries.getData().add(new XYChart.Data("1", 800));
        saleReturnSeries.getData().add(new XYChart.Data("2", 0));
        saleReturnSeries.getData().add(new XYChart.Data("3", 0));
        saleReturnSeries.getData().add(new XYChart.Data("5", 752));
        saleReturnSeries.getData().add(new XYChart.Data("6", 42));
        saleReturnSeries.getData().add(new XYChart.Data("7", 0));
        saleReturnSeries.getData().add(new XYChart.Data("8", 22));
        saleReturnSeries.getData().add(new XYChart.Data("9", 0));
        saleReturnSeries.getData().add(new XYChart.Data("10", 0));
        saleReturnSeries.getData().add(new XYChart.Data("11", 0));
        saleReturnSeries.getData().add(new XYChart.Data("12", 118));
        saleReturnSeries.getData().add(new XYChart.Data("13", 72));
        saleReturnSeries.getData().add(new XYChart.Data("14", 0));
        saleReturnSeries.getData().add(new XYChart.Data("15", 0));
        saleReturnSeries.getData().add(new XYChart.Data("16", 8));
        saleReturnSeries.getData().add(new XYChart.Data("17", 2));
        
        XYChart.Series receiptSeries = new XYChart.Series();
        
        receiptSeries.getData().add(new XYChart.Data("1", 12000));
        receiptSeries.getData().add(new XYChart.Data("2", 10000));
        receiptSeries.getData().add(new XYChart.Data("3", 9000));
        receiptSeries.getData().add(new XYChart.Data("5", 15000));
        receiptSeries.getData().add(new XYChart.Data("6", 6200));
        receiptSeries.getData().add(new XYChart.Data("7", 8000));
        receiptSeries.getData().add(new XYChart.Data("8", 12000));
        receiptSeries.getData().add(new XYChart.Data("9", 9000));
        receiptSeries.getData().add(new XYChart.Data("10", 6555));
        receiptSeries.getData().add(new XYChart.Data("11", 8000));
        receiptSeries.getData().add(new XYChart.Data("12", 4000));
        receiptSeries.getData().add(new XYChart.Data("13", 8950));
        receiptSeries.getData().add(new XYChart.Data("14", 7000));
        receiptSeries.getData().add(new XYChart.Data("15", 12000));
        receiptSeries.getData().add(new XYChart.Data("16", 8500));
        receiptSeries.getData().add(new XYChart.Data("17", 5000));
        
        XYChart.Series paymentSeries = new XYChart.Series();
        
        paymentSeries.getData().add(new XYChart.Data("1", 6000));
        paymentSeries.getData().add(new XYChart.Data("2", 5000));
        paymentSeries.getData().add(new XYChart.Data("3", 8000));
        paymentSeries.getData().add(new XYChart.Data("5", 12000));
        paymentSeries.getData().add(new XYChart.Data("6", 6000));
        paymentSeries.getData().add(new XYChart.Data("7", 10000));
        paymentSeries.getData().add(new XYChart.Data("8", 10000));
        paymentSeries.getData().add(new XYChart.Data("9", 8000));
        paymentSeries.getData().add(new XYChart.Data("10", 5000));
        paymentSeries.getData().add(new XYChart.Data("11", 7000));
        paymentSeries.getData().add(new XYChart.Data("12", 6000));
        paymentSeries.getData().add(new XYChart.Data("13", 8000));
        paymentSeries.getData().add(new XYChart.Data("14", 6500));
        paymentSeries.getData().add(new XYChart.Data("15", 10000));
        paymentSeries.getData().add(new XYChart.Data("16", 6500));
        paymentSeries.getData().add(new XYChart.Data("17", 8000));
        
        receiptSeries.setName("Receipt");
        paymentSeries.setName("Payment");
        purchaseSeries.setName("Purchase");
        purchaseReturnSeries.setName("Purchase Return");
        saleSeries.setName("Sale");
        saleReturnSeries.setName("Sales Return");
        
        chartPurchase.getData().addAll(purchaseSeries, purchaseReturnSeries);
        chartSale.getData().addAll(saleSeries, saleReturnSeries);
        chartReceipt.getData().addAll(paymentSeries, receiptSeries);    
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
