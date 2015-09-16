package org.rearviewofagenius.iain.data;

import org.rearviewofagenius.iain.data.generators.Generator;
import org.rearviewofagenius.iain.data.generators.BinaryGenerator;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;

public class DataGenerator
{
	static boolean verbose = false;

	//	String row = "";
	//	Integer rowCount = 10;

	ArrayList<LinkedBlockingDeque<String>> pieces = new ArrayList<LinkedBlockingDeque<String>> ();

	public static void main(String[] args)
	{
		String binaryFormat = "octets";
		String row = "";
		Integer rowCount = 10;
		boolean verbose = false;

		for(int i = 0; i < args.length; i++){
			if(args[i].startsWith("--")){
				switch(args[i].substring(2)){
				case "--verbose":
					verbose = true;
					break;
				case "hex":
				case "hexadecimal":
					binaryFormat = "hex";
					break;
				case "bit":
				case "bit-string":
				case "bitstring":
					binaryFormat = "bitstring";
					break;
				}
			} else if(args[i].startsWith("-")){
				for(char a : args[i].substring(1).toCharArray()){
					switch(a){
					case 'v':
						verbose = true;
						break;
					case 'h':
						binaryFormat = "hex";
						break;
					case 'b':
						binaryFormat = "bitstring";
						break;
					}
				}
			} else{
				if(row.isEmpty()){
					row = args[i];
				} else {
					rowCount = Integer.valueOf(args[i]);
				}
			}
		}
		Long time = Long.valueOf(System.currentTimeMillis());
		DataGenerator.binaryFormat(binaryFormat);
		DataGenerator.verbose(verbose);
		DataGenerator.generate(row,rowCount);
		if (verbose){
			System.err.println(System.currentTimeMillis() - time.longValue() + "ms");
		}
	}

	public static void binaryFormat(String bf) {
		BinaryGenerator.setBinaryFormat(bf);
	}

	public static void verbose(boolean v) {
		DataGenerator.verbose = v;
		Generator.setVerbose(v);
	}

	public static void generate(String row, Integer rowCount) {
		Format.setRowCount(rowCount);
		ArrayList<LinkedBlockingDeque<String>> pieces = parseFormat(row, null);
		Generator.start();
		write(pieces, rowCount);
	}

	public static void write(ArrayList<LinkedBlockingDeque<String>> pieces, Integer rowCount) {
		do {
			for (LinkedBlockingDeque<String> piece : pieces) {
				try {
					System.out.print((String)piece.take());
				}
				catch (InterruptedException localInterruptedException) {}
			}
			System.out.println("");
			rowCount--;
		} while (rowCount > 0);
		Generator.shutdownNow();
	}


	public static ArrayList<LinkedBlockingDeque<String>> parseFormat(String row, Integer index){
		if (verbose) {
			System.err.println("row: " + row);
			System.err.println("index: " + index);
			if (index == null) {
				System.err.println(row);
			} else if (index < Format.values().length) {
				System.err.println(Format.values()[index] + ": " + row);
			} else {
				System.err.println(row + " CONSTANT");
			}
		}
		ArrayList<LinkedBlockingDeque<String>> pieces = new ArrayList<LinkedBlockingDeque<String>>();
		if (!row.isEmpty()) {
			// initial call of this
			if (index == null) {
				pieces.addAll(parseFormat(row, 0));
			} else {
				Format format = Format.values()[index];
				Matcher m = format.matcher(row);
				if (verbose) {
					System.err.println(m.pattern().toString() + " has " + m.groupCount() + " groups");
				}
				if (m.matches()){
					if (verbose) {
						for (int x = 1; x <= m.groupCount(); x++) {
							System.err.println(m.group(x));
						}
					}

					// constant SHOULD catch the entire row and leave nothing else
					if(!format.toString().equalsIgnoreCase("CONSTANT")){
						pieces.addAll(parseFormat(m.group(1), index + 1));
					}

					pieces.add(format.deque(format.arguments(m)));

					// constant SHOULD catch the entire row and leave nothing else
					if(!format.toString().equalsIgnoreCase("CONSTANT")){
						pieces.addAll(parseFormat(m.group(m.groupCount()), index));
					}
				} else {
					pieces.addAll(parseFormat(row, index + 1));
				}
			}
		}

		return pieces;
	}

