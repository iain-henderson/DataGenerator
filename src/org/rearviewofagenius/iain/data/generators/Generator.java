package org.rearviewofagenius.iain.data.generators;

import org.rearviewofagenius.iain.data.DataGenerator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Generator implements Runnable
{
	int rows = 0;
	static int queueDepth = 50;

	DataGenerator writer = null;

	static ArrayList<Generator> threads = new ArrayList<Generator>();
	static HashMap<Class<?>, Generator> generators = new HashMap<Class<?>, Generator>();

	static ExecutorService es = Executors.newCachedThreadPool();

	static boolean verbose = false;
	
	LinkedBlockingDeque<String> deque;

	boolean unique = false;

	public static LinkedBlockingDeque<String> get(String generatorClass, Object... arguments)
	{
		try
		{
			return get(Class.forName(generatorClass), arguments);
		} catch (ClassNotFoundException cnfe) {
			System.err.println(generatorClass + " not found"); }
		return null;
	}


	public static LinkedBlockingDeque<String> get(Class<?> generatorClass, Object... arguments)
	{
		Generator g = null;
		Class<?>[] argumentClasses = new Class[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			argumentClasses[i] = arguments[i].getClass();
		}
		try {
			g = (Generator)generatorClass.getConstructor(argumentClasses).newInstance(arguments);
			if (g.unique) {
				threads.add(g);
			} else if (generators.containsKey(generatorClass)) {
				g = (Generator)generators.get(generatorClass);
				g.addRows((Integer)arguments[0]);
			} else {
				generators.put(generatorClass, g);
				threads.add(g);
			}

		} catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
			e.printStackTrace();
		}
		return g.deque();
	}

	public Generator(Integer rows) {
		this(Integer.valueOf(queueDepth), rows);
	}

	public Generator(Integer capacity, Integer rows) {
		this.rows = rows;
		deque = new LinkedBlockingDeque<String>(capacity);
	}

	public void run()
	{
		try {
			while (rows-- > 0) {
				deque.put(generate());
			}
		} catch (InterruptedException ie) {
			if (rows > 0) {
				System.err.println(toString() + " did not write " + rows + " rows");
			}
		}
		finish();
	}

	public abstract String generate();

	public static void start() { 
		es.shutdown();
		es = Executors.newWorkStealingPool(threads.size());
		for (Generator g : threads) {
			es.execute(g);
		}
		es.shutdown(); }

	public void finish() {}

	public String toString() { return "Generator"; }

	public LinkedBlockingDeque<String> deque()
	{
		return deque;
	}

	public Integer rows() {
		return Integer.valueOf(rows);
	}

	public void addRows(Integer rows) { this.rows += rows.intValue(); }

	public static void shutdownNow()
	{
		es.shutdown();
		for (Runnable r : es.shutdownNow()) {
			System.out.print(r.toString() + " is still running");
		}
	}
	
	public static void setVerbose(boolean v){
		verbose = v;
	}

	public static boolean nextBoolean()
	{
		return ThreadLocalRandom.current().nextBoolean();
	}

	public static void nextBytes(byte[] bytes) {
		ThreadLocalRandom.current().nextBytes(bytes);
	}

	public static double nextDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}

	public static float nextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}

	public static double nextGaussian() {
		return ThreadLocalRandom.current().nextGaussian();
	}

	public static int nextInt() {
		return ThreadLocalRandom.current().nextInt();
	}

	public static int nextInt(int bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}

	public static long nextLong() {
		return ThreadLocalRandom.current().nextLong();
	}
}
