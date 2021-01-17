/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.DbConnection;
import helper.AlertHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import model.PurchaseModel;

/**
 * FXML Controller class
 *
 * @author Ramesh Godara
 */
public class ListPurchaseController implements Initializable {

    private Connection con;

//    @FXML
//    private TableView tableView;

    @FXML
    private Button deleteButton;

    @FXML
    private Pagination pagination;
    
    private final TableView<PurchaseModel> tableView = createTable();
    private static final int rowsPerPage = 100;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = dbc.getConnection();
        pagination.setPageFactory(this::createPage);
        
    }

    private Node createPage(int pageIndex) {
        this.createData(pageIndex);
        return tableView;
    }

    private void createData(int pageIndex) {
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM ( SELECT a.*, rownum r__ FROM ( SELECT * FROM purchases ORDER BY order_id desc ) a WHERE rownum < (("+ (pageIndex + 1 )+" * "+ rowsPerPage+") + 1 )) WHERE r__ >= ((("+ (pageIndex + 1 )+"-1) * "+ rowsPerPage+") + 1)";
            ResultSet rs = stmt.executeQuery(query);

            tableView.getItems().clear();
            try {
                while (rs.next()) {
                    tableView.getItems().addAll(new PurchaseModel(rs.getLong("order_id"), String.valueOf(rs.getDate("invoice_date")),
                            rs.getString("party_name"), rs.getFloat("total_quantity"), rs.getFloat("total_amount"),
                            rs.getFloat("other_amount"), rs.getFloat("total_payble_amount"), rs.getFloat("total_paid_amount"), rs.getFloat("total_due_amount")));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TableView<PurchaseModel> createTable() { 
        
        TableView<PurchaseModel> tableView = new TableView<>();
        
        String rightPositionCSS = "-fx-alignment: CENTER-RIGHT;";
        String centerPostionCSS = "-fx-alignment: CENTER;";

        TableColumn<PurchaseModel, Long> columnInvoiceId = new TableColumn<>("Order Id");
        columnInvoiceId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<PurchaseModel, Long> columnInvoiceDate = new TableColumn<>("Invoice Date");
        columnInvoiceDate.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));

        TableColumn<PurchaseModel, Long> columnPartyName = new TableColumn<>("Party");
        columnPartyName.setCellValueFactory(new PropertyValueFactory<>("partyName"));

        TableColumn<PurchaseModel, Long> columnTotalQuantity = new TableColumn<>("Quantity");
        columnTotalQuantity.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
        columnTotalQuantity.setStyle(rightPositionCSS);

        TableColumn<PurchaseModel, Long> columnTotalAmount = new TableColumn<>("Total Amount");
        columnTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        columnTotalAmount.setStyle(rightPositionCSS);

        TableColumn<PurchaseModel, Long> columnOtherAmount = new TableColumn<>("Other Amount");
        columnOtherAmount.setCellValueFactory(new PropertyValueFactory<>("otherAmount"));
        columnOtherAmount.setStyle(rightPositionCSS);

        TableColumn<PurchaseModel, Long> columnTotalPaybleAmount = new TableColumn<>("Payble Amount");
        columnTotalPaybleAmount.setCellValueFactory(new PropertyValueFactory<>("totalPaybleAmount"));
        columnTotalPaybleAmount.setStyle(rightPositionCSS);

        TableColumn<PurchaseModel, Long> columnTotalPaidAmount = new TableColumn<>("Paid Amount");
        columnTotalPaidAmount.setCellValueFactory(new PropertyValueFactory<>("totalPaidAmount"));
        columnTotalPaidAmount.setStyle(rightPositionCSS);

        TableColumn<PurchaseModel, Long> columnTotalDueAmount = new TableColumn<>("Due Amount");
        columnTotalDueAmount.setCellValueFactory(new PropertyValueFactory<>("totalDueAmount"));
        columnTotalDueAmount.setStyle(rightPositionCSS);

        tableView.getColumns().addAll(columnInvoiceId, columnInvoiceDate, columnPartyName, columnTotalQuantity,
                columnTotalAmount, columnOtherAmount, columnTotalPaybleAmount, columnTotalPaidAmount, columnTotalDueAmount);

        return tableView;
    }

    @FXML
    public void viewInvoice(ActionEvent event) {
        List<PurchaseModel> collect = (List<PurchaseModel>) tableView.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
        long orderId = collect.get(0).getOrderId();
        EditPurchaseController.orderId = orderId;
        Scene scene = (Scene) ((Node) event.getSource()).getScene();
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("/view/EditPurchaseView.fxml"));
            BorderPane borderPane = (BorderPane) scene.lookup("#borderPane");
            borderPane.setCenter(parent);
        } catch (IOException ex) {
            Logger.getLogger(ListPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteInvoice(ActionEvent event) {
        Window owner = deleteButton.getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                "Do you want to delete it?");
        if (AlertHelper.result) {
            List<PurchaseModel> collect = (List<PurchaseModel>) tableView.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
            long orderId = collect.get(0).getOrderId();
            EditPurchaseController.orderId = orderId;
            Statement stmt;
            try {
                stmt = con.createStatement();
                stmt.executeQuery("delete from purchases where order_id = " + orderId);
                tableView.getItems().remove(collect.get(0));
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Information",
                        "A record has been deleted successfully.");
            } catch (SQLException ex) {
                Logger.getLogger(ListPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
