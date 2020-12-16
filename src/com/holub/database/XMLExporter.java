package com.holub.database;

import java.io.*;
import java.util.Iterator;

public class XMLExporter implements Table.Exporter{
	private final Writer out;
	private int width, height;
	private boolean tableH = true;
	private String tableName;
	private String[] columnNames;
	
	public XMLExporter(Writer out) {
		this.out = out;
	}
	
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException
	{
		this.width = width;
		this.height = height;
		this.tableName = tableName;
		this.columnNames = new String[width];
		storeRow(columnNames);
		
	}
	
	
	public void storeRow(Iterator data) throws IOException{
	int i = width;
    
    if (!tableH) 
       out.write("\t<"+ tableName +">\n");
    
    while( data.hasNext() ){   
    	
    	Object datum = data.next();
    
       if (tableH) { 
          if( datum != null )   
             columnNames[i - 1] = datum.toString() ; 
       } 
       else {
          if( i > 0 ) 
             out.write("\t\t<" + columnNames[i - 1] + ">");
          
          if( datum != null )   
             out.write( datum.toString() );
 
          if( i > 0 )
             out.write("</" + columnNames[i - 1] + ">\n");
          
       }
       
       i = i - 1;

    }
    
    if (!tableH)
       out.write("\t</"+ tableName +">\n");
    
    tableH = false;
 }
	
	public void startTable() throws IOException {out.write("<root>\n");}
	public void endTable() throws IOException{out.write("</root>\n");}
}
