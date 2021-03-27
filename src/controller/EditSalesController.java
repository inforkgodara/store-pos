/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.DbConnection;
import helper.AlertHelper;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import model.Item;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 * FXML Controller class
 *
 * @author Ramesh Godara
 */
public class EditSalesController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField textFieldItem;

    @FXML
    private ComboBox comboBoxUom;
    
    @FXML
    private ComboBox comboBoxCurrency;
    
    @FXML
    private TextField textFieldTaux;

    @FXML
    private TextField textFieldQty;

    @FXML
    private TextField textFieldPrice;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private TableView<Item> tableViewItem;

    @FXML
    private ComboBox comboBoxLocation;

    @FXML
    private TextField textFieldTotalQuantity;

    @FXML
    private TextField textFieldTotalAmount;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textFieldTotalOther;

    @FXML
    private TextField textFieldTotalPaybleAmount;

    @FXML
    private TextField textFieldTotalPaidAmount;

    @FXML
    private TextField textFieldTotalDueAmount;

    @FXML
    private TextField textFieldParty;

    @FXML
    private TextField textFieldContact;

    @FXML
    private TextField textFieldRemarks;
    
    @FXML
    private DatePicker date;

    Set<String> items = new HashSet<>();
    SuggestionProvider<String> provider = SuggestionProvider.create(items);
    private AutoCompletionBinding<String> autoCompleteBinding;

    Set<String> customers = new HashSet<>();
    SuggestionProvider<String> provider1 = SuggestionProvider.create(customers);
    private AutoCompletionBinding<String> autoCompleteBinding1;

    private final Connection con;

    private int selectedTableViewRow = 0;
    private long itemId;
    private long previousID;

    public static long orderId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String rightPositionCSS = "-fx-alignment: CENTER-RIGHT;";
        String centerPostionCSS = "-fx-alignment: CENTER;";
        AutoCompletionTextFieldBinding test = new AutoCompletionTextFieldBinding<>(textFieldItem, provider);
        test.setOnAutoCompleted(e -> setUomAndPrice());

        AutoCompletionTextFieldBinding test1 = new AutoCompletionTextFieldBinding<>(textFieldParty, provider1);
        test1.setOnAutoCompleted(e -> setCustomer());

        TableColumn<Item, String> columnItem = new TableColumn<>("Item");
        columnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnItem.setPrefWidth(400);

        TableColumn<Item, String> columnUom = new TableColumn<>("Uom");
        columnUom.setCellValueFactory(new PropertyValueFactory<>("uom"));
        columnUom.setSortable(true);

        TableColumn<Item, Float> columnQuantity = new TableColumn<>("Quantity");
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnQuantity.setSortable(true);
        columnQuantity.setStyle(rightPositionCSS);

        TableColumn<Item, Float> columnLocation = new TableColumn<>("Location");
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnLocation.setSortable(true);
        columnLocation.setStyle(centerPostionCSS);

        TableColumn<Item, Float> columnPrice = new TableColumn<>("Price");
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnPrice.setSortable(true);
        columnPrice.setStyle(rightPositionCSS);

        TableColumn<Item, Float> columnAmount = new TableColumn<>("Amount");
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnAmount.setSortable(true);
        columnAmount.setStyle(rightPositionCSS);

        TableColumn<Item, Long> columnItemId = new TableColumn<>("Item ID");
        columnItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        columnItemId.setVisible(false);

        tableViewItem.getColumns().add(columnItemId);

        tableViewItem.getColumns().add(columnItem);
        tableViewItem.getColumns().add(columnUom);
        tableViewItem.getColumns().add(columnQuantity);
        tableViewItem.getColumns().add(columnLocation);
        tableViewItem.getColumns().add(columnPrice);
        tableViewItem.getColumns().add(columnAmount);

        comboBoxLocation.getItems().setAll("Rack", "Depot", "Display");
        comboBoxLocation.getSelectionModel().select("Depot");
        
        comboBoxCurrency.getItems().setAll("USD", "FC");
        comboBoxCurrency.getSelectionModel().select("USD");

        try {
            viewInvoice(orderId);
        } catch (SQLException ex) {
            Logger.getLogger(EditSalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    @SuppressWarnings("empty-statement")
    void searchItem() {

        String typedItem = textFieldItem.getText();
        if (typedItem != null && typedItem.length() > 2) {
            try {
                boolean isNumeric = typedItem.chars().allMatch(Character::isDigit);
                LocalDate documentDate = LocalDate.now();
                Statement stmt = con.createStatement();
                String query = null;
                if (isNumeric) {
                    query = "select code, a.name item_name, b.name as pack_unit, a.pack_size, c.name as standard_unit, "
                            + "p.sale_price from items a, uoms b, uoms c,\n"
                            + "(select item_id, sale_price from item_prices\n"
                            + "where date'" + documentDate + "' between effective_from and effective_to) p\n"
                            + "where code = ? and a.pack_unit_id = b.UOM_ID and a.standard_unit_id = c.uom_id\n"
                            + "and a.item_id = p.item_id\n"
                            + "order by 2";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, typedItem);
                    ResultSet rs = pstmt.executeQuery();

                    String selectedItem = null;
                    while (rs.next()) {
                        items.add(rs.getString("item_name"));
                        selectedItem = rs.getString("item_name");
                    }
                    if (selectedItem != null) {
                        textFieldItem.setText(selectedItem);
                    } else {
                        query = "select code, a.name item_name, b.name as pack_unit, a.pack_size, c.name as standard_unit, "
                                + "p.sale_price from items a, uoms b, uoms c,\n"
                                + "(select item_id, sale_price from item_prices\n"
                                + "where date'" + documentDate + "' between effective_from and effective_to) p\n"
                                + "where upper(a.name) LIKE upper(?) and a.pack_unit_id = b.UOM_ID and a.standard_unit_id = c.uom_id\n"
                                + "and a.item_id = p.item_id\n"
                                + "order by 2";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, "%" + typedItem + "%");
                        rs = pstmt.executeQuery();

                        while (rs.next()) {
                            items.add(rs.getString("item_name"));
                        }
                        Set<String> filteredAutoCompletions = new HashSet<>(items);
                        provider.clearSuggestions();
                        provider.addPossibleSuggestions(filteredAutoCompletions);
                    }
                } else {
                    query = "select code, a.name item_name, b.name as pack_unit, a.pack_size, c.name as standard_unit, "
                            + "p.sale_price from items a, uoms b, uoms c,\n"
                            + "(select item_id, sale_price from item_prices\n"
                            + "where date'" + documentDate + "' between effective_from and effective_to) p\n"
                            + "where upper(a.name) LIKE upper(?) and a.pack_unit_id = b.UOM_ID and a.standard_unit_id = c.uom_id\n"
                            + "and a.item_id = p.item_id\n"
                            + "order by 2";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, "%" + typedItem + "%");
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        items.add(rs.getString("item_name"));
                    }
                    Set<String> filteredAutoCompletions = new HashSet<>(items);
                    provider.clearSuggestions();
                    provider.addPossibleSuggestions(filteredAutoCompletions);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public EditSalesController() {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = dbc.getConnection();
    }

    public void setUomAndPrice() {
        String pName = textFieldItem.getText();
        try {
            LocalDate documentDate = LocalDate.now();
            Statement stmt = con.createStatement();
            String query = "select a.item_id, a.name item_name, b.name as pack_unit, a.pack_size, c.name as standard_unit,"
                    + "p.sale_price from items a, uoms b, uoms c,\n"
                    + "(select item_id, sale_price from item_prices\n"
                    + "where date'" + documentDate + "' between effective_from and effective_to) p\n"
                    + "where a.name=? and a.pack_unit_id = b.UOM_ID and a.standard_unit_id = c.uom_id\n"
                    + "and a.item_id = p.item_id\n"
                    + "order by 2";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, pName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                items.clear();
                comboBoxUom.getItems().clear();
                comboBoxUom.getItems().setAll(rs.getString("pack_unit"), rs.getString("standard_unit"));
                comboBoxUom.getSelectionModel().select(rs.getString("pack_unit"));
                textFieldPrice.setText(rs.getString("sale_price"));
                itemId = rs.getLong("item_id");
            } else {
                comboBoxUom.getItems().clear();
                textFieldPrice.clear();
                itemId = 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void setPrice() {
        String uom = (String) comboBoxUom.getSelectionModel().getSelectedItem();
        System.out.println(uom);
        try {
            Statement stmt = con.createStatement();
            String query = "select * from uoms where name=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, uom);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String unitName = rs.getString("name");
                String pCode = textFieldItem.getText();
                LocalDate documentDate = LocalDate.now();
                String query1 = "select code, a.name item_name, b.name as pack_unit, a.pack_size, c.name as standard_unit, "
                        + "p.sale_price from items a, uoms b, uoms c,\n"
                        + "(select item_id, sale_price from item_prices\n"
                        + "where date'" + documentDate + "' between effective_from and effective_to) p\n"
                        + "where a.name=? and a.pack_unit_id = b.UOM_ID and a.standard_unit_id = c.uom_id\n"
                        + "and a.item_id = p.item_id\n"
                        + "order by 2";
                PreparedStatement pstmt1 = con.prepareStatement(query1);
                pstmt1.setString(1, pCode);
                ResultSet rs1 = pstmt1.executeQuery();
                if (rs1.next()) {
                    if (unitName.equals(rs1.getString("pack_unit"))) {
                        float price = rs1.getFloat("sale_price");
                        price = (float) (Math.round(price * 100) / 100.0);
                        textFieldPrice.setText(String.valueOf(price));
                    } else {
                        int packSize = rs1.getInt("pack_size");
                        float salePrice = rs1.getFloat("sale_price");
                        float pricePerStandardUnit = packSize / salePrice;
                        pricePerStandardUnit = (float) (Math.round(pricePerStandardUnit * 100) / 100.0);
                        textFieldPrice.setText(String.valueOf(pricePerStandardUnit));
                    }
                }
                this.calculatePrice();
            } else {

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void calculatePrice() {
        if (!textFieldPrice.getText().isEmpty() && !textFieldQty.getText().isEmpty()) {
            float qty = Float.parseFloat(textFieldQty.getText());
            float amt = Float.parseFloat(textFieldPrice.getText());
            float tot = qty * amt;
            tot = (float) (Math.round(tot * 100) / 100.0);
            textFieldAmount.setText(String.valueOf(tot));
        }
    }

    public void addItemInTableView() {
        if (selectedTableViewRow != 0) {
            int selectedRowNum = tableViewItem.getSelectionModel().getSelectedIndex();
            tableViewItem.getItems().remove(selectedRowNum);

            tableViewItem.getItems().add(selectedRowNum, new Item(textFieldItem.getText(), (String) comboBoxUom.getSelectionModel().getSelectedItem(),
                    Float.parseFloat(textFieldQty.getText()), Float.parseFloat(textFieldPrice.getText()), Float.parseFloat(textFieldAmount.getText()), (String) comboBoxLocation.getSelectionModel().getSelectedItem(),
                    itemId)
            );
            selectedTableViewRow = 0;
        } else {
            tableViewItem.getItems().add(new Item(textFieldItem.getText(), (String) comboBoxUom.getSelectionModel().getSelectedItem(),
                    Float.parseFloat(textFieldQty.getText()), Float.parseFloat(textFieldPrice.getText()), Float.parseFloat(textFieldAmount.getText()), (String) comboBoxLocation.getSelectionModel().getSelectedItem(),
                    itemId)
            );
        }
        this.clearHeaderForm();
        this.calculateTotalAmount();

    }

    public void clearHeaderForm() {
        textFieldItem.clear();
        comboBoxUom.getItems().clear();
        textFieldQty.clear();
//        comboBoxLocation.getItems().clear();
        textFieldPrice.clear();
        textFieldAmount.clear();
        textFieldItem.requestFocus();
        this.calculateTotalAmount();
        tableViewItem.scrollTo(tableViewItem.getItems().size());
        this.selectedTableViewRow = 0;
        itemId = 0;
    }

    @FXML
    private void calculateDueAmount() {
        float paidAmount = (float) 0.0;
        float paybleAmount = Float.parseFloat(textFieldTotalPaybleAmount.getText());
        if (!textFieldTotalPaidAmount.getText().isEmpty()) {
            paidAmount = Float.parseFloat(textFieldTotalPaidAmount.getText());
        }

        textFieldTotalDueAmount.setText(Float.toString(paybleAmount - paidAmount));
    }

    @FXML
    private void calculateTotalAmount() {
        float amount = 0;
        float quantity = 0;
        float other = 0;
        amount = tableViewItem.getItems().stream().map((item) -> item.getAmount()).reduce(amount, (accumulator, _item) -> accumulator + _item);

        quantity = tableViewItem.getItems().stream().map((item) -> item.getQuantity()).reduce(quantity, (accumulator, _item) -> accumulator + _item);

        try {
            other = Float.parseFloat(textFieldTotalOther.getText());

        } catch (Exception e) {

        }
        textFieldTotalPaybleAmount.setText(Float.toString(amount + other));

        textFieldTotalQuantity.setText(Float.toString(quantity));
        textFieldTotalAmount.setText(Float.toString(amount));
        calculateDueAmount();
    }

    public void getSelectedRowTableView() {
        List<Item> collect = (List<Item>) tableViewItem.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
        if (collect.size() > 0) {
            selectedTableViewRow = 1;
            itemId = collect.get(0).getItemId();
            textFieldItem.setText(collect.get(0).getItem());
            comboBoxUom.getItems().clear();
            comboBoxUom.getItems().add(collect.get(0).getUom());
            comboBoxUom.getSelectionModel().select(collect.get(0).getUom());
            textFieldQty.setText(Float.toString(collect.get(0).getQuantity()));
            comboBoxLocation.getSelectionModel().select(collect.get(0).getLocation());
            textFieldPrice.setText(Float.toString(collect.get(0).getPrice()));
            textFieldAmount.setText(Float.toString(collect.get(0).getAmount()));
        }
    }

    public void deleteTableViewRow() {
        int selectedRowNum = tableViewItem.getSelectionModel().getSelectedIndex();
        if (selectedRowNum >= 0) {
            tableViewItem.getItems().remove(selectedRowNum);
        }
        this.clearHeaderForm();
    }

    @FXML
    private void save(ActionEvent event) {
        LocalDate documentDate = LocalDate.now();
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery("delete from sales where order_id = " + orderId);
            stmt.executeQuery("delete from sale_details where order_id =" + orderId);
            String query = "insert into sales (order_id,INVOICE_DATE,TOTAL_QUANTITY,TOTAL_AMOUNT,OTHER_AMOUNT,TOTAL_PAYBLE_AMOUNT,"
                    + "TOTAL_PAID_AMOUNT,TOTAL_DUE_AMOUNT,PARTY_NAME,PARTY_CONTACT,CURRENCY,TAUX,REMARKS)"
                    + "values(" + orderId + "," + "date '" + date.getValue() + "','" + textFieldTotalQuantity.getText() + "','" + textFieldTotalAmount.getText() + "',"
                    + "'" + textFieldTotalOther.getText() + "','" + textFieldTotalPaybleAmount.getText() + "','" + textFieldTotalPaidAmount.getText() + "','" + textFieldTotalDueAmount.getText() + "',"
                    + "'" + textFieldParty.getText() + "','" + textFieldContact.getText() + "',"
                    + "'" + comboBoxCurrency.getValue() + "','" + textFieldTaux.getText() + "',"
                    + "'" + textFieldRemarks.getText() + "')";
            int rs = stmt.executeUpdate(query);

            String posDetailsQuery = "insert into sale_details (order_id,ITEM_ID,ITEM_NAME,UOM,QUANTITY,PRICE,AMOUNT) ";
            int count = 0;
            for (Item item : tableViewItem.getItems()) {
                posDetailsQuery += "select " + orderId + ",'" + item.getItemId() + "','" + item.getItem() + "','" + item.getUom() + "'," + item.getQuantity() + "," + item.getPrice() + "," + item.getAmount() + " from dual ";
                if (count != (tableViewItem.getItems().size() - 1)) {
                    posDetailsQuery += "union all ";
                }
                count++;
            }
            ResultSet record = stmt.executeQuery(posDetailsQuery);

            clearFooterForm();
            Window owner = buttonSave.getScene().getWindow();

            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Information",
                    "A record has been updated successfully.");

            Scene scene = (Scene) ((Node) event.getSource()).getScene();
            Parent parent;
            try {
                parent = FXMLLoader.load(getClass().getResource("/view/AddSalesView.fxml"));
                BorderPane borderPane = (BorderPane) scene.lookup("#borderPane");
                borderPane.setCenter(parent);
            } catch (IOException ex) {
                Logger.getLogger(ListSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clearWholeForm() {
        clearHeaderForm();
        clearFooterForm();
        textFieldItem.requestFocus();
    }

    private void clearFooterForm() {
        tableViewItem.getItems().clear();
        textFieldTotalAmount.clear();
        textFieldTotalQuantity.clear();
        textFieldParty.clear();
        textFieldTaux.clear();
        textFieldContact.clear();
        textFieldRemarks.clear();
        textFieldTotalAmount.clear();
        textFieldTotalDueAmount.clear();
        textFieldTotalOther.clear();
        textFieldTotalPaidAmount.clear();
        textFieldTotalPaidAmount.clear();
        textFieldTotalPaybleAmount.clear();
        textFieldTotalQuantity.clear();
        date.getEditor().clear();
    }

    private void setCustomer() {
    }

    @FXML
    private void searchCustomer() {
        String searchText = textFieldParty.getText();
        if (searchText != null && searchText.length() > 2) {
            try {
                Statement stmt = con.createStatement();
                String query = "select * from customers where upper(name) LIKE upper(?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, "%" + searchText + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    customers.add(rs.getString("NAME"));
                }
                Set<String> filteredAutoCompletions = new HashSet<>(customers);
                provider1.clearSuggestions();
                provider1.addPossibleSuggestions(filteredAutoCompletions);
            } catch (SQLException ex) {
//                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void viewInvoice(long id) throws SQLException {
        String query = "select * from sales where order_id = '" + id + "'";
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("party_name"));
            textFieldTotalQuantity.setText(String.valueOf(rs.getFloat("total_quantity")));
            textFieldTotalAmount.setText(String.valueOf(rs.getFloat("total_amount")));
            textFieldTotalOther.setText(String.valueOf(rs.getFloat("other_amount")));
            textFieldTotalPaybleAmount.setText(String.valueOf(rs.getFloat("total_payble_amount")));
            textFieldTotalPaidAmount.setText(String.valueOf(rs.getFloat("total_paid_amount")));
            textFieldTotalDueAmount.setText(String.valueOf(rs.getFloat("total_due_amount")));
            textFieldParty.setText(rs.getString("party_name"));
            textFieldContact.setText(rs.getString("party_contact"));
            comboBoxCurrency.setValue(rs.getString("currency"));
            textFieldTaux.setText(String.valueOf(rs.getFloat("taux")));
            textFieldRemarks.setText(rs.getString("remarks"));
            date.setValue(new java.sql.Date(rs.getDate("invoice_date").getTime()).toLocalDate());
            query = "select * from sale_details where order_id = '" + id + "'";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println(query);
            while (rs.next()) {
                tableViewItem.getItems().add(new Item(rs.getString("item_name"), rs.getString("uom"),
                        rs.getFloat("quantity"), rs.getFloat("price"), rs.getFloat("amount"), rs.getString("location"), rs.getLong("item_id"))
                );
            }
        }

    }
}
