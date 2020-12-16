package com.holub.database;

import java.io.*;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter{
	private final Writer out;
	private int width, height;
	private boolean tableH = true;
	
	public HTMLExporter(Writer out) {
		this.out = out;
	}
	
	public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException
	{
		this.width = width;
		this.height = height;
		out.write("<caption>"+tableName+"</caption>\n");
		storeRow(columnNames);
	}
	
	public void storeRow(Iterator data) throws IOException
	{
		int i = width;

		out.write("\t<tr>\n");
		while(data.hasNext()) {

			Object datum = data.next();
			
			if(tableH) {
				if( datum != null )	
					out.write("\t\t<th>"+datum.toString());

				if( --i > 0 ) {
					out.write("</th>\n");
				}
				else if( i == 0) {
					out.write("</th>\n\t</tr>\n");
				}
			}
			
			else {
				if( datum != null )	
					out.write("\t\t<td>"+datum.toString());
				if( --i > 0 ) {
					out.write("</td>\n");
				}
				else if( i == 0) {
					out.write("</td>\n\t</tr>\n");
				}
			}
			
		}
		tableH = false;
	}
	
	public void startTable() throws IOException {out.write("<table>\n");}
	public void endTable() throws IOException{out.write("</table>\n");}

}
