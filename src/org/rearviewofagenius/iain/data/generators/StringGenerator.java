package org.rearviewofagenius.iain.data.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class StringGenerator extends Generator implements Callable<ArrayList<Long>> {
	static String dictionary = "/usr/share/dict/words";
	String file = "";
	RandomAccessFile reader;
	ArrayList<Long> lines = null;
	static HashMap<String, ArrayList<Long>> fileLines = new HashMap<String, ArrayList<Long>>();
	int length = 0;
	ExecutorService fileReaders = es;

	public StringGenerator(Integer rows) {
		this(rows, dictionary, Integer.valueOf(0));
		unique = false;
	}

	public StringGenerator(Integer rows, Integer length) {
		this(rows, dictionary, length);
	}

	public StringGenerator(Integer rows, String file) {
		this(rows, file, Integer.valueOf(0));
	}

	public StringGenerator(Integer rows, String file, Integer length) {
		super(Integer.valueOf(queueDepth), rows);unique = true;
		try {
			if (file.equalsIgnoreCase(dictionary)) {
				file = dictionary();
			}
			reader = new RandomAccessFile(file, "r");
			if (fileLines.containsKey(file)){
				lines = fileLines.get(file);
			} else {
				// this provides the functionality of a second run method without branching the run method itself
				es.submit((Callable<?>)this);
				if (fileLines.isEmpty()) {
					synchronized (fileLines) {
						// if fileLines is empty kickoff the first thread to determine where line endings are
						// subsequent threads will unravel after the first has completed
						fileLines.notify();
					}
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
			System.exit(4246);
			if (length.intValue() > 0) {
				this.length = length.intValue();
			}
		}
	}

	public String generate() {
		return length > 0 ? nextString(length) : nextString();
	}

	public void finish() {
		try	{
			reader.close();
		} catch (IOException ioe) {
			System.err.println("Error closing file: " + file);
		}
	}

	public String nextString() {
		try	{
			if (lines != null) {
				reader.seek(((Long)lines.get(nextInt(lines.size()))).longValue());
			} else {
				long position = Math.abs(nextLong() % reader.length());
				boolean search = position > 0L;
				reader.seek(position);
				while (search) {
					if (position > 0L) {
						reader.seek(position - 1L);
						reader.readLine();
						if (reader.getFilePointer() > position) {
							position -= 1L;
						} else {
							search = false;
						}
					} else {
						search = false;
					}
				}
				reader.seek(position);
				if ((fileLines.containsKey(file)) && (fileLines.get(file) != null)) {
					lines = fileLines.get(file);
				}
			}
			return reader.readLine();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return "";
	}

	public String nextString(int length) {
		StringBuilder sb = new StringBuilder();
		sb.append(nextString());
		while (sb.length() < length) {
			sb.append(" ");
			sb.append(nextString());
		}
		return sb.toString().substring(0, length);
	}

	public synchronized ArrayList<Long> call() throws Exception {
		fileLines.wait();
		if (!fileLines.containsKey(file)) {
			fileLines.put(file, null);
			fileLines.notify();
			ArrayList<Long> newLines = new ArrayList<Long>();
			newLines.add(0L);
			RandomAccessFile fileReader = new RandomAccessFile(file, "r");
			while (fileReader.readLine() != null) {
				newLines.add(fileReader.getFilePointer());
			}
			fileLines.put(file, newLines);
			fileReader.close();
			return newLines;
		}
		fileLines.notify();
		return null;
	}

	public String toString() {
		return "String Generator";
	}

	public String dictionary() {
		// check the full path
		if (!new File(dictionary).exists()) {
			String[] parts = dictionary.split("[\\\\/]"); // file can do this, but we want to support windows and *NIX paths
			dictionary = parts[(parts.length - 1)];
		}
		// check just the file ending
		if (!new File(dictionary).exists()) {
			try {
				// write out the file from the jar if it doesn't exist
				File dictionaryFile = File.createTempFile("words", ".tmp");
				dictionaryFile.deleteOnExit();
				FileOutputStream output = new FileOutputStream(dictionaryFile);
				JarFile jf = new JarFile(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));

				InputStream input = jf.getInputStream(new JarEntry(dictionary));

				int read = 0;
				byte[] bytes = new byte['Ѐ'];
				while ((read = input.read(bytes)) != -1) {
					output.write(bytes, 0, read);
				}
				output.close();
				input.close();
				jf.close();
				dictionary = "words.tmp";
			} catch (URISyntaxException urise) {
				urise.printStackTrace();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return dictionary;
	}
}
