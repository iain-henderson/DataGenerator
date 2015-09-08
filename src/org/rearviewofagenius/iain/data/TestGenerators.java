package org.rearviewofagenius.iain.data;

import java.util.concurrent.*;

import org.junit.*;
import org.rearviewofagenius.iain.data.generators.Generator;

import static org.junit.Assert.*;

public class TestGenerators {
	@Test
	public void TestBooleanGenerator(){
		final Integer rows = 10000;
		Format.setRowCount(rows);
		LinkedBlockingDeque<String> deque = Format.BOOLEAN.deque();
		assertTrue(Format.BOOLEAN.toString() + " started by itself", deque.isEmpty());

		Generator.start();

		for(int i = 0; i < rows; i++){
			String value = null;
			try{
				value = deque.poll(50, TimeUnit.MILLISECONDS); // give the generator a chance to start
			} catch(InterruptedException ie) { 
				System.err.println(ie.getMessage());
			}
			assertNotNull(Format.BOOLEAN.toString() + " only created " + i + " values", value);
			assertTrue(Format.BOOLEAN.toString() + " generated a value that was not true or false", value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
		}

		assertTrue(Format.BOOLEAN.toString() + " generated more than " + rows + " values", deque.isEmpty());
	}
}
