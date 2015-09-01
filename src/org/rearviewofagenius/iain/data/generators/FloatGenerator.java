package org.rearviewofagenius.iain.data.generators;

public class FloatGenerator extends Generator {
	Integer precision = 54;

	public FloatGenerator(Integer rows) {
		this(rows, 54);
	}

	public FloatGenerator(Integer rows, Integer precision) {
		super(Integer.valueOf(queueDepth), rows);
		if ((precision != null) && (precision != this.precision)) {
			this.precision = precision;
			unique = true;
		}
	}

	public String generate() {
		return Double.toString(nextDouble());
	}

	public String toString() {
		return "Float Generator";
	}
}
