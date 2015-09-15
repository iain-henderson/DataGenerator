/**
 * Order of these enumerations matters as the DataGenerator will attempt to match them in the order they appear.
 * Hence, if a less specific pattern comes first, it could steal what should be a more specific pattern.
 * 
 * If adding Formats to this list, keep CONSTANT last
 */
package org.rearviewofagenius.iain.data;

import java.util.concurrent.LinkedBlockingDeque;

import java.util.function.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rearviewofagenius.iain.data.generators.Generator;

enum Format{

	VARBINARY_LENGTH("VARBINARY(length)", "(.*?)(?:LONG )?VARBINARY\\(([0-9]+)\\)(.*)", 
			"org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BINARY_BITS("BINARY(length Bits)","(.*?)BINARY\\(([0-9]+) [Bb][Ii][Tt][Ss]\\)(.*)",
			"org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BINARY_LENGTH("BINARY(length)","(.*?)BINARY\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	VARBINARY("VARBINARY","(.*?)(?:LONG )?VARBINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BINARY_VARYING("BINARY VARYING","(.*?)BINARY VARYING(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BINARY("BINARY","(.*?)BINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BYTEA("BYTEA","(.*?)BYTEA(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	RAW("RAW","(.*?)RAW(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
			m -> new Object[]{}),
	BOOLEAN("BOOLEAN","(.*?)BOOLEAN(.*)","org.rearviewofagenius.iain.data.generators.BooleanGenerator",
			m -> new Object[]{}),
	BOOL("BOOL","(.*?)BOOL(.*)","org.rearviewofagenius.iain.data.generators.BooleanGenerator",
			m -> new Object[]{}),
	CHARACTER_VARYING("CHARACTER VARYING", "(.*?)CHARACTER VARYING(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator",
			m -> new Object[]{}),
	CHARACTER("CHARACTER", "(.*?)CHARACTER(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator",
			m -> new Object[]{}),
	CHAR_LENGTH("CHAR(length)", "(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator",
			m -> new Object[]{}),
	CHAR("CHAR", "(.*?)(?:LONG )?(?:VAR)?CHAR(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator",
			m -> new Object[]{}),
	//     formats.add(new Format("TIMETZ", Pattern.compile("(.*?)TIMETZ(.*)")));
	//     formats.add(new Format("TIMESTAMPTZ", Pattern.compile("(.*?)TIMESTAMPTZ(.*)")));
	//     formats.add(new Format("SMALLDATETIME", Pattern.compile("(.*?)SMALLDATETIME(.*)")));
	//     formats.add(new Format("DATETIME", Pattern.compile("(.*?)DATETIME(.*)")));
	//     formats.add(new Format("TIMESTAMP", Pattern.compile("(.*?)TIMESTAMP(.*)")));
	//     formats.add(new Format("DATE", Pattern.compile("(.*?)DATE(.*)")));
	//     formats.add(new Format("TIME", Pattern.compile("(.*?)TIME(.*)")));
	INTERVALYM("INTERVALYM","(.*?)INTERVALYM(.*)",
			"org.rearviewofagenius.iain.data.generators.IntervalYMGenerator",
			m -> new Object[]{}),
	INTERVAL("INTERVAL","(.*?)INTERVAL(.*)",
			"org.rearviewofagenius.iain.data.generators.IntervalGenerator",
			m -> new Object[]{}),
	DOUBLE_PRECISION("DOUBLE PRECISION",
			"(.*?)DOUBLE PRECISION(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator",
			m -> new Object[]{}),
	REAL("REAL","(.*?)REAL(.*)",
			"org.rearviewofagenius.iain.data.generators.FloatGenerator",
			m -> new Object[]{}),
	FLOAT_PRECISION("FLOAT(precision)", "(.*?)FLOAT\\(([0-9]+)\\)(.*)",
			"org.rearviewofagenius.iain.data.generators.FloatGenerator",
			m -> new Object[]{}),
	FLOAT8("FLOAT8","(.*?)FLOAT8(.*)",
			"org.rearviewofagenius.iain.data.generators.FloatGenerator",
			m -> new Object[]{}),
	FLOAT("FLOAT","(.*?)FLOAT(.*)",
			"org.rearviewofagenius.iain.data.generators.FloatGenerator",
			m -> new Object[]{}),
	INTEGER_MINIMUM_MAXIMUM("INTEGER(minimum, maximum)","(.*?)INTEGER\\(([0-9]+), ?([0-9]+)\\)(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	INTEGER_MAXIMUM("INTEGER(maximum)",	"(.*?)INTEGER\\(([0-9]+)\\)(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	INTEGER("INTEGER","(.*?)INTEGER(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	BIGINT("BIGINT","(.*?)BIGINT(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	INT8("INT8","(.*?)INT8(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	SMALLINT("SMALLINT","(.*?)SMALLINT(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	TINYINT("TINYINT","(.*?)TINYINT(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	INT("INT","(.*?)INT(.*)",
			"org.rearviewofagenius.iain.data.generators.IntegerGenerator",
			m -> new Object[]{}),
	NUMERIC_PRECISION_SCALE("NUMERIC(precision, scale)",
			"(.*?)NUMERIC\\(([0-9]+), ?([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	NUMERIC_PRECISION("NUMERIC(precision)",
			"(.*?)NUMERIC\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	NUMERIC("NUMERIC","(.*?)NUMERIC(.*)",
			"org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	DECIMAL("DECIMAL","(.*?)DECIMAL(.*)",
			"org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	NUMBER("NUMBER","(.*?)NUMBER(.*)",
			"org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	MONEY("MONEY","(.*?)MONEY(.*)",
			"org.rearviewofagenius.iain.data.generators.NumericGenerator",
			m -> new Object[]{}),
	FOREIGNKEY("FOREIGN KEY", "(.*?)FOREIGN ?KEY\\((.*?)\\)(.*)",
			"org.rearviewofagenius.iain.data.generators.StringGenerator",
			m -> new Object[]{}),
	TELEPHONE("TELEPHONE", "(.*?)TELEPHONE(.*)",
			"org.rearviewofagenius.iain.data.generators.TelephoneNumberGenerator",
			m -> new Object[]{}),
	CONSTANT("CONSTANT", "(.*?)",
			"org.rearviewofagenius.iain.data.generators.ConstantGenerator",
			m -> new Object[]{m.group()});

	final private String description;
	final private String generator;
	final private Pattern pattern;
	final private Function<Matcher, Object[]> argumentLogic;

	private static Integer rowCount = 10;

	Format(String d, String p, String g, Function<Matcher, Object[]> a) {
		description = d;
		generator = g;
		pattern = Pattern.compile(p);
		argumentLogic = a;
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
	public Object[] arguments(Matcher m){
		return argumentLogic.apply(m);
	}
	public LinkedBlockingDeque<String> deque(Object... a){
		Object[] arguments = new Object[a.length + 1];

		arguments[0] = rowCount;
		System.arraycopy(a, 0, arguments, 1, a.length);

		return Generator.get(generator, arguments);
	}

	// Automated method for when the matched groups line up with the constructor arguments
	//	public LinkedBlockingDeque<String> deque(String row){
	//		Matcher m = matcher(row);
	//	Object[] arguments;
	//		arguments = new Object[m.groupCount() - 2];
	//		
	//		for(int i = 0; i < arguments.length; i++){
	//			arguments[i] = m.group(i + 2); // group 2 goes in index 1 etc etc
	//		}
	//		
	//		return deque(rowCount, arguments);
	//	}
}
