package org.rearviewofagenius.iain.data;

import org.rearviewofagenius.iain.data.generators.Generator;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGenerator
{
	boolean verbose = false;
	String binaryFormat = "octets";

	String row = "";
	Integer rowCount = 10;

	ArrayList<LinkedBlockingDeque<String>> pieces = new ArrayList<LinkedBlockingDeque<String>> ();

	static ArrayList<Format> formats = new ArrayList<Format>();

	public static void main(String[] args)
	{
	     String binaryFormat = "octets";
	     boolean verbose = false;
	     Long time = Long.valueOf(System.currentTimeMillis());
	     if (args.length > 1) {
	    	 new DataGenerator(args[0], args[1]).binaryFormat(binaryFormat).verbose(verbose).generate();
	     } else if (args.length > 0) {
	    	 new DataGenerator(args[0]).verbose(verbose).binaryFormat(binaryFormat).generate();
	     }
	     if (verbose)
	    	 System.err.println(System.currentTimeMillis() - time.longValue() + "ms");
	}

	public DataGenerator(String row) {
		this.row = row;
	}

	public DataGenerator(String row, String rowCount) {
		this(row);
		this.rowCount = Integer.valueOf(Integer.parseInt(rowCount));
	}

	public DataGenerator binaryFormat(String format) {
		binaryFormat = format;
		return this;
	}

	public DataGenerator verbose(boolean verbose) {
		this.verbose = verbose;
		return this;
	}

	public void generate() {
		write(parseFormat(row, null), rowCount);
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


	public ArrayList<LinkedBlockingDeque<String>> parseFormat(String row, Integer index)
	{
		if (verbose) {
			System.err.println("row: " + row);
			System.err.println("index: " + index);
			if (index == null) {
				System.err.println(row);
			} else if (index < formats.size()) {
				System.err.println(Format.values()[index] + ": " + row);
			} else {
				System.err.println(row + " CONSTANT");
			}
		}
		ArrayList<LinkedBlockingDeque<String>> pieces = new ArrayList<LinkedBlockingDeque<String>>();
		if (!row.isEmpty()) {
			// initial call of this
			if (index == null) {
				Format.setRowCount(rowCount);
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
					pieces.addAll(parseFormat(m.group(1), index + 1));
//					Object[] arguments;
//					arguments = new Object[m.groupCount() - 2];
//					
//					for(int i = 0; i < arguments.length; i++){
//						arguments[i] = m.group(i + 2); // group 2 goes in index 1 etc etc
//					}

				switch (Format.values()[index].toString()) {
				case "DECIMAL":
					break;
				case "NUMBER":
					break; 
				case "TIMETZ":
					break; 
				case "VARBINARY":
					break; 
				case "VARBINARY(length)":
					break; 
				case "SMALLDATETIME":
					break; 
				case "DATETIME":
					break; 
				case "INTEGER":
					break; 
				case "CHAR(length)":
					break; 
				case "TIMESTAMP":
					break; 
				case "CHARACTER VARYING":
					break; 
				case "NUMERIC":
					break; 
				case "INTERVALYM":
					break; 
				case "INTEGER(minimum, maximum)":
					break; 
				case "TELEPHONE":
					break; 
				case "TIMESTAMPTZ":
					break; 
				case "TINYINT":
					break; 
				case "INTEGER(maximum)":
					break; 
				case "BINARY(length Bits)":
					break; 
				case "NUMERIC(precision, scale)":
					break; 
				case "INT":
					break; 
				case "RAW":
					break; 
				case "BOOL":
					break; 
				case "CHAR":
					break; 
				case "DATE":
					break; 
				case "INT8":
					break; 
				case "TIME":
					break; 
				case "CHARACTER":
					break; 
				case "BYTEA":
					break; 
				case "FLOAT":
					break; 
				case "MONEY":
					break; 
				case "SMALLINT":
					break; 
				case "FOREIGN KEY":
					break; 
				case "BOOLEAN":
					break; 
				case "INTERVAL":
					break; 
				case "BINARY VARYING":
					break; 
				case "DOUBLE PRECISION":
					break; 
				case "BINARY(length)":
					break; 
				case "NUMERIC(precision)":break; 
				case "BIGINT":break; 
				case "BINARY":break; 
				case "FLOAT(precision)":break; 
				case "FLOAT8":
					break;
				default:
					break;
				}
					pieces.addAll(parseFormat(m.group(m.groupCount()), index));
				} else {
					pieces.addAll(parseFormat(row, Integer.valueOf(index.intValue() + 1)));
				}
			}
		}

		return pieces;
	}

	public LinkedBlockingDeque<String> binaryBits(String format, Matcher m) {
		return binary(format, Integer.valueOf(0), Integer.valueOf(Integer.parseInt(m.group(2))));
	}

	public LinkedBlockingDeque<String> binary(String format, Matcher m) { return binary(format, Integer.valueOf(Integer.parseInt(m.group(2))), null); }

	public LinkedBlockingDeque<String> binary(String format)
	{
		return binary(format, null, null);
	}

	public LinkedBlockingDeque<String> binary(String format, Integer bytes, Integer bits) {
		return 


				bits == null ? 
						Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format, bytes }) : bytes == null ? Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format }) : 

							Generator.get("com.hp.cse.iain.data.Generators.BinaryGenerator", new Object[] { rowCount, format, bytes, bits });
	}

	public LinkedBlockingDeque<String> string()
	{
		return string(null, null);
	}

	public LinkedBlockingDeque<String> foreignKey(Matcher m) { return Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, m.group(2) }); }


	public LinkedBlockingDeque<String> string(Matcher m) { return Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, Integer.valueOf(Integer.parseInt(m.group(2))) }); }

	public LinkedBlockingDeque<String> string(Integer length, String file) {
		return 





				file == null ? 
						Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, length }) : length == null ? Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, file }) : (length == null) && (file == null) ? Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount }) : 

							Generator.get("com.hp.cse.iain.data.Generators.StringGenerator", new Object[] { rowCount, file, length });
	}

	public LinkedBlockingDeque<String> bool()
	{
		return Generator.get("com.hp.cse.iain.data.Generators.BooleanGenerator", new Object[] { rowCount });
	}

	public LinkedBlockingDeque<String> integer(Matcher m) {
		return m.groupCount() == 4 ? 
				integer(Long.valueOf(Long.parseLong(m.group(2))), Long.valueOf(Long.parseLong(m.group(3)))) : 

					integer(Long.valueOf(Long.parseLong(m.group(2))));
	}

	public LinkedBlockingDeque<String> integer() {
		return integer(null, null);
	}

	public LinkedBlockingDeque<String> integer(Long maximum) { return integer(null, maximum); }

	public LinkedBlockingDeque<String> integer(Long minimum, Long maximum) {
		return 


				minimum == null ? 
						Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount, maximum }) : maximum == null ? Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount }) : 

							Generator.get("com.hp.cse.iain.data.Generators.IntegerGenerator", new Object[] { rowCount, minimum, maximum });
	}

	public LinkedBlockingDeque<String> doublePrecision()
	{
		return Generator.get("com.hp.cse.iain.data.Generators.FloatGenerator", new Object[] { rowCount });
	}

	public LinkedBlockingDeque<String> numeric(Matcher m) {
		return m.groupCount() == 4 ? 
				numeric(Integer.valueOf(Integer.parseInt(m.group(2))), Integer.valueOf(Integer.parseInt(m.group(3)))) : 

					numeric(Integer.valueOf(Integer.parseInt(m.group(2))));
	}

	public LinkedBlockingDeque<String> money() {
		return numeric(Integer.valueOf(18), Integer.valueOf(4));
	}

	public LinkedBlockingDeque<String> number() { return numeric(null, null); }

	public LinkedBlockingDeque<String> numeric() {
		return numeric(Integer.valueOf(37), Integer.valueOf(15));
	}

	public LinkedBlockingDeque<String> numeric(Integer precision) { return numeric(precision, null); }

	public LinkedBlockingDeque<String> numeric(Integer precision, Integer scale) {
		return 


				scale == null ? 
						Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount, precision }) : precision == null ? Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount }) : 

							Generator.get("com.hp.cse.iain.data.Generators.NumericGenerator", new Object[] { rowCount, precision, scale });
	}

	public LinkedBlockingDeque<String> date()
	{
		return timestamp("yyyy-MM-dd");
	}

	public LinkedBlockingDeque<String> time() { return timestamp("kk:mm:ss"); }

	public LinkedBlockingDeque<String> timetz() {
		return timestamp("kk:mm:ss zzz");
	}

	public LinkedBlockingDeque<String> timestamp() { return timestamp(""); }

	public LinkedBlockingDeque<String> timestamptz() {
		return timestamp("yyyy-MM-dd kk:mm:ss zzz");
	}

	public LinkedBlockingDeque<String> timestamp(Matcher m) { return timestamp(m.group(2)); }

	public LinkedBlockingDeque<String> timestamp(String format) {
		return (format == null) || (format.isEmpty()) ? 
				Generator.get("com.hp.cse.iain.data.Generators.DateTimeGenerator", new Object[] { rowCount }) : 

					Generator.get("com.hp.cse.iain.data.Generators.DateTimeGenerator", new Object[] { rowCount, format });
	}

	public LinkedBlockingDeque<String> interval()
	{
		return Generator.get("com.hp.cse.iain.data.Generators.IntervalGenerator", new Object[] { rowCount });
	}

	public LinkedBlockingDeque<String> intervalYM() {
		return Generator.get("com.hp.cse.iain.data.Generators.IntervalYMGenerator", new Object[] { rowCount });
	}

	public LinkedBlockingDeque<String> telephone() {
		return Generator.get("com.hp.cse.iain.data.Generators.TelephoneNumberGenerator", new Object[] { rowCount });
	}

	public LinkedBlockingDeque<String> constant(String constant) {
		return Generator.get("com.hp.cse.iain.data.Generators.ConstantGenerator", new Object[] { rowCount, constant });
	}

	private enum Format{

		VARBINARY_LENGTH("VARBINARY(length)", "(.*?)(?:LONG )?VARBINARY\\(([0-9]+)\\)(.*)", "org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BINARY_BITS("BINARY(length Bits)","(.*?)BINARY\\(([0-9]+) [Bb][Ii][Tt][Ss]\\)(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BINARY_LENGTH("BINARY(length)","(.*?)BINARY\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		VARBINARY("VARBINARY","(.*?)(?:LONG )?VARBINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BINARY_VARYING("BINARY VARYING","(.*?)BINARY VARYING(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BINARY("BINARY","(.*?)BINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BYTEA("BYTEA","(.*?)BYTEA(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		RAW("RAW","(.*?)RAW(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		BOOLEAN("BOOLEAN","(.*?)BOOLEAN(.*)","org.rearviewofagenius.iain.data.generators.BooleanGenerator"),
		BOOL("BOOL","(.*?)BOOL(.*)","org.rearviewofagenius.iain.data.generators.BooleanGenerator"),
//	     formats.add(new Format("CHARACTER VARYING", Pattern.compile("(.*?)CHARACTER VARYING(.*)")));
//	     formats.add(new Format("CHARACTER", Pattern.compile("(.*?)CHARACTER(.*)")));
//	     formats.add(new Format("CHAR(length)", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("CHAR", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR(.*)")));
		CHARACTER_VARYING("CHARACTER VARYING", "(.*?)CHARACTER VARYING(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
		CHARACTER("CHARACTER", "(.*?)CHARACTER(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
		CHAR_LENGTH("CHAR(length)", "(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
		CHAR("CHAR", "(.*?)(?:LONG )?(?:VAR)?CHAR(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
//	     formats.add(new Format("TIMETZ", Pattern.compile("(.*?)TIMETZ(.*)")));
//	     formats.add(new Format("TIMESTAMPTZ", Pattern.compile("(.*?)TIMESTAMPTZ(.*)")));
//	     formats.add(new Format("SMALLDATETIME", Pattern.compile("(.*?)SMALLDATETIME(.*)")));
//	     formats.add(new Format("DATETIME", Pattern.compile("(.*?)DATETIME(.*)")));
//	     formats.add(new Format("TIMESTAMP", Pattern.compile("(.*?)TIMESTAMP(.*)")));
//	     formats.add(new Format("DATE", Pattern.compile("(.*?)DATE(.*)")));
//	     formats.add(new Format("TIME", Pattern.compile("(.*?)TIME(.*)")));
		INTERVALYM("INTERVALYM","(.*?)INTERVALYM(.*)","org.rearviewofagenius.iain.data.generators.IntervalYMGenerator"),
		INTERVAL("INTERVAL","(.*?)INTERVAL(.*)","org.rearviewofagenius.iain.data.generators.IntervalGenerator"),
		DOUBLE_PRECISION("DOUBLE PRECISION","(.*?)DOUBLE PRECISION(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator"),
		REAL("REAL","(.*?)REAL(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator"),
		FLOAT_PRECISION("FLOAT(precision)","(.*?)FLOAT\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator"),
		FLOAT8("FLOAT8","(.*?)FLOAT8(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator"),
		FLOAT("FLOAT","(.*?)FLOAT(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator"),
		INTEGER_MINIMUM_MAXIMUM("INTEGER(minimum, maximum)","(.*?)INTEGER\\(([0-9]+), ?([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		INTEGER_MAXIMUM("INTEGER(maximum)","(.*?)INTEGER\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		INTEGER("INTEGER","(.*?)INTEGER(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		BIGINT("BIGINT","(.*?)BIGINT(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		INT8("INT8","(.*?)INT8(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		SMALLINT("SMALLINT","(.*?)SMALLINT(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		TINYINT("TINYINT","(.*?)TINYINT(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		INT("INT","(.*?)INT(.*)","org.rearviewofagenius.iain.data.generators.IntegerGenerator"),
		NUMERIC_PRECISION_SCALE("NUMERIC(precision, scale)","(.*?)NUMERIC\\(([0-9]+), ?([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		NUMERIC_PRECISION("NUMERIC(precision)","(.*?)NUMERIC\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		NUMERIC("NUMERIC","(.*?)NUMERIC(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		DECIMAL("DECIMAL","(.*?)DECIMAL(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		NUMBER("NUMBER","(.*?)NUMBER(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		MONEY("MONEY","(.*?)MONEY(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator"),
		FOREIGNKEY("FOREIGN KEY", "(.*?)FOREIGN ?KEY\\((.*?)\\)(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
		TELEPHONE("TELEPHONE", "(.*?)TELEPHONE(.*)", "org.rearviewofagenius.iain.data.generators.TelephoneNumberGenerator"),
		CONSTANT("CONSTANT", "(.*?)", "org.rearviewofagenius.iain.data.generators.ConstantGenerator");

		final private String description;
		final private String generator;
		final private Pattern pattern;
		
		private static Integer rowCount = 10;
		
		Format(String d, String p, String g) {
			description = d;
			generator = g;
			pattern = Pattern.compile(p);
		}
		
		static public synchronized void setRowCount(Integer rc){
			rowCount = rc;
		}
		
		public String toString(){
			return description;
		}
		public Matcher matcher(String row){
			return pattern.matcher(row);
		}
		public LinkedBlockingDeque<String> deque(Object... arguments){
			return Generator.get(generator, rowCount, arguments);
		}

		// Automated method for when the matched groups line up with the constructor arguments
//		public LinkedBlockingDeque<String> deque(String row){
//			Matcher m = matcher(row);
//		Object[] arguments;
//			arguments = new Object[m.groupCount() - 2];
//			
//			for(int i = 0; i < arguments.length; i++){
//				arguments[i] = m.group(i + 2); // group 2 goes in index 1 etc etc
//			}
//			
//			return deque(rowCount, arguments);
//		}
	}
}
