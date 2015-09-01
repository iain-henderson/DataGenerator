package org.rearviewofagenius.iain.data.generators;

public class IntervalYMGenerator extends Generator {

	  double years;
	  double months;
	  
	  public IntervalYMGenerator(Integer rows)
	  {
	    super(queueDepth, rows);
	  }
	  
	  public String generate()
	  {
	    years = (nextInt(50) + nextGaussian());
	    months = ((years - (int)years) * 12.0D);
	    
	    return String.format("%d years %d months", (int)years, (int)months);
	  }
	  
	  public String toString()
	  {
	    return "IntervalYM Generator";
	  }

}
