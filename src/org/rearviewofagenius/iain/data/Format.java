package org.rearviewofagenius.iain.data;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rearviewofagenius.iain.data.generators.Generator;

enum Format{

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
//     formats.add(new Format("CHARACTER VARYING", Pattern.compile("(.*?)CHARACTER VARYING(.*)")));
//     formats.add(new Format("CHARACTER", Pattern.compile("(.*?)CHARACTER(.*)")));
//     formats.add(new Format("CHAR(length)", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)")));
//     formats.add(new Format("CHAR", Pattern.compile("(.*?)(?:LONG )?(?:VAR)?CHAR(.*)")));
	CHARACTER_VARYING("CHARACTER VARYING", "(.*?)CHARACTER VARYING(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
	CHARACTER("CHARACTER", "(.*?)CHARACTER(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
	CHAR_LENGTH("CHAR(length)", "(.*?)(?:LONG )?(?:VAR)?CHAR\\(([0-9]+)\\)(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
	CHAR("CHAR", "(.*?)(?:LONG )?(?:VAR)?CHAR(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator"),
//     formats.add(new Format("TIMETZ", Pattern.compile("(.*?)TIMETZ(.*)")));
//     formats.add(new Format("TIMESTAMPTZ", Pattern.compile("(.*?)TIMESTAMPTZ(.*)")));
//     formats.add(new Format("SMALLDATETIME", Pattern.compile("(.*?)SMALLDATETIME(.*)")));
//     formats.add(new Format("DATETIME", Pattern.compile("(.*?)DATETIME(.*)")));
//     formats.add(new Format("TIMESTAMP", Pattern.compile("(.*?)TIMESTAMP(.*)")));
//     formats.add(new Format("DATE", Pattern.compile("(.*?)DATE(.*)")));
//     formats.add(new Format("TIME", Pattern.compile("(.*?)TIME(.*)")));
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
