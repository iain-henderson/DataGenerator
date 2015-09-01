package org.rearviewofagenius.iain.data.generators;

public class NumericGenerator extends Generator {

	Integer precision = 37;
	Integer scale = 0;

	public NumericGenerator(Integer rows, Integer precision, Integer scale) {
		super(queueDepth, rows);
		if (precision != null) {
			if (precision < 0) {
				precision = 0;
			}
			if (precision > 1024) {
				precision = 1024;
			}
			this.precision = precision;
			unique = true;
		}
		if ((scale != null) && (scale > 0)) {
			if ((precision != null) && (scale > precision)) {
				this.scale = precision;
			} else {
				this.scale = scale;
			}
			unique = true;
		}
	}

	public NumericGenerator(Integer rows, Integer precision) {
		this(rows, precision, null);
	}

	public NumericGenerator(Integer rows) {
		this(rows, 37, 0);
	}

	public String generate() {
		StringBuilder sb = new StringBuilder();
		int p = precision - scale;
		int s = scale;
		if (p > 0) {
			while (p > 10) {
				sb.append(nextInt((int)Math.pow(10.0D, 10.0D) - 1));
				p -= 10;
			}
			sb.append(nextInt((int)Math.pow(10.0D, p) - 1));
		} else {
			sb.append(0);
		}
		if (s > 0) {
			sb.append(".");
			while (s > 10) {
				sb.append(nextInt((int)Math.pow(10.0D, 1.0D) - 1));
				s -= 10;
			}
			sb.append(nextInt((int)Math.pow(10.0D, s) - 1));
		}
		return sb.toString();
	}

	public String toString() {
		return "Numeric Generator";
	}
}
