/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author David Klecker
 */
public class RoughSetsCls {
      
    public List<DatabaseCls> Data;
    public String DataFile; 
    private Integer NumberOfAttributes;
    private ArrayList<String> Attributes;
    public ArrayList<LListsCls> LList;
    public ArrayList<LListsCls> RList;
    public ArrayList<LListsCls> LowerLists;
    public ArrayList<String> LocalCertainRules;
    
    public ArrayList<String> getAttributes() {
        return Attributes;
    }

    public void setAttributes(ArrayList<String> Attributes) {
        this.Attributes = Attributes;
    }

    
    public Integer getNumberOfAttributes() {
        return NumberOfAttributes;
    }

    public void setNumberOfAttributes(Integer NumberOfAttributes) {
        this.NumberOfAttributes = NumberOfAttributes;
    }
    
    public RoughSetsCls(){
        
        this.Data = new ArrayList<>();
        this.DataFile = "";
        this.NumberOfAttributes = 0;
        Attributes = new ArrayList<>();
        LList = new ArrayList<>();
        LocalCertainRules = new ArrayList<>();
    }
            
    public void ProcessRequest(HttpServletRequest request) throws IOException{
        
        DataFile = request.getServletContext().getRealPath("/") + "testDB.csv";
        
        GetDatabaseData();
        RemoveCopiesOfRows();
        RemoveAttributes();
        CreateLLists();
        CreateRLists();
        GetLowerLists();
        CreateLocalCertainRules();
    }
    
    public void GetDatabaseData() throws FileNotFoundException, IOException{

        String line = "";
        int index = 0;
        
        BufferedReader br = new BufferedReader(new FileReader(DataFile));
        while((line = br.readLine()) != null) {
            
            String[] str = line.split(",");
            if(!isInteger(str[0]) && index == 0){                
                NumberOfAttributes = str.length;
                for(int i=0; i<str.length; i++){
                    Attributes.add(str[i]);
                }
            }   
            else{
                DatabaseCls db = new DatabaseCls(str);
                db.setRecordNumber(index);
                Data.add(db);
            }
            index++;
        }
                
        br.close();
    }

    public void RemoveCopiesOfRows(){
        
        List<DatabaseCls> NewData = new ArrayList<>();
        List<DatabaseCls> CopyData = new ArrayList<>(Data);

        while(CopyData.size() > 0){
            
            DatabaseCls x = CopyData.get(0);
            List<Integer> ARecords = new ArrayList<>();
            
            if(!ARecords.contains(x.getRecordNumber()))
                ARecords.add(x.getRecordNumber());
            
            CopyData.remove(0);
            
            for(int i=0; i<CopyData.size(); i++){

                DatabaseCls y = CopyData.get(i);

                if(CompareRows(x, y, false)){

                    if(!ARecords.contains(y.getRecordNumber()))
                        ARecords.add(y.getRecordNumber());
                
                    CopyData.remove(i--);
                }  
            }

            x.setARecords(new ArrayList<>(ARecords));
            x.setZRecordNumber(0);
            NewData.add(x);
        }
        
        Data = new ArrayList<>(NewData);
    }

    public static List<DatabaseCls> cloneData(List<DatabaseCls> dbList) {
        
        List<DatabaseCls> clonedList = new ArrayList<>(dbList.size());
        for (DatabaseCls db : dbList) {
            clonedList.add(new DatabaseCls(db));
        }
        return clonedList;
    }

    public void RemoveAttributes(){
    
        for(int i=0; i<getNumberOfAttributes()-1; i++){
            
            List<DatabaseCls> NewData = cloneData(Data);
                    
            for(int j=0; j<NewData.size(); j++){
            
                DatabaseCls db = NewData.get(j);
                db.getAttributes().remove(i);
            }
            
            if(!FindCopies(NewData))
                Data = new ArrayList<>(NewData);
        }
    }
    
    public boolean FindCopies(List<DatabaseCls> NewData){
        
        for(int i=0, k=1; i<NewData.size(); i++){
            
            DatabaseCls x = NewData.get(i);
            
            for(int j=i+1; j<NewData.size(); j++){
                
                DatabaseCls y = NewData.get(j);
                
                if(CompareRows(x, y, false)){
                    
                    return true;
                }  
            }
            
        }
        return false;
    }
    
