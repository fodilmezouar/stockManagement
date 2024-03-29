/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.sell;

import BLL.SellCartBLL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAL.CurrentProduct;
import Getway.CurrentProductGetway;
import Getway.CustomerGetway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;
import DAL.Customer;
import DAL.SellCart;
import Getway.SellCartGerway;
import List.BeanSell;
import List.ListCustomer;
import List.ListPreSell;
import StoreManage.PrintReport;
import controller.application.settings.OrgSettingController;
import custom.CustomTf;
import custom.RandomIdGenarator;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.controlsfx.control.textfield.TextFields;
/**
 *
 * @author fodil
 */
public class NewSellController implements Initializable {

    public Button btnAddCustomer;
        DBConnection dbCon = new DBConnection();
      DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();
    userNameMedia nameMedia;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private HashMap<Integer,String> productHm= new HashMap<>();

    String userId;
    @FXML
    private MenuButton mbtnCustomer;
    @FXML
    private TableView<ListCustomer> tblCustomerSortView;
    @FXML
    private TableColumn<Object, Object> tblClmCustomerName;
    @FXML
    private TableColumn<Object, Object> tblClmCustomerPhoneNo;
    @FXML
    private TextField tfCustomerSearch;
    @FXML
    private Button btnClose;

    String customerId;
    @FXML
    public TextField tfProductId;
    @FXML
    private TableView<ListPreSell> tblSellPreList;
    @FXML
    private TableColumn<Object, Object> tblClmSellId;
    @FXML
    private TableColumn<Object, Object> tblClmProductId;
    @FXML
    private TableColumn<Object, Object> tblClmSellPrice;
    @FXML
    private TableColumn<Object, Object> tblClmCustomer;
    @FXML
    private TableColumn<Object, Object> tblClmSolledBy;
    @FXML
    private TableColumn<Object, Object> tblClmQuantity;
    @FXML
    private TableColumn<Object, Object> tblClmTotalPrice;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblCurrentQuantity;
    @FXML
    private TextField tfSellPrice;
    @FXML
    private Label lblPursesPrice;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblNetCost;
    private Label lblDiscount;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField tfSupplyer;
    @FXML
    private TextField tfBrand;
    @FXML
    private TextField tfCatagory;
    @FXML
    private Button btnAddToChart;
    @FXML
    private Button btnSell;
    @FXML
    private Label lblPursesDate;
    int quantity;
    @FXML
    private Label lblTotalItems;
    @FXML
    private TextField tfProductName;
    @FXML
    private Button btnClearSelected;
    @FXML
    private Label lblSellId;
    @FXML
    private Button btnprint;

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;

        
    }

    Customer customer = new Customer();
    CustomerGetway customerGetway = new CustomerGetway();
    CurrentProduct currrentProduct = new CurrentProduct();
    CurrentProductGetway currentProductGetway = new CurrentProductGetway();
    SellCart sellCart = new SellCart();
    SellCartGerway sellCartGerway = new SellCartGerway();
    SellCartBLL scbll = new SellCartBLL();
    CustomTf ctf = new CustomTf();

    ObservableList<ListPreSell> preList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearAll();
        autoComplete();

    }
