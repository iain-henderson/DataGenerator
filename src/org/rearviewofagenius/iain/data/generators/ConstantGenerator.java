package org.rearviewofagenius.iain.data.generators;

public class ConstantGenerator extends Generator {
  String constant = "";
  
  public ConstantGenerator(Integer rows, String constant){
    super(Integer.valueOf(queueDepth), rows);
    this.constant = constant;
    unique = true;
  }
  
  public String generate(){
    return constant;
  }
  
  public String toString(){
    return constant + " Generator";
  }
}
