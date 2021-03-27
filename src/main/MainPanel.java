/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Ramesh Godara
 */
public class MainPanel extends Application {
    
    public void start(Stage stage) throws Exception {
        
//        Parent root = FXMLLoader.load(getClass().getResource("/view/ListSalesInvoiceView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPanelView.fxml"));
        
//        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);
       
        
        stage.setScene(scene);
        stage.setTitle("Store POS - Powered by Ramesh Godara");
        stage.getIcons().add(new Image("/asset/icon.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
