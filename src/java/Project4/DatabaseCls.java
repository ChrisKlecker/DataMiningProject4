/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Klecker
 */
public class DatabaseCls implements Cloneable {
    
    private List<Integer> ARecords;
    private List<String> AttributesData;
    private Integer RecordNumber;
    private Integer ZRecordNumber;

    public Integer getZRecordNumber() {
        return ZRecordNumber;
    }

    public void setZRecordNumber(Integer ZRecordNumber) {
        this.ZRecordNumber = ZRecordNumber;
    }

    public List<String> getAttributesData() {
        return AttributesData;
    }

    public void setAttributesData(List<String> AttributesData) {
        this.AttributesData = AttributesData;
    }

    public Integer getRecordNumber() {
        return RecordNumber;
    }

    public void setRecordNumber(Integer RecordNumber) {
        this.RecordNumber = RecordNumber;
    }

    public List<String> getAttributes() {
        return AttributesData;
    }

    public void setAttributes(List<String> AttributesData) {
        this.AttributesData = AttributesData;
    }

    public List<Integer> getARecords() {
        return ARecords;
    }

    public void setARecords(List<Integer> ARecords) {
        this.ARecords = ARecords;
    }
    
    public DatabaseCls(DatabaseCls pCopy){
        
        List<String> NewAttributeData = new ArrayList<>();
        for(int i=0; i<pCopy.getAttributesData().size(); i++){
            NewAttributeData.add(i, pCopy.getAttributesData().get(i));
        }
        setAttributes(NewAttributeData);
        
        this.ARecords = new ArrayList<>(pCopy.ARecords);
        this.RecordNumber = pCopy.RecordNumber;    
        this.ZRecordNumber = pCopy.ZRecordNumber;
    }
    
    public DatabaseCls(String Radiation, String Vibration, String MagneticField, String Fume, String GPX){
        this.ARecords = new ArrayList<>();
        this.AttributesData = new ArrayList<>();
        this.RecordNumber = 0;
        this.ZRecordNumber = 0;
    }

    public DatabaseCls(String[] Data){
        this.ARecords = new ArrayList<>();
        this.AttributesData = new ArrayList<>();
        this.RecordNumber = 0;
        this.ZRecordNumber = 0;

        for(int i=0; i<Data.length; i++){
            this.AttributesData.add(Data[i]);
        }
    }
    
    public DatabaseCls(){
        this.ARecords = new ArrayList<>();
        this.AttributesData = new ArrayList<>();
        this.RecordNumber = 0;
        this.ZRecordNumber = 0;
    }
    
    public void RemoveAttribute(int Index){
        
        this.AttributesData.remove(Index);
    }
}

