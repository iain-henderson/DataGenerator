package org.rearviewofagenius.iain.data.generators;

public class BinaryGenerator extends Generator {
	  int bytes = 8;
	  int bits = 0;
	  int v;
	  byte[] byteArray;
	  char[] chars;
	  String format = "octets";
	  protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
	  
	  public BinaryGenerator(Integer rows, String format, Integer bytes, Integer bits)
	  {
	    this(rows, bytes, bits);
	    unique = (!format.equalsIgnoreCase("octets"));
	    
	    this.format = format;
	    switch (format){
	    case "hex": 
	        chars = new char[bytes.intValue() * 2 + 2];
	        break;
	    case "bitstring":
	        chars = new char[bytes.intValue() * 8];
	        break;
        default:
    	    chars = new char[bytes.intValue() * 5 + 2];
	    }
	  }
	  
	  public BinaryGenerator(Integer rows, String format, Integer bytes)
	  {
	    this(rows, format, bytes, Integer.valueOf(0));
	  }
	  
	  public BinaryGenerator(Integer rows, Integer bytes, Integer bits){
	    super(queueDepth, rows);
	    if (bits != 0){
	      this.bytes = (bytes + bits / 8);
	      this.bits = (bits % 8);
	      
	      unique = ((bits != 0) && (bytes != 8));
	    } else {
	      this.bytes = bytes;
	      unique = (bytes != 8);
	    }
	    byteArray = new byte[bytes];
	  }
	  
	  public BinaryGenerator(Integer rows, Integer bytes){
	    this(rows, bytes, 0);
	  }
	  
	  public BinaryGenerator(Integer rows, String format){
	    this(rows, format, 8, 0);
	  }
	  
	  public BinaryGenerator(Integer rows){
	    this(rows, 8, 0);
	  }
	  
	  public String generate()
	  {
	    byteArray = new byte[bytes];
	    nextBytes(byteArray);
	    switch (format){
	    case "hex": 
	        chars = new char[bytes * 2 + 2];
	        chars[0] = '0';
	        chars[1] = 'x';
	        for (int j = 0; j < bytes; j++)
	        {
	          v = (byteArray[j] & 0xFF);
	          chars[(j * 2 + 2)] = hexArray[(v >>> 4)];
	          chars[(j * 2 + 3)] = hexArray[(v & 0xF)];
	        }
	        break;
	    case "bitstring": 
	        chars = new char[bytes * 8];
	        for (int j = 0; j < bytes; j++)
	        {
	          v = (byteArray[j] & 0xFF);
	          chars[(j * 8)] = hexArray[(byteArray[j] / 128)];
	          chars[(j * 8 + 1)] = hexArray[(byteArray[j] % 128 / 64)];
	          chars[(j * 8 + 2)] = hexArray[(byteArray[j] % 64 / 32)];
	          chars[(j * 8 + 3)] = hexArray[(byteArray[j] % 32 / 16)];
	          chars[(j * 8 + 4)] = hexArray[(byteArray[j] % 16 / 8)];
	          chars[(j * 8 + 5)] = hexArray[(byteArray[j] % 8 / 4)];
	          chars[(j * 8 + 6)] = hexArray[(byteArray[j] % 4 / 2)];
	          chars[(j * 8 + 7)] = hexArray[(byteArray[j] % 2)];
	        }
	        break;
        default:
		    chars = new char[bytes * 5];
		    for (int j = 0; j < bytes; j++)
		    {
		      v = (byteArray[j] & 0xFF);
		      chars[(j * 5)] = '\\';
		      chars[(j * 5 + 1)] = '\\';
		      chars[(j * 5 + 2)] = hexArray[(v / 64)];
		      chars[(j * 5 + 3)] = hexArray[(v % 64 / 8)];
		      chars[(j * 5 + 4)] = hexArray[(v % 8)];
		    }
	    }
	    return new String(chars);
	  }
	  
	  public String toString(){
	    return "Binary Generator";
	  }
}
