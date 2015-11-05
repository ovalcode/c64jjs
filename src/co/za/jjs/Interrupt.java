package co.za.jjs;

public class Interrupt implements InterruptInterface{
  private int interruptFlags =0;
  private int interruptMask = 0;
  private Machine machine;
  private boolean hasInterruptOccured = false;
  public Interrupt(Machine machine) {
	  this.machine = machine;
  }
  
  public boolean isInterruptAllowed(String interruptName) {
	  if ((interruptMask & 128) == 0)
        return false;
	  
		if (interruptName == "TA")
			return (interruptMask & 1) != 0;
		else  if (interruptName == "TB")
			return (interruptMask & 2) != 0;
		else if (interruptName == "FL")
			return (interruptMask & 16) != 0;
       return false;
  }
  
  public void setInterrupt(String interruptname) {
	if (interruptname == "TA")
		interruptFlags |= 1;
	else  if (interruptname == "TB")
		interruptFlags |= 2;
	else if (interruptname == "FL")
		interruptFlags |= 16;
	
	//if ((interruptFlags & 128) == 0) {
		interruptFlags |= 128;
		if (isInterruptAllowed(interruptname))
		  hasInterruptOccured = true;
	//}
  }
  
  public void setInterruptMask(int mask) {
	  if ((mask & 0x80) == 0x80)
		  //we must set
		  this.interruptMask |= mask;
	  else
		  //we must mask of
		  this.interruptMask &= ~mask;
	  this.interruptMask = this.interruptMask & 0xff; 
	  //this.interruptMask = mask;
  }
  
  public int readInterrupts() {
	  int temp = this.interruptFlags;
	  this.interruptFlags = 0;
	  hasInterruptOccured = false;
	  return temp;
  }

@Override
public boolean hasInterruptOccured() {
	// TODO Auto-generated method stub
	return hasInterruptOccured;
}
}