	//	public LinkedBlockingDeque<String> binaryBits(String format, Matcher m) {
	//		return binary(format, Integer.valueOf(0), Integer.valueOf(Integer.parseInt(m.group(2))));
	//	}
	//
	//	public LinkedBlockingDeque<String> binary(String format, Matcher m) { return binary(format, Integer.valueOf(Integer.parseInt(m.group(2))), null); }
	//
	//	public LinkedBlockingDeque<String> binary(String format)
	//	{
	//		return binary(format, null, null);
	//	}
	//
	//	public LinkedBlockingDeque<String> binary(String format, Integer bytes, Integer bits) {
	//		return 
	//
	//
	//				bits == null ? 
	//						Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format, bytes }) : bytes == null ? Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format }) : 
	//
	//							Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format, bytes, bits });
	//	}
	//
	//	public LinkedBlockingDeque<String> string()
	//	{
	//		return string(null, null);
	//	}
	//
	//	public LinkedBlockingDeque<String> foreignKey(Matcher m) { return Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, m.group(2) }); }
	//
	//
	//	public LinkedBlockingDeque<String> string(Matcher m) { return Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, Integer.valueOf(Integer.parseInt(m.group(2))) }); }
	//
	//	public LinkedBlockingDeque<String> string(Integer length, String file) {
	//		return 
	//
	//
	//
	//
	//
	//				file == null ? 
	//						Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, length }) : length == null ? Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, file }) : (length == null) && (file == null) ? Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount }) : 
	//
	//							Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, file, length });
	//	}
	//
	//	public LinkedBlockingDeque<String> bool()
	//	{
	//		return Generator.get("com.hp.cse.iain.data.Generators.BooleanGenerator", new Object[] { rowCount });
	//	}
	//
	//	public LinkedBlockingDeque<String> integer(Matcher m) {
	//		return m.groupCount() == 4 ? 
	//				integer(Long.valueOf(Long.parseLong(m.group(2))), Long.valueOf(Long.parseLong(m.group(3)))) : 
	//
	//					integer(Long.valueOf(Long.parseLong(m.group(2))));
	//	}
	//
	//	public LinkedBlockingDeque<String> integer() {
	//		return integer(null, null);
	//	}
	//
	//	public LinkedBlockingDeque<String> integer(Long maximum) { return integer(null, maximum); }
	//
	//	public LinkedBlockingDeque<String> integer(Long minimum, Long maximum) {
	//		return 
	//
	//
	//				minimum == null ? 
	//						Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount, maximum }) : maximum == null ? Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount }) : 
	//
	//							Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount, minimum, maximum });
	//	}
	//
	//	public LinkedBlockingDeque<String> doublePrecision()
	//	{
	//		return Generator.get("com.hp.cse.iain.data.Generators.FloatGenerator", new Object[] { rowCount });
	//	}
	//
	//	public LinkedBlockingDeque<String> numeric(Matcher m) {
	//		return m.groupCount() == 4 ? 
	//				numeric(Integer.valueOf(Integer.parseInt(m.group(2))), Integer.valueOf(Integer.parseInt(m.group(3)))) : 
	//
	//					numeric(Integer.valueOf(Integer.parseInt(m.group(2))));
	//	}
	//
	//	public LinkedBlockingDeque<String> money() {
	//		return numeric(Integer.valueOf(18), Integer.valueOf(4));
	//	}
	//
	//	public LinkedBlockingDeque<String> number() { return numeric(null, null); }
	//
	//	public LinkedBlockingDeque<String> numeric() {
	//		return numeric(Integer.valueOf(37), Integer.valueOf(15));
	//	}
	//
	//	public LinkedBlockingDeque<String> numeric(Integer precision) { return numeric(precision, null); }
	//
	//	public LinkedBlockingDeque<String> numeric(Integer precision, Integer scale) {
	//		return 
	//
	//
	//				scale == null ? 
	//						Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount, precision }) : precision == null ? Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount }) : 
	//
	//							Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount, precision, scale });
	//	}
	//
	//	public LinkedBlockingDeque<String> date()
	//	{
	//		return timestamp("yyyy-MM-dd");
	//	}
	//
	//	public LinkedBlockingDeque<String> time() { return timestamp("kk:mm:ss"); }
	//
	//	public LinkedBlockingDeque<String> timetz() {
	//		return timestamp("kk:mm:ss zzz");
	//	}
	//
	//	public LinkedBlockingDeque<String> timestamp() { return timestamp(""); }
	//
	//	public LinkedBlockingDeque<String> timestamptz() {
	//		return timestamp("yyyy-MM-dd kk:mm:ss zzz");
	//	}
	//
	//	public LinkedBlockingDeque<String> timestamp(Matcher m) { return timestamp(m.group(2)); }
	//
	//	public LinkedBlockingDeque<String> timestamp(String format) {
	//		return (format == null) || (format.isEmpty()) ? 
	//				Generator.get("com.hp.cse.iain.data.Generators.DateTimeGenerator", new Object[] { rowCount }) : 
	//
	//					Generator.get("com.hp.cse.iain.data.Generators.DateTimeGenerator", new Object[] { rowCount, format });
	//	}
	//
	//	public LinkedBlockingDeque<String> interval()
	//	{
	//		return Generator.get("com.hp.cse.iain.data.Generators.IntervalGenerator", new Object[] { rowCount });
	//	}
	//
	//	public LinkedBlockingDeque<String> intervalYM() {
	//		return Generator.get("com.hp.cse.iain.data.Generators.IntervalYMGenerator", new Object[] { rowCount });
	//	}
	//
	//	public LinkedBlockingDeque<String> telephone() {
	//		return Generator.get("com.hp.cse.iain.data.Generators.TelephoneNumberGenerator", new Object[] { rowCount });
	//	}
	//
	//	public LinkedBlockingDeque<String> constant(String constant) {
	//		return Generator.get("com.hp.cse.iain.data.Generators.ConstantGenerator", new Object[] { rowCount, constant });
	//	}
}
