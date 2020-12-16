package com.holub.database;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DatabseTest {
	Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
	public void testInsert() {
		people.insert(new Object[] { "Holub", "Allen", "1" });
		people.insert(new Object[] { "Flintstone", "Wilma", "2" });
		people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });
	}
	@Test
	void testTestStore_xml() throws IOException, ClassNotFoundException {
		Writer out = new FileWriter("C:/dp2020/people.xml");
		people.export(new XMLExporter(out));
		out.close();

		Reader in = new FileReader("C:/dp2020/people.xml");
		Table new_people = new ConcreteTable(new XMLImporter(in));
		in.close();
		
		assertTrue(people.toString().equals(new_people.toString()));
	}

}
