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

//	VARBINARY_LENGTH("VARBINARY(length)",
//		"(.*?)(?:LONG )?VARBINARY\\(([0-9]+)\\)(.*)", 
//		"org.rearviewofagenius.iain.data.generators.BinaryGenerator",
//		m -> new Object[]{Integer.valueOf(m.group(2)), 0}),
	BINARY_BITS("BINARY(length Bits)","(.*?)BINARY\\(([0-9]+) [Bb][Ii][Tt][Ss]\\)(.*)",
		"org.rearviewofagenius.iain.data.generators.BinaryGenerator",
		m -> new Object[]{0, Integer.valueOf(m.group(2))}),
	BINARY_LENGTH("BINARY(length)","(.*?)(?:LONG )?(?:VAR)?BINARY\\(([0-9]+)\\)(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
		m -> new Object[]{Integer.valueOf(m.group(2)), 0}),
//	VARBINARY("VARBINARY","(.*?)(?:LONG )?BINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
//		m -> new Object[]{}),
	BINARY_VARYING("BINARY VARYING","(.*?)BINARY VARYING(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
		m -> new Object[]{}),
	BINARY("BINARY","(.*?)(?:LONG )?(?:VAR)?BINARY(.*)","org.rearviewofagenius.iain.data.generators.BinaryGenerator",
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
		m -> new Object[]{Integer.valueOf(m.group(2))}),
	CHAR("CHAR", "(.*?)(?:LONG )?(?:VAR)?CHAR(.*)", "org.rearviewofagenius.iain.data.generators.StringGenerator",
		m -> new Object[]{}),
	INTERVALYM("INTERVALYM","(.*?)INTERVALYM(.*)",
		"org.rearviewofagenius.iain.data.generators.IntervalYMGenerator",
		m -> new Object[]{}),
	INTERVAL("INTERVAL","(.*?)INTERVAL(.*)",
		"org.rearviewofagenius.iain.data.generators.IntervalGenerator",
		m -> new Object[]{}),
	DOUBLE_PRECISION("DOUBLE PRECISION",
		"(.*?)DOUBLE PRECISION(.*)","org.rearviewofagenius.iain.data.generators.FloatGenerator",
		m -> new Object[]{}),
	REAL("REAL",
		"(.*?)REAL(.*)",
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

	public boolean matches(String row){
		return pattern.matcher(row).matches();
	}

	public LinkedBlockingDeque<String> deque(String row){
		return deque(matcher(row));
	}

	public LinkedBlockingDeque<String> deque(Matcher m){
		return deque(arguments(m));
	}

	public LinkedBlockingDeque<String> deque(Object... a){
		Object[] arguments = new Object[a.length + 1];

		arguments[0] = rowCount;
		System.arraycopy(a, 0, arguments, 1, a.length);

		return Generator.get(generator, arguments);
	}
}
