package DAL;

import List.ListSupplyer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by fodil
 */
public class Supplyer {

    public String id;
    public String supplyerName;
    public String supplyerContactNumber;
    public String supplyerAddress;
    public String supplyerDescription;
    public String creatorId;
    public String date;

//    public List<ListSupplyer> rowSupplyer;
    public ObservableList<ListSupplyer> supplyerDetails = FXCollections.observableArrayList();




}
