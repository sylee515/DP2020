package com.holub.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import com.holub.tools.ArrayIterator;

public class XMLImporter implements Table.Importer{
	private BufferedReader  in;			// null once end-of-file reached
	private String[]        columnNames;
	private String          tableName;

	public XMLImporter( Reader in )
	{	this.in = in instanceof BufferedReader
						? (BufferedReader)in
                        : new BufferedReader(in)
	                    ;
	}
	public void startTable()			throws IOException
	{
		ArrayList<String> cols = new ArrayList<String>();
		String root = in.readLine();
		in.mark(0);
		tableName = in.readLine().trim().replace("<","").replace(">", "");
		int i = 0;
		while(true) {
			String s = in.readLine().trim();
			if(s.matches("<[\\w]+>[\\w\\s]+<[/\\w]+>")) {
				cols.add(s.replace("<","").replaceAll(">.+", ""));
			}
			else 
				break;
		}
		columnNames = cols.toArray(new String[cols.size()]);
		System.out.println(cols);
		in.reset();
	}
		
	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnNames.length;
	}
	public Iterator loadColumnNames()	throws IOException
	{	return new ArrayIterator(columnNames); 
	}

	public Iterator loadRow()			throws IOException
	{	Iterator row = null;
		String[] records = new String[columnNames.length];
		
		int i = 0;
		if(in != null) {
			String line = in.readLine().trim();
			while((line = in.readLine().trim()) != null) {
				if(line.matches("<[\\w]+>[\\w\\s]+<[/\\w]+>")) {
					records[i++] = line.replaceAll("<[\\w]+>", "").replaceAll("<.+", "");
					System.out.println(line.replaceAll("<[\\w]+>", "").replaceAll("<.+", ""));
				}
				else if(line.contentEquals("</"+tableName+">")) {
					row = new ArrayIterator(records);
					break;
				}

			}
		}
		
		return row;
	}

	public void endTable() throws IOException {}

}