private void autoComplete(){
    SQL sq = new SQL();
            productHm = sq.productId("Products");
            TextFields.bindAutoCompletion(tfProductId,productHm.values());
            
       
    }
    @FXML
    private void tblCustomerOnClick(MouseEvent event) {
        mbtnCustomer.setText(tblCustomerSortView.getSelectionModel().getSelectedItem().getCustomerName());
        customerId = tblCustomerSortView.getSelectionModel().getSelectedItem().getId();
        System.out.println(customerId);
    }

    @FXML
    private void mbtnCustomerOnClicked(MouseEvent event) {
        customer.customerName = tfCustomerSearch.getText().trim();
        tblCustomerSortView.setItems(customer.customerList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmCustomerPhoneNo.setCellValueFactory(new PropertyValueFactory<>("customerContNo"));
        customerGetway.searchView(customer);
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tfCustomerSearchOnKeyReleased(KeyEvent event) {
        customer.customerName = tfCustomerSearch.getText().trim();
        tblCustomerSortView.setItems(customer.customerList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmCustomerPhoneNo.setCellValueFactory(new PropertyValueFactory<>("customerContNo"));
        customerGetway.searchView(customer);
    }

    @FXML
    public void tfProductIdOnAction(ActionEvent event) {
        if (tfProductId.getText().isEmpty()) {
            clearAll();
        } else {
            currrentProduct.productId = tfProductId.getText().trim();
            currentProductGetway.sView(currrentProduct);
            lblUnit.setText(currrentProduct.unitName);
            lblCurrentQuantity.setText(currrentProduct.quantity);
            lblPursesPrice.setText(currrentProduct.pursesPrice);
            tfBrand.setText(currrentProduct.brandName);
            tfSupplyer.setText(currrentProduct.supplierName);
            tfCatagory.setText(currrentProduct.catagoryName);
            lblPursesDate.setText(currrentProduct.date);
            tfProductName.setText(currrentProduct.productName);
            tfSellPrice.setText(currrentProduct.sellPrice);
        }
    }

    @FXML
    private void btnAddToChartOnAction(ActionEvent event) {
        if(inNotNull()){
        preList.add(new ListPreSell(currrentProduct.id, currrentProduct.productId,currrentProduct.productName, customerId, currrentProduct.pursesPrice, tfSellPrice.getText(), lblCurrentQuantity.getText(), tfQuantity.getText(), lblNetCost.getText(), currrentProduct.date, userId, LocalDateTime.now().toString()));
        viewAll();
        btnprint.setDisable(false);
        sumTotalCost();
        clearAll();
        }

    }

    private void sumTotalCost() {
        tblSellPreList.getSelectionModel().selectFirst();
        int sum = 0;
        int items = tblSellPreList.getItems().size();

        for (int i = 0; i < items; i++) {
            tblSellPreList.getSelectionModel().select(i);
            String selectedItem = tblSellPreList.getSelectionModel().getSelectedItem().getTotalPrice();
            int newFloat = Integer.parseInt(selectedItem);
            sum = sum + newFloat;
        }
        String totalCost = String.valueOf(sum);
        lblTotal.setText(totalCost);
        System.out.println("Total:" + sum);
        String totalItem = String.valueOf(items);
        lblTotalItems.setText(totalItem);
    }

    public void viewAll() {
        tblSellPreList.setItems(preList);
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        tblClmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    @FXML
    private void btnSellOnAction(ActionEvent event) {
        if(!tblSellPreList.getItems().isEmpty()){
            System.out.println(lblSellId.getText());
            int indexs = tblSellPreList.getItems().size();
            for (int i = 0; i < indexs; i++) {
                tblSellPreList.getSelectionModel().select(i);
                sellCart.Id = tblSellPreList.getSelectionModel().getSelectedItem().getId();
                sellCart.productID = tblSellPreList.getSelectionModel().getSelectedItem().getId();
                sellCart.sellID = lblSellId.getText();
                sellCart.customerID = customerId;
                sellCart.pursesPrice = tblSellPreList.getSelectionModel().getSelectedItem().getPursesPrice();
                sellCart.sellPrice = tblSellPreList.getSelectionModel().getSelectedItem().getSellPrice();
                sellCart.quantity = tblSellPreList.getSelectionModel().getSelectedItem().getQuantity();
                sellCart.oldQuentity = tblSellPreList.getSelectionModel().getSelectedItem().getOldQuantity();
                sellCart.totalPrice = tblSellPreList.getSelectionModel().getSelectedItem().getTotalPrice();
                sellCart.sellerID = userId;
                scbll.sell(sellCart);
                System.out.println("Old Quentity:" + tblSellPreList.getSelectionModel().getSelectedItem().getOldQuantity());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Soled");
            alert.setContentText("Soled Sucessfuly");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

           tblSellPreList.getItems().clear();
           lblTotal.setText(null);

            System.out.println("Customer ID: " + customerId);
        }else{
            System.out.println("EMPTY");
        }

    }

    public void clearAll() {
        tfBrand.clear();
        tfProductId.clear();
        tfCatagory.clear();
        tfSellPrice.clear();
        tfSupplyer.clear();
        tfQuantity.clear();
        tfProductName.clear();
        lblCurrentQuantity.setText(null);
        lblNetCost.setText(null);
        lblPursesPrice.setText(null);
        lblUnit.setText(null);
        lblPursesDate.setText(null);
    }

    @FXML
    private void tfQuantityOnAction(KeyEvent event) {
        if (!tfQuantity.getText().isEmpty()) {
            String givenQuentity = tfQuantity.getText();
            int givenQinInt = Integer.parseInt(givenQuentity);
            String currentQuentity = lblCurrentQuantity.getText();
            int currentQuentiInt = Integer.parseInt(currentQuentity);
            if (givenQinInt > currentQuentiInt) {
                System.out.println("BIG");
                tfQuantity.clear();
                lblNetCost.setText("0");
            } else {
                quantity = Integer.parseInt(tfQuantity.getText());
                int sellPrice = Integer.parseInt(tfSellPrice.getText());
                String netPrice = String.valueOf(sellPrice * quantity);
                lblNetCost.setText(netPrice);
            }
        } else {
            lblNetCost.setText("0");
        }
    }

    @FXML
    private void tfSellPriceOnAction(KeyEvent event) {
        System.out.println("PRESSES");

        if (!tfSellPrice.getText().isEmpty()) {
            String quentity = tfQuantity.getText();
            int intQuentity = Integer.parseInt(quentity);
            String sellPrice = tfSellPrice.getText();
            int fSellPrice = Integer.parseInt(sellPrice);
            String sTotalPrice = String.valueOf(intQuentity * fSellPrice);
            lblNetCost.setText(sTotalPrice);
            System.out.println(sTotalPrice);
        } else {
            lblNetCost.setText("0");
        }
    }

    @FXML
    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        System.out.println(userId);
        AddCustomerController acc = new AddCustomerController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/application/sell/AddCustomer.fxml"));
        try {
            fXMLLoader.load();
            Parent parent = fXMLLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddCustomerController addCustomerController = fXMLLoader.getController();
            media.setId(userId);
            addCustomerController.setNameMedia(nameMedia);
            addCustomerController.lblCustomerContent.setText("ADD CUSTOMER");
            addCustomerController.btnUpdate.setVisible(false);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void btnClearSelectedOnAction(ActionEvent event) {
        if(tblSellPreList.getItems().size() != 0){
            System.out.println("Clicked");
        tblSellPreList.getItems().removeAll(tblSellPreList.getSelectionModel().getSelectedItems());
        sumTotalCost();
        }else{
            System.out.println("EMPTY");
        }
        

    }

    public void genarateSellID() {
        String id = RandomIdGenarator.randomstring();
        if (id.matches("001215")) {
            String nId = RandomIdGenarator.randomstring();
            lblSellId.setText(nId);
        } else {
            lblSellId.setText(id);
        }

    }

    public boolean inNotNull() {
        boolean isNotNull = false;
        
        if (mbtnCustomer.getText().matches("Sélectionner un client")||tfSellPrice.getText() == null || tfQuantity.getText().trim().matches("")) {
//            Dialogs.create().title("").masthead("ERROR").message("Please fill requere field").styleClass(org.controlsfx.dialog.Dialog.STYLE_CLASS_UNDECORATED).showError();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please fill all requre field");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            return isNotNull;
        } else {
            isNotNull = true;
        }
        return isNotNull;
    }

    @FXML
    private void btnPrintOnAction(ActionEvent event) throws IOException {
        List<String> listItems = new ArrayList<String>();
        con = dbCon.geConnection();
        BufferedImage bufImg=null;
        try {
            pst = con.prepareStatement("select * from "+db+".Organize where Id=?");
            pst.setString(1, "1");
            
            String orgName = null,registre = null,orgnumber = null,orgadress = null;
            rs = pst.executeQuery();
            while (rs.next()) {
                orgName=rs.getString(2);
                registre=rs.getString(3);
                orgnumber=rs.getString(4);
                orgadress=rs.getString(5);

                Blob blob = rs.getBlob(6);
                
               
            }
            listItems.add(lblTotal.getText()); 
            listItems.add(orgName);
            listItems.add(registre);
            listItems.add(orgnumber);
            listItems.add(orgadress);
            listItems.add(lblSellId.getText());
            listItems.add(mbtnCustomer.getText());
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(tblSellPreList.getItems());
        PrintReport view  = new PrintReport();
        view.previewFacture(itemsJRBean,listItems);
    }
}
