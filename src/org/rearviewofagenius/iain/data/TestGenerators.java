package org.rearviewofagenius.iain.data;

import java.util.concurrent.*;

import org.junit.*;
import org.rearviewofagenius.iain.data.generators.Generator;

import static org.junit.Assert.*;

public class TestGenerators {
	final Integer rows = 10000;

	@Test
	public void TestBinaryGenerator(){
		Format.setRowCount(rows);
		
		Format format = Format.BINARY;
		LinkedBlockingDeque<String> deque = format.deque("bitstring");
		assertTrue(format.toString() + " started by itself", deque.isEmpty());

		Generator.start();

		String value = null;
		for(int i = 0; i < rows; i++){
			try{
				value = deque.poll(500, TimeUnit.MILLISECONDS); // give the generator a chance to start
			} catch(InterruptedException ie) { 
				System.err.println(ie.getMessage());
			}
			assertNotNull(format.toString() + " only created " + i + " values", value);
			assertTrue(format.toString() + " generated a value that was not binary", value.replaceAll("[0-1]","").isEmpty());
		}

		assertTrue(format.toString() + " generated more than " + rows + " values", deque.isEmpty());
	}
	
	@Test
	public void TestHexGenerator(){
		Format.setRowCount(rows);
		
		Format format = Format.BINARY;
		LinkedBlockingDeque<String> deque = format.deque("hex");
		assertTrue(format.toString() + " started by itself", deque.isEmpty());

		Generator.start();

		String value = null;
		for(int i = 0; i < rows; i++){
			try{
				value = deque.poll(500, TimeUnit.MILLISECONDS); // give the generator a chance to start
			} catch(InterruptedException ie) { 
				System.err.println(ie.getMessage());
			}
			assertNotNull(format.toString() + " only created " + i + " values", value);
			assertTrue(format.toString() + " generated a value that was not a hexadecimal value", value.startsWith("0x") && value.substring(2).replaceAll("[0-9A-F]", "").isEmpty());
		}

		assertTrue(format.toString() + " generated more than " + rows + " values", deque.isEmpty());		
	}
	
	@Test
	public void TestOctetGenerator(){
		Format.setRowCount(rows);
		
		Format format = Format.BINARY;
		LinkedBlockingDeque<String> deque = format.deque("octet");
		assertTrue(format.toString() + " started by itself", deque.isEmpty());

		Generator.start();

		String value = null;
		for(int i = 0; i < rows; i++){
			try{
				value = deque.poll(500, TimeUnit.MILLISECONDS); // give the generator a chance to start
			} catch(InterruptedException ie) { 
				System.err.println(ie.getMessage());
			}
			assertNotNull(format.toString() + " created " + i + " values then stopped", value);
			for(String v:value.split("\\\\")){
				if(!v.isEmpty()){
					assertTrue(v + " out of range", Integer.valueOf(v.substring(0, 1)) <= 3);
					assertTrue(v + " out of range", Integer.valueOf(v.substring(1, 2)) <= 7);
					assertTrue(v + " out of range", Integer.valueOf(v.substring(2, 3)) <= 7);
				}
			}
		}

		assertTrue(format.toString() + " generated more than " + rows + " values", deque.isEmpty());
	}

	@Test
	public void TestBooleanGenerator(){
		Format.setRowCount(rows);
		
		Format format = Format.BOOLEAN;
		LinkedBlockingDeque<String> deque = format.deque();
		assertTrue(format.toString() + " started by itself", deque.isEmpty());

		Generator.start();

		String value = null;
		for(int i = 0; i < rows; i++){
			try{
				value = deque.poll(500, TimeUnit.MILLISECONDS); // give the generator a chance to start
			} catch(InterruptedException ie) { 
				System.err.println(ie.getMessage());
			}
			assertNotNull(format.toString() + " only created " + i + " values", value);
			assertTrue(format.toString() + " generated a value that was not true or false", value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
		}

		assertTrue(format.toString() + " generated more than " + rows + " values", deque.isEmpty());
	}
	
}
