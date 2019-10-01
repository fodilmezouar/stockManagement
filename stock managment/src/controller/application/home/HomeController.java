/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.home;

import Getway.CurrentProductGetway;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author rifat
 */
public class HomeController implements Initializable {
    @FXML
    private Label lblTotalItem;
    @FXML
    private Label lblStockValue;
    @FXML
    private Label lblTotalSupplyer;
    @FXML
    private Label lblTotalEmployee;
    
    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private Label lblTotalSell;
    @FXML
    private Label lblSellValue;
    @FXML
    private Label lblOrgName;
    private Text txtOrgAddress;
    private Text txtorgPhoneNumber;
    
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();
    @FXML
    private Label chiffreJ;
    @FXML
    private DatePicker dateP;
    @FXML
    private ToggleGroup radio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateP.setValue(LocalDate.now());
        valueCount();
        totalCount();
        
    }    
    
    public void valueCount(){
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select sum(PursesPrice) from "+db+".Products");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblStockValue.setText(rs.getString(1));
            }con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void totalCount(){
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select count(*) from "+db+".Sell");
            rs = pst.executeQuery();
            while (rs.next()) {
                lblTotalSell.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select count(*) from "+db+".Supplyer");
            rs = pst.executeQuery();
            while(rs.next()){
                lblTotalSupplyer.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select count(*) from "+db+".Products");
            rs = pst.executeQuery();
            while(rs.next()){
                lblTotalItem.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select sum(TotalPrice) from "+db+".Sell");
            rs = pst.executeQuery();
            while(rs.next()){
                lblSellValue.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select count(*) from "+db+".User");
            rs = pst.executeQuery();
            while(rs.next()){
                lblTotalEmployee.setText(rs.getString(1));
            }
            pst = con.prepareStatement("select SUM(TotalPrice) from "+db+".Sell where DATE(SellDate) = CURRENT_DATE ");
            rs = pst.executeQuery();
            String chiffre = "0";
            while(rs.next()){
                if(rs.getString(1) != null)
                    chiffre = rs.getString(1);
                chiffreJ.setText(chiffre + " Da");
            }
            pst =con.prepareStatement("select * from "+db+".Organize where Id=1");
            rs = pst.executeQuery();
            while(rs.next()){
                lblOrgName.setText(rs.getString(2));
                txtOrgAddress.setText(rs.getString(5));
                txtorgPhoneNumber.setText(rs.getString(4));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGetway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionDate(ActionEvent event) {
        action(null);
    }

    @FXML
    private void action(ActionEvent event) {
         con = dbCon.geConnection();
        try {
           
            RadioButton selectedRadioButton = (RadioButton) radio.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            if("Jour".equals(toogleGroupValue)){
                LocalDate date = dateP.getValue();
                String query ="select SUM(TotalPrice) from "+db+".Sell "
                        +"WHERE YEAR(SellDate) = YEAR('" + date + "') AND "
                    + "MONTH(SellDate) = MONTH('" + date + "') AND DAY(SellDate) = DAY('" + date + "')";
                pst = con.prepareStatement(query);
                     
                rs = pst.executeQuery();
                 String chiffre = "0";
            while(rs.next()){
                if(rs.getString(1) != null)
                    chiffre = rs.getString(1);
                
            }
            chiffreJ.setText(chiffre + " Da");

    
            }
            if("Mois".equals(toogleGroupValue)){
                LocalDate date = dateP.getValue();
                  String query ="select SUM(TotalPrice) from "+db+".Sell "
                        +"WHERE YEAR(SellDate) = YEAR('" + date + "') AND "
                    + "MONTH(SellDate) = MONTH('" + date + "')" ;
                pst = con.prepareStatement(query);
                     
                rs = pst.executeQuery();
                 String chiffre = "0";
            while(rs.next()){
                if(rs.getString(1) != null)
                    chiffre = rs.getString(1);
                
            }
            chiffreJ.setText(chiffre + " Da");
                
            }
            if("Annee".equals(toogleGroupValue)){
                LocalDate date = dateP.getValue();
                  String query ="select SUM(TotalPrice) from "+db+".Sell "
                        +"WHERE YEAR(SellDate) = YEAR('" + date + "')";
                pst = con.prepareStatement(query);
                     
                rs = pst.executeQuery();
                 String chiffre = "0";
            while(rs.next()){
                if(rs.getString(1) != null)
                    chiffre = rs.getString(1);
                
            }
            chiffreJ.setText(chiffre + " Da");
                
            }
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