    public boolean CompareRows(DatabaseCls x, DatabaseCls y, boolean ExcludeDependantAttribute){
        
        if(ExcludeDependantAttribute){
            for(int i=0; i<x.getAttributes().size()-1; i++){
                if(!x.getAttributes().get(i).equals(y.getAttributes().get(i))){
                    return false;
                }
            }
        }
        else{
            for(int i=0; i<x.getAttributes().size(); i++){
                if(!x.getAttributes().get(i).equals(y.getAttributes().get(i))){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }    
    
    public void CreateLLists(){
        
        ArrayList<Integer> ClassValuesUsed = new ArrayList<>();
        
        //Go Through DB getting all unique class values and create an array of them. Our dependant variable must always be the 
        //last attribute in our database. 
        for(int i=0; i<Data.size(); i++){
            
            DatabaseCls db = Data.get(i);
            
            int ClassValue = Integer.parseInt(db.getAttributes().get(db.getAttributes().size()-1));
            if(!ClassValuesUsed.contains(ClassValue)){
                ClassValuesUsed.add(ClassValue);
            }
        }
        Collections.sort(ClassValuesUsed);

        //Iterate through each class value and go through the DB and copy all the DatabaseObject match that class value over to NewData. 
        for(int i=0; i<ClassValuesUsed.size(); i++){
            
            ArrayList<DatabaseCls> NewDataDB = new ArrayList<>();
            for(int j=0; j<Data.size(); j++){
                
                DatabaseCls db = Data.get(j);
                int ClassValue = Integer.parseInt(db.getAttributes().get(db.getAttributes().size()-1));
                if(ClassValue == ClassValuesUsed.get(i)){
                    NewDataDB.add(db);
                }
            }
            
            LList.add(new LListsCls(NewDataDB));
        }
    }
    
    public void CreateRLists(){

        //We need to grab the first z record and check the rest of the records. If all attributes are the same 
        //EXCEPT the dependant variable which can be different then these records get stored into an RClass. 
        RList = new ArrayList<>();

        ArrayList<DatabaseCls> CopyDataDB = new ArrayList<>(Data);
        while(CopyDataDB.size() > 0){
            
            ArrayList<DatabaseCls> NewDataDB = new ArrayList<>();
            DatabaseCls db = CopyDataDB.get(0);
            NewDataDB.add(db);
            CopyDataDB.remove(0);
            
            for(int i=0; i<CopyDataDB.size(); i++){
                
                DatabaseCls db2 = CopyDataDB.get(i);
                if(CompareRows(db, db2, true)){
                    NewDataDB.add(db2);
                    CopyDataDB.remove(i--);
                }
            }
            
            RList.add(new LListsCls(NewDataDB));
        }
    }    
    
    public void GetLowerLists(){
        
        LowerLists = new ArrayList<>();

        for(int i=0; i<LList.size(); i++){

            LListsCls L = LList.get(i);
            ArrayList<DatabaseCls> DataDB = new ArrayList<>();
            ArrayList<DatabaseCls> LowerListDB = new ArrayList<>();

            for(int j=0; j<RList.size(); j++){
                
                LListsCls R = RList.get(j);
                                
                if(LContainsR(L, R)){
                    for(int k=0; k<R.getDataDB().size(); k++){
                        LowerListDB.add(R.getDataDB().get(k));
                    }
                }
            }
            
            LowerLists.add(new LListsCls(LowerListDB));
        }
    }
    
    public boolean LContainsR(LListsCls L, LListsCls R){
        
        ArrayList<DatabaseCls> RdataList = R.getDataDB();
        for(int i=0; i<RdataList.size(); i++){
            
            DatabaseCls rDB = RdataList.get(i);
            ArrayList<DatabaseCls> LdataList = L.getDataDB();
            
            boolean FoundInL = false;
            for(int j=0; j<LdataList.size(); j++){
                
                DatabaseCls lDB = LdataList.get(j);
                                        
                if(rDB.getRecordNumber() == lDB.getRecordNumber()){
                    FoundInL = true;
                    break;
                }
            }
            
            if(FoundInL == false)
                return false;                     
        }
        
        return true;
    }
    
    public void CreateLocalCertainRules(){
        
        for(int i=0; i<this.LowerLists.size(); i++){
            LListsCls LLowerLists = LowerLists.get(i);
            ArrayList<DatabaseCls> dbList = LLowerLists.getDataDB();
            
            String ifStatement = "if";
            String DStatement = "";

            for(int j=0; j<dbList.size(); j++){
                
                DatabaseCls db = dbList.get(j);
                
                ifStatement += "(";
                for(int k=0; k<db.getAttributes().size(); k++){
                    
                    String str = db.getAttributes().get(k);
                    
                    if(k == db.getAttributes().size() - 1 && DStatement.isEmpty()){
                        DStatement = " then D = " + str;
                    }
                    else if(k < db.getAttributes().size() - 1){
                        ifStatement += "C" + k + " = "+ str;
                        if(k<db.getAttributes().size()-2)
                            ifStatement += "^";
                    }
                }
                if(j < dbList.size() - 1){
                    ifStatement += ") OR ";
                }
            }  
            LocalCertainRules.add(ifStatement + DStatement);
        }
    }
}
