/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4;

import java.util.ArrayList;

/**
 *
 * @author David Klecker
 */
public class LListsCls {
    
    private ArrayList<DatabaseCls> DataDB;

    public ArrayList<DatabaseCls> getDataDB() {
        return DataDB;
    }

    public void setDataDB(ArrayList<DatabaseCls> DataDB) {
        this.DataDB = DataDB;
    }
    
    public LListsCls(ArrayList<DatabaseCls> DataList){
        
        DataDB = new ArrayList<>(DataList);
    }
}
