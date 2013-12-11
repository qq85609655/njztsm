package com.sklay.core.listener;

public class ConsoleRecordHandler implements RecordHandler {  
    @Override  
    public void handleRecord(Record record) {  
        System.out.println("@@@@@@@");  
        System.out.println(record.getRecord());  
    }  
} 
