/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.sell;

import BLL.SellCartBLL;
import DAL.CurrentProduct;
import DAL.SellCart;
import Getway.CurrentProductGetway;
import Getway.SellCartGerway;
import dataBase.SQL;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class SellUpController implements Initializable {

    @FXML
    private TextField Total;
    @FXML
    private TextField prixVent;
    @FXML
    private TextField qunatit;
    @FXML
    private TextField productId;
    @FXML
    private TextField sellerId;
    @FXML
    private Button btnClose;
    
    int quantity;
    public String idSell;
    public String cusstumer_Name;
    public  String product_Id;
    public String quantite;
    @FXML
    private Label lblCurrentQuantity;
    CurrentProduct currentProduct = new CurrentProduct();
    CurrentProductGetway currentProductGetway = new CurrentProductGetway();
    SellCart currentSell = new SellCart();
    SellCartGerway currentSellGetway = new SellCartGerway();
    SellCartBLL sellBll = new SellCartBLL();
    SQL sql = new SQL();
    @FXML
    private TextField clientNam;
    @FXML
    private TextField productName;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tfQuantityOnAction(KeyEvent event) {
        if (!qunatit.getText().isEmpty()) {
            String givenQuentity = qunatit.getText();
            int givenQinInt = Integer.parseInt(givenQuentity);
            String currentQuentity = lblCurrentQuantity.getText();
            int currentQuentiInt = Integer.parseInt(currentQuentity);
            if (givenQinInt > currentQuentiInt) {
                System.out.println("BIG");
                qunatit.clear();
                Total.setText("0.0");
            } else {
                quantity = Integer.parseInt(qunatit.getText());
                float sellPrice = Float.parseFloat(prixVent.getText());
                String netPrice = String.valueOf(sellPrice * quantity);
                Total.setText(netPrice);
            }
        } else {
            Total.setText("0.0");
        }
    }
   
    public void showView(){
        
        
        currentProductGetway.selectedView(currentProduct);
        currentSellGetway.selectView(currentSell);
        productName.setText(currentProduct.productName);
        clientNam.setText(cusstumer_Name);
        int qunt = Integer.parseInt(currentSell.quantity)+Integer.parseInt(currentProduct.quantity);
        lblCurrentQuantity.setText(qunt+"");
        qunatit.setText(currentSell.quantity);
        prixVent.setText(currentSell.sellPrice);
        Total.setText(currentSell.totalPrice);
        
    }
    @FXML
    private void modifier(ActionEvent event) {
        currentSell.sellPrice = prixVent.getText();
        currentSell.totalPrice = Total.getText();
        currentSell.quantity = qunatit.getText();
        currentSell.oldQuentity = lblCurrentQuantity.getText();
        sellBll.updat(currentSell);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Modifier");
            alert.setContentText("modifier avec success");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
         Stage stage = (Stage) btnClose.getScene().getWindow();
         stage.close();
    }

    @FXML
    private void tfSellPriceOnAction(KeyEvent event) {
        if (!prixVent.getText().isEmpty()) {
            String quentity = qunatit.getText();
            int intQuentity = Integer.parseInt(quentity);
            String sellPrice = prixVent.getText();
            float fSellPrice = Float.parseFloat(sellPrice);
            String sTotalPrice = String.valueOf(intQuentity * fSellPrice);
            Total.setText(sTotalPrice);
            System.out.println(sTotalPrice);
        } else {
            Total.setText("0.0");
        }
    }
    
}
