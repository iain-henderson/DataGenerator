package org.rearviewofagenius.iain.data.generators;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class IntegerGenerator extends Generator {

	  Iterator<Long> stream = ThreadLocalRandom.current().longs().iterator();
	  long maximum = Long.MAX_VALUE;
	  long minimum = Long.MIN_VALUE;
	  
	  public IntegerGenerator(Integer rows, Long minimum, Long maximum) {
	    super(Integer.valueOf(queueDepth), rows);
	    if (minimum > maximum) {
	      Long temp = minimum;
	      minimum = maximum;
	      maximum = temp;
	    }
	    if ((minimum != null) && (minimum > Long.MIN_VALUE)) {
	      this.minimum = minimum;
	      unique = true;
	    }
	    if ((maximum != null) && (maximum < Long.MAX_VALUE)) {
	      this.maximum = maximum;
	      unique = true;
	    }
	  }
	  
	  public IntegerGenerator(Integer rows, Long maximum) {
	    this(rows, maximum, Long.valueOf(Long.MIN_VALUE));
	  }
	  
	  public IntegerGenerator(Integer rows) {
	    this(rows, Long.valueOf(Long.MAX_VALUE));
	  }
	  
	  public String generate() {
	    if (unique) {
	      return Long.toString((long)nextGaussian() * (maximum - minimum));
	    }
	    return Long.toString(nextLong());
	  }
	  
	  public String toString() {
	    return "Integer Generator";
	  }
}
