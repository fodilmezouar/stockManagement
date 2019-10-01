package StoreManage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataBase.DBConnection;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author computeruser
 */
public class PrintReport extends JFrame {

    public void previewFacture(JRBeanCollectionDataSource itemsJRBean,List<String> liste) {
        try{
            HashMap param = new HashMap();
          param.put("dataSetsStock", itemsJRBean);
          param.put("total_tot", liste.get(0));
          param.put("orgContactNumbers", liste.get(3));
          param.put("orgContactAddress", liste.get(4));
          param.put("orgname", liste.get(1));
          param.put("numRegistre", liste.get(2));
          param.put("idinvoice", liste.get(5));
          param.put("clientName", liste.get(6));
         JasperPrint print  = JasperFillManager.fillReport("/Users/fodil/NetBeansProjects/stock managment/src/StoreKeeper/stockkeeper.jasper", param,new JREmptyDataSource());
            
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );           
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        
        }catch(JRException e){
            System.out.println(e);
        }
    
    
    }
    
}
