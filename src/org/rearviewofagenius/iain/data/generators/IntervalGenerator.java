package org.rearviewofagenius.iain.data.generators;

public class IntervalGenerator extends Generator {

	  double days;
	  double hours;
	  double minutes;
	  double seconds;
	  
	  public IntervalGenerator(Integer rows)
	  {
	    super(queueDepth, rows);
	  }
	  
	  public String generate()
	  {
	    days = (nextInt(730) + nextGaussian());
	    hours = ((days - (int)days) * 24.0D);
	    minutes = ((hours - (int)hours) * 60.0D);
	    seconds = ((minutes - (int)minutes) * 60.0D);
	    
	    return String.format("%d %d:%d:%d", (int)days, (int)hours, (int)minutes, (int)seconds);
	  }
	  
	  public String toString()
	  {
	    return "Interval Generator";
	  }

}
