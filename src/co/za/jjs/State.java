package co.za.jjs;

public class State {
  private byte a = 0;
  private byte x = 0;
  private byte y = 0;
  private int pc = 0;
  private byte flags = 0;
  private byte stackPointer = (byte) 0xff;
  
  public byte getStackPointer() {
	return stackPointer;
}
public void setStackPointer(byte stackPointer) {
	this.stackPointer = stackPointer;
}

public int push() {
	byte temp = stackPointer;
	stackPointer--;
	return (temp & 0xff) + 0x100;
}

public int pop() {
	stackPointer++;
	return (stackPointer & 0xff) + 0x100;
}

public static final byte FLAG_CARRY = 1;
  public static final byte FLAG_ZERO = 2;
  public static final byte FLAG_IRQ_DISABLE = 4;
  public static final byte FLAG_DECIMAL_MODE = 8;
  public static final byte FLAG_BREAK = 16;
  public static final byte FLAG_UNUSED = 32;
  public static final byte FLAG_OVERFLOW = 64;
  public static final byte FLAG_NEGATIVE = -128;
  
 // 0  Carry Flag  C  Indicates when a bit of the result is to be carried to, or borrowed from, another byte.  
  //1  Zero Flag  Z  Indicates when the result is equal, or not, to zero.  
  //2  Interrupt Request Disable Flag  I  Indicates when preventing, or allowing, non-maskable interrupts (NMI).  
  //3  Decimal Mode Flag  D  Indicates when switching between decimal/binary modes.  
  //4  Break Command Flag  B  Indicates when stopping the execution of machine code instructions.  
  //5  Unused  -  Cannot be changed.  
  //6  Overflow Flag  V  Indicates when the result is greater, or less, than can be stored in one byte (including sign).  
  //7  Negative Flag  N  Indicates when the result is negative, or positive, in signed operations.  

public byte getA() {
	return a;
}
public void setA(byte a) {
	this.a = a;
}
public byte getX() {
	return x;
}
public void setX(byte x) {
	this.x = x;
}
public byte getY() {
	return y;
}
public void setY(byte y) {
	this.y = y;
}
public int getPc() {
	return pc;
}
public void setPc(int pc) {	
	if ((pc & 0xffff) == 0xff84) {
		System.out.println("fff");
	}
		
	this.pc = pc & 0xffff;
}
public byte getFlags() {
	return (byte) (flags | 32);
}
public void setFlags(byte flags) {
	this.flags = flags;
}
  
public boolean getDecimalModeFlag () {
	return (flags & FLAG_DECIMAL_MODE) != 0;  
}

public void setDecimalModeFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_DECIMAL_MODE;
	}else {
		this.flags &= ~FLAG_DECIMAL_MODE;
	}
  	
}


public boolean getCarryFlag () {
	return (flags & FLAG_CARRY) != 0;  
}

public void setCarryFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_CARRY;
	}else {
		this.flags &= ~FLAG_CARRY;
	}
  	
}

public boolean getOverflowFlag () {
	return (flags & FLAG_OVERFLOW) != 0;  
}

public void setOverflowFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_OVERFLOW;
	}else {
		this.flags &= ~FLAG_OVERFLOW;
	}
  	
}

public boolean getZeroFlag () {
	return (flags & FLAG_ZERO) != 0;  
}

public void setZeroFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_ZERO;
	}else {
		this.flags &= ~FLAG_ZERO;
	}  	
}

public boolean getNegativeFlag () {
	return (flags & FLAG_NEGATIVE) != 0;  
}

public void setNegativeFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_NEGATIVE;
	}else {
		this.flags &= ~FLAG_NEGATIVE;
	}
  	
}

public boolean getInteruptDisableFlag () {
	return (flags & FLAG_IRQ_DISABLE) != 0;  
}

public void setInteruptDisableFlag(boolean flag) {
	if (flag) {
		this.flags |= FLAG_IRQ_DISABLE;
	}else {
		this.flags &= ~FLAG_IRQ_DISABLE;
	}
  	
}

public String digitFrmBoolean(Boolean flag) {
  return flag ? "1" : "0";
}

public String dumpState() {
	String result = "A = " + Integer.toHexString(getA() & 0xff) + "      ";
	result += "X = " + Integer.toHexString(getX() & 0xff) + "      ";
	result += "Y = " + Integer.toHexString(getY() & 0xff) + "      ";
	result += "PC = " + Integer.toHexString(getPc() & 0xffff) + "      ";
	result += "SP = " + Integer.toHexString(getStackPointer() & 0xff) + "      ";
	
	result += "C=" + digitFrmBoolean(getCarryFlag()) + " ";
	result += "Z=" + digitFrmBoolean(getZeroFlag()) + " ";
	result += "I=" + digitFrmBoolean(getInteruptDisableFlag()) + " ";
	result += "D=" + digitFrmBoolean(getDecimalModeFlag()) + " ";
	//result += "B=" + digitFrmBoolean(getCarryFlag()) + " ";
	result += "OV=" + digitFrmBoolean(getOverflowFlag()) + " ";
	result += "N=" + digitFrmBoolean(getNegativeFlag()) + " ";

	return result;
/*	public static final byte FLAG_CARRY = 1;
	  public static final byte FLAG_ZERO = 2;
	  public static final byte FLAG_IRQ_DISABLE = 4;
	  public static final byte FLAG_DECIMAL_MODE = 8;
	  public static final byte FLAG_BREAK = 16;
	  public static final byte FLAG_UNUSED = 32;
	  public static final byte FLAG_OVERFLOW = 64;
	  public static final byte FLAG_NEGATIVE = -127;*/

//register
	//flags
	//stackpointer
	//pc
	
}

}
