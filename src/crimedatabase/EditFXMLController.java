/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crimedatabase;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Purusharth
 */
public class EditFXMLController implements Initializable {

    private  String currentOp="";
    private  int authLevel=-1;
    @FXML Tab add_tab;
    @FXML Tab delete_tab;
    @FXML Tab modify_tab;
    @FXML TabPane tp;
    @FXML Button addBtn;
    @FXML TextField name;String name_store="";
    @FXML TextField age;String age_store="";
    @FXML TextField status;String status_store="";
    @FXML TextField eyecolor;String eyecolor_store="";
    @FXML TextField haircolor;String haircolor_store="";
    @FXML TextField weight;String weight_store="";
    @FXML TextField height;String height_store="";
    @FXML TextField dna;String dna_store="";
    @FXML TextField scars;String scars_store="";
    //@FXML TextField ha;String ha_dtore="";
    @FXML DatePicker doc;String doc_store="";
    @FXML TextField crime;String crime_store="";
    @FXML TextField coc;String coc_store="";
    @FXML TextField lid;String lid_store="";
    @FXML TextField jid;String jid_store="";
    @FXML DatePicker doc_del;
    @FXML TextField ns_del;
    @FXML TextField coc_del;
    
    @FXML Label addBtnCheck;
    @FXML Button delete;
    @FXML Button fr;
    
    @FXML TableColumn col_cid;
    @FXML TableColumn col_name;
    @FXML TableColumn col_age;
    @FXML TableColumn col_crime;
    @FXML TableColumn col_coc;
    @FXML TableColumn col_doc;
    @FXML TableColumn col_status;
    @FXML TableColumn col_crimeid;
    
    @FXML TableView table_del;
    
    private ObservableList<criminal_del> data;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        delete.setOnAction(e->{
            criminal_del del=(criminal_del)table_del.getSelectionModel().getSelectedItem();
            System.out.println(del.name+" is the ansewr  "+del.crimeid);
            JdbcDemo a = new JdbcDemo(); 
            a.getConnection("puru","puru");
            a.deleteCriminal(Integer.parseInt(del.cid), Integer.parseInt(del.crimeid));
            
        });
        fr.setOnAction(e->{
            initTable();
            data=FXCollections.observableArrayList();
            JdbcDemo a = new JdbcDemo(); 
            a.getConnection("puru","puru");

            String date="";
            if(doc_del.getValue()==null){
                System.out.println("Here is prob");
                date="";
            }else{
                date=doc_del.getValue().toString();
                System.out.println("Date present");
            }
           ArrayList<Integer> f = a.searchByName(ns_del.getText(), 1, coc_del.getText(), date);
            int i=0;
            
            for(i=0;i<f.size();i++){
                Criminal c=a.returnEverything(f.get(i));
                criminal_del e1=new criminal_del();
                e1.setName(c.getName());
                e1.setAge(c.getAge());
                e1.setCid(""+c.getCid());
                e1.setCity(c.getPlaceofcrime());
                e1.setCrime(c.getType());
                e1.setDateOfCrime(c.getDateOfCrime());
                e1.setStatus(c.getStatus());
                e1.setCrimeid(""+c.getCrimeid());
                System.out.println("id-"+e1.getCid()+" "+e1.getDateOfCrime());
                data.add(e1);
            }
            table_del.setItems(data);
            
            
        });
        addBtn.setOnAction(e->{
            int count=0;
            Criminal c = new Criminal();
            System.out.println("name-"+name.getText());
            c.status=status.getText();
            c.eye=eyecolor.getText();
            c.hair=haircolor.getText();
            if(!age.getText().isEmpty())
                c.age=Integer.parseInt(age.getText());
            if(!lid.getText().isEmpty())
                c.lid=Integer.parseInt(lid.getText());
            if(!jid.getText().isEmpty())
                c.jid=Integer.parseInt(jid.getText());
            
            if(!weight.getText().isEmpty())
                c.weight=Integer.parseInt(weight.getText());
            if(!height.getText().isEmpty())
                c.height=Integer.parseInt(height.getText());
            c.dna=dna.getText();
            c.special=scars.getText();
            if(!name.getText().isEmpty()){
                count++;
                c.name=name.getText();
            }
            if(!coc.getText().isEmpty()){
                count++;
                c.placeofcrime=coc.getText();
            }
            if(!doc.getValue().toString().isEmpty()){
                count++;
                System.out.println("date-"+doc.getValue().toString());
                c.dateOfCrime=doc.getValue().toString();
            }
            if(!crime.getText().isEmpty()){
                count++;
                c.type=crime.getText();
            }
            if(count<4){
                addBtnCheck.setText("fill the form properly");
            }
            else{
                addBtnCheck.setText("DONE!");
                JdbcDemo a = new JdbcDemo();
                a.addCriminal(c);
            }
        });
    }
    public void initData(String op,int auth){
        this.currentOp=op;
        this.authLevel=auth;
        if(currentOp.equals("add")){
            delete_tab.setDisable(true);
            modify_tab.setDisable(true);
            tp.getSelectionModel().select(add_tab);

        }else if(currentOp.equals("delete")){
            add_tab.setDisable(true);
            modify_tab.setDisable(true);
            tp.getSelectionModel().select(delete_tab);
        }else{
            add_tab.setDisable(true);
            delete_tab.setDisable(true);
            tp.getSelectionModel().select(modify_tab);
        }
        
    }

    private void initTable() {
        assert table_del != null : "table not set";
        col_cid.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Cid"));
        col_name.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Name"));
        col_age.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Age"));
        col_crime.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Crime"));
        col_doc.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("DateOfCrime"));
        col_coc.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("City"));
        col_status.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Status"));
        col_crimeid.setCellValueFactory(new PropertyValueFactory<criminal_del,String>("Crimeid"));
        
    }
    
    
}

/*

package crimedatabase;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class EditFXMLController implements Initializable {

  
    @FXML Label disp;
    private String currentOp;
    @FXML TextField name;String name_store="";
    @FXML TextField age;String age_store="";
    @FXML TextField status;String status_store="";
    @FXML TextField eyecolor;String eyecolor_store="";
    @FXML TextField haircolor;String haircolor_store="";
    @FXML TextField weight;String weight_store="";
    @FXML TextField height;String height_store="";
    @FXML TextField dna;String dna_store="";
    @FXML TextField scars;String scars_store="";
    @FXML TextField ha;String ha_dtore="";
    @FXML DatePicker doc;String doc_store="";
    @FXML TextField crime;String crime_store="";
    @FXML TextField coc;String coc_store="";
    @FXML TextField lid;String lid_store="";
    @FXML TextField jid;String jid_store="";
    @FXML Button addBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addBtn.setOnAction(e->{
            if(checkFillA()){
                System.out.println("Returned True!!");
                InsertValues();
            }
            else{
                disp.setText("Fill the mandatory fields !!");
            }
        });
    }
    
  void initData(String op) {
    this.currentOp=op;
    disp.setText(op);
  }
    private boolean checkFillA() {
        if(name_store.isEmpty() || coc_store.isEmpty() || doc_store.isEmpty() || crime_store.isEmpty()){
            return false;
        }
        else return true;
    }

    private void InsertValues() {
           //Insert the vallues into the dbms from here
    }
    
}



*/