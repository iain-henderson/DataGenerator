package org.rearviewofagenius.iain.data.generators;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTimeGenerator extends Generator {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	Long range = Long.valueOf(5364658800000L);
	Long minimum = Long.valueOf(-2682329400000L);

	public DateTimeGenerator(Integer rows, SimpleDateFormat sdf, Long minimum, Long maximum) {
		super(Integer.valueOf(queueDepth), rows);
		if ((sdf != null) && (!sdf.toPattern().isEmpty())) {
			this.sdf = sdf;
			unique = true;
		}
		if (maximum != null) {
			if (minimum != null) {
				if (range != maximum - minimum) {
					range = maximum - minimum;
					unique = true;
				}
				if (this.minimum != minimum) {
					this.minimum = minimum;
					unique = true;
				}
			} else {
				range = maximum;
				this.minimum = 0L;
				unique = true;
			}
		}
	}

	public DateTimeGenerator(Integer rows) {
		this(rows, "");
	}

	public DateTimeGenerator(Integer rows, String format) {
		this(rows, format, (Long)null, (Long)null);
	}

	public DateTimeGenerator(Integer rows, Date range)
	{
		this(rows, "", new Date(-1L * Math.abs(range.getTime())), new Date(Math.abs(range.getTime())));
	}

	public DateTimeGenerator(Integer rows, Long minimum, Long maximum)
	{
		this(rows, "", minimum, maximum);
	}

	public DateTimeGenerator(Integer rows, Date minimum, Date maximum)
	{
		this(rows, "", minimum, maximum);
	}

	public DateTimeGenerator(Integer rows, String format, Date range) {
		this(rows, format, -1L * Math.abs(range.getTime()), Math.abs(range.getTime()));
	}

	public DateTimeGenerator(Integer rows, String format, Long minimum, Long maximum) {
		this(rows, new SimpleDateFormat(format), minimum, maximum);
	}

	public DateTimeGenerator(Integer rows, String format, Date minimum, Date maximum) {
		this(rows, new SimpleDateFormat(format), minimum, maximum);
	}

	public DateTimeGenerator(Integer rows, SimpleDateFormat sdf, Date minimum, Date maximum) {
		this(rows, sdf, minimum.getTime(), maximum.getTime());
	}

	public String generate() {
		if (Math.abs(minimum * 2L) == range) {
			return sdf.format(new Date(nextLong() % range));
		}
		return sdf.format(new Date(Math.abs(nextLong() % range) + minimum));
	}

}
