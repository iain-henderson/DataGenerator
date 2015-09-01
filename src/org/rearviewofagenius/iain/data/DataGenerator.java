package org.rearviewofagenius.iain.data;

import org.rearviewofagenius.iain.data.generators.Generator;
import java.io.PrintStream;
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
			if (index == null) {
				pieces.addAll(parseFormat(row, 0));
			} else if (index.intValue() == formats.size()) {
				pieces.add(constant(row));
			} else {
				Format format = Format.values()[index];
				Matcher m = format.matcher(row);
				if (verbose) {
					System.err.println(m.pattern().toString() + " has " + m.groupCount() + " groups");
				}
				if (m.matches()) { 
					int x;
					if (verbose) {
						for (x = 1; x <= m.groupCount(); x++) {
							System.err.println(m.group(x));
						}
					}
					pieces.addAll(parseFormat(m.group(1), index + 1));

				switch (formats.get(index).description) {
				case "DECIMAL":
					break; 
				case -1981034679:  if (x.equals("NUMBER")) {} break; 
				case -1820338669:  if (x.equals("TIMETZ")) {} break; 
				case -1783518776:  if (x.equals("VARBINARY")) {} break; 
				case -1772648797:  if (x.equals("VARBINARY(length)")) break; break; 
				case -1767280286:  if (x.equals("SMALLDATETIME")) {} break; 
				case -1718637701:  if (x.equals("DATETIME")) {} break; 
				case -1618932450:  if (x.equals("INTEGER")) {} break; 
				case -1488324047:  if (x.equals("CHAR(length)")) {} break; 
				case -1453246218:  if (x.equals("TIMESTAMP")) {} break; 
				case -1344909767:  if (x.equals("CHARACTER VARYING")) {} break; 
				case -1282431251:  if (x.equals("NUMERIC")) {} break; 
				case -1098661223:  if (x.equals("INTERVALYM")) {} break; 
				case -876196735:  if (x.equals("INTEGER(minimum, maximum)")) {} break; 
				case -725171228:  if (x.equals("TELEPHONE")) {} break; 
				case -705241604:  if (x.equals("TIMESTAMPTZ")) {} break; 
				case -594415409:  if (x.equals("TINYINT")) {} break; 
				case -528008493:  if (x.equals("INTEGER(maximum)")) {} break; 
				case -428842032:  if (x.equals("BINARY(length Bits)")) {} break; 
				case -314110058:  if (x.equals("NUMERIC(precision, scale)")) {} break; 
				case 72655:  if (x.equals("INT")) {} break; 
				case 80904:  if (x.equals("RAW")) {} break; 
				case 2044650:  if (x.equals("BOOL")) {} break; 
				case 2067286:  if (x.equals("CHAR")) {} break; 
				case 2090926:  if (x.equals("DATE")) {} break; 
				case 2252361:  if (x.equals("INT8")) {} break; 
				case 2575053:  if (x.equals("TIME")) {} break; 
				case 55823113:  if (x.equals("CHARACTER")) {} break; 
				case 63686713:  if (x.equals("BYTEA")) {} break; 
				case 66988604:  if (x.equals("FLOAT")) {} break; 
				case 73541792:  if (x.equals("MONEY")) {} break; 
				case 176095624:  if (x.equals("SMALLINT")) {} break; 
				case 704200915:  if (x.equals("FOREIGN KEY")) {} break; 
				case 782694408:  if (x.equals("BOOLEAN")) {} break; 
				case 1353045189:  if (x.equals("INTERVAL")) {} break; 
				case 1696795441:  if (x.equals("BINARY VARYING")) {} break; 
				case 1770063567:  if (x.equals("DOUBLE PRECISION")) {} break; 
				case 1861587932:  if (x.equals("BINARY(length)")) break; break; 
				case 1923873926:  if (x.equals("NUMERIC(precision)")) {} break; 
				case 1959128815:  if (x.equals("BIGINT")) {} break; 
				case 1959329793:  if (x.equals("BINARY")) {} break; 
				case 2028970455:  if (x.equals("FLOAT(precision)")) {} break; 
				case 2076646780:  if (!x.equals("FLOAT8"))
				{
					break label1587;
					pieces.add(binary(binaryFormat, m));

					break label1634;
					pieces.add(binaryBits(binaryFormat, m));



					break label1634;


					pieces.add(binary(binaryFormat));


					break label1634;

					pieces.add(string());

					break label1634;
					pieces.add(string(m));

					break label1634;

					pieces.add(bool());



					break label1634;



					pieces.add(integer());

					break label1634;

					pieces.add(integer(m));

					break label1634;
				}
				else
				{
					pieces.add(doublePrecision());

					break label1634;

					pieces.add(numeric(m));

					break label1634;

					pieces.add(numeric());

					break label1634;
					pieces.add(number());

					break label1634;
					pieces.add(money());

					break label1634;
					pieces.add(date());

					break label1634;
					pieces.add(time());

					break label1634;
					pieces.add(timetz());


					break label1634;

					pieces.add(timestamp());

					break label1634;
					pieces.add(timestamptz());

					break label1634;
					pieces.add(intervalYM());

					break label1634;
					pieces.add(interval());

					break label1634;
					pieces.add(telephone());

					break label1634;
					pieces.add(foreignKey(m)); }
				break;
				}

				label1587:
					System.err.println(formatsgetintValuename);
				pieces.add(constant(formatsgetintValuename));

				label1634:

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

//	     formats.add(new Format("VARBINARY(length)", Pattern.compile("(.*?)(?:LONG )?VARBINARY\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("BINARY(length Bits)", Pattern.compile("(.*?)BINARY\\(([0-9]+) [Bb][Ii][Tt][Ss]\\)(.*)")));
//	     formats.add(new Format("BINARY(length)", Pattern.compile("(.*?)BINARY\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("VARBINARY", Pattern.compile("(.*?)(?:LONG )?VARBINARY(.*)")));
//	     formats.add(new Format("BINARY VARYING", Pattern.compile("(.*?)BINARY VARYING(.*)")));
//	     formats.add(new Format("BINARY", Pattern.compile("(.*?)BINARY(.*)")));
//	     formats.add(new Format("BYTEA", Pattern.compile("(.*?)BYTEA(.*)")));
//	     formats.add(new Format("RAW", Pattern.compile("(.*?)RAW(.*)")));
//	     formats.add(new Format("BOOLEAN", Pattern.compile("(.*?)BOOLEAN(.*)")));
//	     formats.add(new Format("BOOL", Pattern.compile("(.*?)BOOL(.*)")));
//	     formats.add(new Format("CHARACTER VARYING", Pattern.compile("(.*?)CHARACTER VARYING(.*)")));
//	     formats.add(new Format("CHARACTER", Pattern.compile("(.*?)CHARACTER(.*)")));
//	     formats.add(new Format("CHAR(length)", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("CHAR", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR(.*)")));
//	     formats.add(new Format("TIMETZ", Pattern.compile("(.*?)TIMETZ(.*)")));
//	     formats.add(new Format("TIMESTAMPTZ", Pattern.compile("(.*?)TIMESTAMPTZ(.*)")));
//	     formats.add(new Format("SMALLDATETIME", Pattern.compile("(.*?)SMALLDATETIME(.*)")));
//	     formats.add(new Format("DATETIME", Pattern.compile("(.*?)DATETIME(.*)")));
//	     formats.add(new Format("TIMESTAMP", Pattern.compile("(.*?)TIMESTAMP(.*)")));
//	     formats.add(new Format("DATE", Pattern.compile("(.*?)DATE(.*)")));
//	     formats.add(new Format("TIME", Pattern.compile("(.*?)TIME(.*)")));
//	     formats.add(new Format("INTERVALYM", Pattern.compile("(.*?)INTERVALYM(.*)")));
//	     formats.add(new Format("INTERVAL", Pattern.compile("(.*?)INTERVAL(.*)")));
//	     formats.add(new Format("DOUBLE PRECISION", Pattern.compile("(.*?)DOUBLE PRECISION(.*)")));
//	     formats.add(new Format("REAL", Pattern.compile("(.*?)REAL(.*)")));
//	     formats.add(new Format("FLOAT(precision)", Pattern.compile("(.*?)FLOAT\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("FLOAT8", Pattern.compile("(.*?)FLOAT8(.*)")));
//	     formats.add(new Format("FLOAT", Pattern.compile("(.*?)FLOAT(.*)")));
//	     formats.add(new Format("INTEGER(minimum, maximum)", Pattern.compile("(.*?)INTEGER\\(([0-9]+), ?([0-9]+)\\)(.*)")));
//	     formats.add(new Format("INTEGER(maximum)", Pattern.compile("(.*?)INTEGER\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("INTEGER", Pattern.compile("(.*?)INTEGER(.*)")));
//	     formats.add(new Format("BIGINT", Pattern.compile("(.*?)BIGINT(.*)")));
//	     formats.add(new Format("INT8", Pattern.compile("(.*?)INT8(.*)")));
//	     formats.add(new Format("SMALLINT", Pattern.compile("(.*?)SMALLINT(.*)")));
//	     formats.add(new Format("TINYINT", Pattern.compile("(.*?)TINYINT(.*)")));
//	     formats.add(new Format("INT", Pattern.compile("(.*?)INT(.*)")));
//	     formats.add(new Format("NUMERIC(precision, scale)", Pattern.compile("(.*?)NUMERIC\\(([0-9]+), ?([0-9]+)\\)(.*)")));
//	     formats.add(new Format("NUMERIC(precision)", Pattern.compile("(.*?)NUMERIC\\(([0-9]+)\\)(.*)")));
//	     formats.add(new Format("NUMERIC", Pattern.compile("(.*?)NUMERIC(.*)")));
//	     formats.add(new Format("DECIMAL", Pattern.compile("(.*?)DECIMAL(.*)")));
//	     formats.add(new Format("NUMBER", Pattern.compile("(.*?)NUMBER(.*)")));
//	     formats.add(new Format("MONEY", Pattern.compile("(.*?)MONEY(.*)")));
//	     formats.add(new Format("FOREIGN KEY", Pattern.compile("(.*?)FOREIGN ?KEY\\((.*?)\\)(.*)")));
//	     formats.add(new Format("TELEPHONE", Pattern.compile("(.*?)TELEPHONE(.*)")));

		VARBINARY("VARBINARY(length)", Pattern.compile("(.*?)(?:LONG )?VARBINARY\\(([0-9]+)\\)(.*)"), "org.rearviewofagenius.iain.data.generators.BinaryGenerator"),
		CONSTANT("CONSTANT", null, "org.rearviewofagenius.iain.data.generators.ConstantGenerator");
		final private String description;
		final private Pattern pattern;
		final private String generator;
		private static Integer rowCount = 10;
		
		Format(String d, Pattern p, String g) {
			description = d;
			generator = g;
			pattern = p;
		}
		
		public synchronized void setRowCount(Integer rc){
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
		public LinkedBlockingDeque<String> deque(String row){
			Object[] arguments;
			Matcher m = matcher(row);
			arguments = new Object[m.groupCount() - 2];
			
			for(int i = 0; i < arguments.length; i++){
				arguments[i] = m.group(i + 2); // group 2 goes in index 1 etc etc
			}
			
			return deque(rowCount, arguments);
		}
	}
}
