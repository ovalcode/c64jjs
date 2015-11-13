package co.za.jjs;

public class Cpu {

	public static final int[] ADDRESS_MODES = {
			5, 7, 0, 0, 0, 10, 10, 0, 5, 4, 0, 0, 0, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0, 
			1, 7, 0, 0, 10, 10, 10, 0, 5, 4, 0, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0, 
			5, 7, 0, 0, 0, 10, 10, 0, 5, 4, 0, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0, 
			5, 7, 0, 0, 0, 10, 10, 0, 5, 4, 0, 0, 6, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0, 
			0, 7, 0, 0, 10, 10, 10, 0, 5, 0, 5, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 11, 11, 12, 0, 5, 3, 5, 0, 0, 2, 0, 0, 
			4, 7, 4, 0, 10, 10, 10, 0, 5, 4, 5, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 11, 11, 12, 0, 5, 3, 5, 0, 2, 2, 3, 0, 
			4, 7, 0, 0, 10, 10, 10, 0, 5, 4, 5, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0, 
			4, 7, 0, 0, 10, 10, 10, 0, 5, 4, 5, 0, 1, 1, 1, 0, 
			9, 8, 0, 0, 0, 11, 11, 0, 5, 3, 0, 0, 0, 2, 2, 0			
	};
	
	public static final int[] INSTRUCTION_LEN = {
			1, 2, 0, 0, 0, 2, 2, 0, 1, 2, 1, 0, 0, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0, 
			3, 2, 0, 0, 2, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0, 
			1, 2, 0, 0, 0, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0, 
			1, 2, 0, 0, 0, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0, 
			0, 2, 0, 0, 2, 2, 2, 0, 1, 0, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 2, 2, 2, 0, 1, 3, 1, 0, 0, 3, 0, 0, 
			2, 2, 2, 0, 2, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 2, 2, 2, 0, 1, 3, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 2, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0, 
			2, 2, 0, 0, 2, 2, 2, 0, 1, 2, 1, 0, 3, 3, 3, 0, 
			2, 2, 0, 0, 0, 2, 2, 0, 1, 3, 0, 0, 0, 3, 3, 0			
	};
	
	public static final int[] INSTRUCTION_CYCLES = {
			7, 6, 0, 0, 0, 3, 5, 0, 3, 2, 2, 0, 0, 4, 6, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0, 
			6, 6, 0, 0, 3, 3, 5, 0, 4, 2, 2, 0, 4, 4, 6, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0, 
			6, 6, 0, 0, 0, 3, 5, 0, 3, 2, 2, 0, 3, 4, 6, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0, 
			6, 6, 0, 0, 0, 3, 5, 0, 4, 2, 2, 0, 5, 4, 6, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0, 
			0, 6, 0, 0, 3, 3, 3, 0, 2, 0, 2, 0, 4, 4, 4, 0, 
			2, 6, 0, 0, 4, 4, 4, 0, 2, 5, 2, 0, 0, 5, 0, 0, 
			2, 6, 2, 0, 3, 3, 3, 0, 2, 2, 2, 0, 4, 4, 4, 0, 
			2, 5, 0, 0, 4, 4, 4, 0, 2, 4, 2, 0, 4, 4, 4, 0, 
			2, 6, 0, 0, 3, 3, 5, 0, 2, 2, 2, 0, 4, 4, 3, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0, 
			2, 6, 0, 0, 3, 3, 5, 0, 2, 2, 2, 0, 4, 4, 6, 0, 
			2, 5, 0, 0, 0, 4, 6, 0, 2, 4, 0, 0, 0, 4, 7, 0			
	};
	
	public long getCycles() {
		return cycles;
	}

	private long cycles;
	private boolean interrupt = false;
	private long nextSreen;
	public long getNextSreen() {
		return nextSreen;
	}
	
	public void setInterrupt() {
		interrupt = true;
	}
	
	public void clearInterupt() {
		interrupt = false;
	}

	private boolean enableDebug = false;
	public void setEnableDebug(boolean enableDebug) {
		this.enableDebug = enableDebug;
	}

	private Machine bus;
	private State state;
	
	public Cpu(Machine machine) {
		bus = machine;
		state = new State();
		int temp = bus.read(0xfffd) * 256 + (bus.read(0xfffc) &0xff );
		temp = temp & 0xffff;
		state.setPc(temp); 
	}
	
	public byte getReadBus(int address) {
		return bus.read(address & 0xffff);
	}
	
	 public boolean getCarryFlag() {
		        return state.getCarryFlag();
    } 
	 
	 public int getCarryBit() {
		        return (state.getCarryFlag() ? 1 : 0);
    } 
		 
    public void setCarryFlag(boolean carryFlag) {    	
       state.setCarryFlag(carryFlag);
    } 	    	 

    public void setOverflowFlag(boolean overflowFlag) {
 	        state.setOverflowFlag(overflowFlag); 
    }
    
    public void setZeroFlag(boolean zeroFlag) {
	        state.setZeroFlag(zeroFlag); 
    }
    
    public void setNegativeFlag(boolean negativeFlag) {
        state.setNegativeFlag(negativeFlag); 
    }

    
    private void setArithmeticFlags(int reg) {
    	        state.setZeroFlag(reg == 0); 
    	        state.setNegativeFlag((reg & 0x80) != 0);
    }
    
    /**
     * Common code for Subtract with Carry.  Just calls ADC of the
     * one's complement of the operand.  This lets the N, V, C, and Z
     * flags work out nicely without any additional logic.
     */
    private int sbc(int acc, int operand) {
        int result;
        result = adc(acc, ~operand);
        setArithmeticFlags(result);
        return result;
    }

    /**
     * Subtract with Carry, BCD mode.
     */
    private int sbcDecimal(int acc, int operand) {
        int l, h, result;
        l = (acc & 0x0f) - (operand & 0x0f) - (state.getCarryFlag() ? 0 : 1);
        if ((l & 0x10) != 0) l -= 6;
        h = (acc >> 4) - (operand >> 4) - ((l & 0x10) != 0 ? 1 : 0);
        if ((h & 0x10) != 0) h -= 6;
        result = (l & 0x0f) | (h << 4);
        setCarryFlag((h & 0xff) < 15);
        setZeroFlag(result == 0);
        setNegativeFlag(false); // BCD is never negative
        setOverflowFlag(false); // BCD never sets overflow flag
        return (result & 0xff);
    }

	
    private int adc(int acc, int operand) { 
        int result = (operand & 0xff) + (acc & 0xff) + getCarryBit(); 
        int carry6 = (operand & 0x7f) + (acc & 0x7f) + getCarryBit(); 
        setCarryFlag((result & 0x100) != 0); 
        setOverflowFlag(state.getCarryFlag() ^ ((carry6 & 0x80) != 0)); 
        result &= 0xff; 
        setArithmeticFlags(result); 
        return result; 
    } 
    
    private int adcDecimal(int acc, int operand) { 
    	        int l, h, result; 
    	        l = (acc & 0x0f) + (operand & 0x0f) + getCarryBit();
    	        if ((l & 0xff) > 9) l += 6; 
    	        h = (acc >> 4) + (operand >> 4) + (l > 15 ? 1 : 0);
    	        if ((h & 0xff) > 9) h += 6; 
    	        result = (l & 0x0f) | (h << 4);
    	        result &= 0xff; 
    	        setCarryFlag(h > 15);
    	        setZeroFlag(result == 0);
    	        setNegativeFlag(false); // BCD is never negative    	 
    	        setOverflowFlag(false); // BCD never sets overflow flag    	 
    	        return result;     	 
    	    } 
    
    private int asl (int val) {
    	int temp = val << 1;
    	state.setCarryFlag((temp & 256) != 0);
    	temp = temp & 0xff;
    	setArithmeticFlags(temp);
    	return temp;
    }
    
    private void compare(int value, int operand) {
    	value = value & 0xff;
    	operand = operand & 0xff;
    	int temp = (value - operand) & 0xff;
    	state.setCarryFlag(value >= operand);
    	state.setZeroFlag(temp == 0);
    	state.setNegativeFlag((temp & 0x80) !=0);
    }
    
    public void jumpRelative(byte jumpSize) {
    	state.setPc(state.getPc() + jumpSize);
    }
    
    private void jumpToInterruptRoutine() {
    	                             /*RTI  Return from Interrupt

        pull SR, pull PC                 N Z C I D V
                                         from stack

        addressing    assembler    opc  bytes  cyles
        --------------------------------------------
        implied       RTI           40    1     6  
case 0x40:  temp =  bus.read(state.pop());
            state.setFlags((byte) temp);
            temp = bus.read(state.pop()) & 0xff;
         temp = bus.read(state.pop()) * 256 + temp;
         temp = temp & 0xffff;
         state.setPc(temp);
         break;
         
        push pc high,
        push pc low
        push sr
        get irq low from fffe
        get irq high from ffff
        
        
                           case 0x20: temp = state.getPc() - 1;                	          
                              bus.write(state.push(), (byte) ((temp >> 8) & 0xff));
                              bus.write(state.push(), (byte) (temp & 0xff));
                              state.setPc(cargs[2] * 256 + cargs[1]);
                              break;

         */
    	
    	int temp = state.getPc();
    	temp = temp & 0xffff;
    	bus.write(state.push(), (byte)(temp >> 8));
        bus.write(state.push() ,(byte)(temp & 0xff));
        bus.write(state.push() ,state.getFlags());
        int low = bus.read(0xfffe) & 0xff;
        int high = bus.read(0xffff) & 0xff;
        state.setPc(high * 256 + low);
    }

	
	public void step() {
		int[] cargs = new int[3];
		byte membyte1, membyte2;
		int effectiveAddress = 0;
		if (enableDebug)
          System.out.print(Integer.toHexString(state.getPc()) + " ");
		if (bus.hasInterruptOccured() & !state.getInteruptDisableFlag()) {
			state.setInteruptDisableFlag(true);
			jumpToInterruptRoutine();
			interrupt = false;
		}
		
		if ((state.getPc() & 0xffff) == 0xfdcd)
		{
		//	System.out.println("==================================================");
			//int high = bus.read(0xdc07) & 0xff;
			//int low = bus.read(0xdc06) & 0xff;
			//int result = high * 256 + low;
			//result = 0xffff - result;
			//System.out.print(bus.read(0xdc07)+" ");
			//System.out.println(bus.read(0xdc06));
			//System.out.println(result);
		}
		
		if ((state.getPc() & 0xffff) == 0x363)
		{
			//System.out.println(this.cycles);
			//System.out.println("");
		}
		
		if ((state.getPc() & 0xffff) == 0x3c2)
		{
			int high = 0;
			System.out.println("Checksum: " + state.getA());
			//System.out.println(bus.read(0xb1));
			//int high = bus.read(0xdc07) & 0xff;
			//int low = bus.read(0xdc06) & 0xff;
			//int result = high * 256 + low;
			//result = 0xffff - result;
			//System.out.print(bus.read(0xdc07)+" ");
			//System.out.println(bus.read(0xdc06));
			//System.out.println(result);
		}
		
		if ((state.getPc() & 0xffff) == 0xfdcd)
		{
			int high = 0;
			System.out.println("Data: " + state.getA());
			//System.out.println(bus.read(0xb1));
			//int high = bus.read(0xdc07) & 0xff;
			//int low = bus.read(0xdc06) & 0xff;
			//int result = high * 256 + low;
			//result = 0xffff - result;
			//System.out.print(bus.read(0xdc07)+" ");
			//System.out.println(bus.read(0xdc06));
			//System.out.println(result);
		}


		
		//if ((state.getPc() & 0xffff) == 0xF9BC)
		//{
		//	System.out.println(state.getX());
		//}


		//if ((state.getPc() & 0xffff) == 0xF984)
		//	System.out.println("=========================================");

		
		cargs[0] = bus.read(state.getPc()) & 0xff;		
		if (enableDebug)
		  System.out.print(Integer.toHexString(cargs[0]) + " ");
		state.setPc(state.getPc() + 1);
		for (int i = 1; i < INSTRUCTION_LEN[cargs[0]]; i++) {
			cargs[i] = bus.read(state.getPc())  & 0xff;
			if (enableDebug)
			  System.out.print(Integer.toHexString(cargs[i]) + " ");
			state.setPc(state.getPc() + 1);
		}
		
		if (cycles > nextSreen) {
			//nextSreen = nextSreen + 17000;
			//bus.write(0xd012, (byte) 0);
		}
		cycles = cycles + INSTRUCTION_CYCLES[cargs[0]];
		if (enableDebug)
		  System.out.println("          "+ state.dumpState());
		
		switch (ADDRESS_MODES[cargs[0]]) {
		// Absolute
        case 1:  effectiveAddress = (cargs[2] * 256 + cargs[1]) & 0xffff;
                 break;
        // Absolute,X
        case 2:  effectiveAddress = (cargs[2] * 256 + cargs[1] + (state.getX() & 0xff)) & 0xffff;
        break;
        // Absolute,Y
        case 3:  effectiveAddress = (cargs[2] * 256 + cargs[1] + (state.getY() & 0xff)) & 0xffff;
        break;
        // indirect
        case 6:  effectiveAddress = cargs[2] * 256 + cargs[1];
                 membyte1 = bus.read(effectiveAddress);
                 membyte2 = bus.read(effectiveAddress + 1);
                 effectiveAddress = membyte2 * 256 + (membyte1 & 0xff);
        break;
        // (indirect,X)
        case 7:  effectiveAddress = cargs[1] + (state.getX() & 0xff);
                 membyte1 = bus.read(effectiveAddress);
                 membyte2 = bus.read(effectiveAddress + 1);
                 effectiveAddress = membyte2 * 256 + (membyte1 & 0xff);
        break;
        // (indirect),Y
        case 8:
                 membyte1 = bus.read(cargs[1]);
                 membyte2 = bus.read(cargs[1] + 1);
                 effectiveAddress = membyte2 * 256 + (membyte1 & 0xff) + (state.getY() & 0xff);
        break;
        // relative
        case 9:
                 effectiveAddress = state.getPc() + cargs[1];
        break;
        // zeropage
        case 10:
                 effectiveAddress = cargs[1];
        break;
        // zeropage,X
        case 11:
                 effectiveAddress = (cargs[1] + (state.getX() & 0xff)) & 0xff;
        break;
        // zeropage,Y
        case 12:
                 effectiveAddress = (cargs[1] + (state.getY() & 0xff)) & 0xff;
        break;

                 
        default: 
                 break;
    }

		int temp = 0;
		switch ((int)(cargs[0] & 0xff )) {
		/*
		ADC  Add Memory to Accumulator with Carry

		 A + M + C -> A, C                N Z C I D V
		                                  + + + - - +

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 immidiate     ADC #oper     69    2     2
		 zeropage      ADC oper      65    2     3
		 zeropage,X    ADC oper,X    75    2     4
		 absolute      ADC oper      6D    3     4
		 absolute,X    ADC oper,X    7D    3     4*
		 absolute,Y    ADC oper,Y    79    3     4*
		 (indirect,X)  ADC (oper,X)  61    2     6
		 (indirect),Y  ADC (oper),Y  71    2     5*
		*/
		case 0x69: if (state.getDecimalModeFlag()) {
			         state.setA((byte) adcDecimal(state.getA(), cargs[1]));
		           } else {
    	        	   state.setA((byte) adc(state.getA(), cargs[1]));
		           };
		           break;
		case 0x65:
		case 0x75:
		case 0x6D:
		case 0x7D:
		case 0x79:
		case 0x61:
		case 0x71: if (state.getDecimalModeFlag()) {
           	         state.setA((byte) adcDecimal(state.getA(), bus.read(effectiveAddress)));
                   } else {
                	   state.setA((byte) adc(state.getA(), bus.read(effectiveAddress)));
                   };
                   break;
		
/*
AND  AND Memory with Accumulator

 A AND M -> A                     N Z C I D V
                                  + + - - - -

 addressing    assembler    opc  bytes  cyles
 --------------------------------------------
 immidiate     AND #oper     29    2     2
 zeropage      AND oper      25    2     3
 zeropage,X    AND oper,X    35    2     4
 absolute      AND oper      2D    3     4
 absolute,X    AND oper,X    3D    3     4*
 absolute,Y    AND oper,Y    39    3     4*
 (indirect,X)  AND (oper,X)  21    2     6
 (indirect),Y  AND (oper),Y  31    2     5*

 */
		
		case 0x29: state.setA((byte) (state.getA() & cargs[1]));
		           setArithmeticFlags(state.getA());
		           break;
		case 0x25:
		case 0x35:
		case 0x2D:
		case 0x3D:
		case 0x39:
		case 0x21:
		case 0x31: state.setA((byte) (state.getA() & bus.read(effectiveAddress)));
                   setArithmeticFlags(state.getA());
                   break;
                   
/*
ASL  Shift Left One Bit (Memory or Accumulator)

 C <- [76543210] <- 0             N Z C I D V
                                  + + + - - -

 addressing    assembler    opc  bytes  cyles
 --------------------------------------------
 accumulator   ASL A         0A    1     2
 zeropage      ASL oper      06    2     5
 zeropage,X    ASL oper,X    16    2     6
 absolute      ASL oper      0E    3     6
 absolute,X    ASL oper,X    1E    3     7

 * */
                   
		case 0x0A: state.setA((byte)asl(state.getA()));
		           break;
		case 0x06:
		case 0x16:
		case 0x0E:
		case 0x1E:bus.write(effectiveAddress, (byte) asl(bus.read(effectiveAddress)));
		          break;
		
/*		BCC  Branch on Carry Clear

		 branch on C = 0                  N Z C I D V
		                                  - - - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 relative      BCC oper      90    2     2**
*/
		case 0x90: if (!state.getCarryFlag())
			   jumpRelative((byte)cargs[1]);
			break;
		
/*			BCS  Branch on Carry Set

			 branch on C = 1                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BCS oper      B0    2     2**
*/
		case 0xB0: if (state.getCarryFlag())
			jumpRelative((byte)cargs[1]);
			break;
			
/*			BEQ  Branch on Result Zero

			 branch on Z = 1                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BEQ oper      F0    2     2**
*/
			
		case 0xF0: if (state.getZeroFlag())
			jumpRelative((byte)cargs[1]);
			break;

/*			BMI  Branch on Result Minus

			 branch on N = 1                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BMI oper      30    2     2**
*/

		case 0x30: if (state.getNegativeFlag())
			jumpRelative((byte)cargs[1]);
			break;
			
/*			BNE  Branch on Result not Zero

			 branch on Z = 0                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BNE oper      D0    2     2**
*/

		case 0xD0: if (!state.getZeroFlag())
			jumpRelative((byte)cargs[1]);
			break;

/*			BPL  Branch on Result Plus

			 branch on N = 0                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BPL oper      10    2     2**
*/
		case 0x10: if (!state.getNegativeFlag())
			jumpRelative((byte)cargs[1]);
			break;

/*			BVC  Branch on Overflow Clear

			 branch on V = 0                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BVC oper      50    2     2**
*/
		case 0x50: if (!state.getOverflowFlag())
			jumpRelative((byte)cargs[1]);
			break;
			
/*			BVS  Branch on Overflow Set

			 branch on V = 1                  N Z C I D V
			                                  - - - - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 relative      BVC oper      70    2     2**
*/
		case 0x70: if (state.getOverflowFlag())
			jumpRelative((byte)cargs[1]);
			break;

			/*CLC  Clear Carry Flag

			 0 -> C                           N Z C I D V
			                                  - - 0 - - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 implied       CLC           18    1     2  */
		case 0x18: state.setCarryFlag(false);
		break;

			/*CLD  Clear Decimal Mode

			 0 -> D                           N Z C I D V
			                                  - - - - 0 -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 implied       CLD           D8    1     2 */
		case 0xd8: state.setDecimalModeFlag(false);
		break;

			/*CLI  Clear Interrupt Disable Bit

			 0 -> I                           N Z C I D V
			                                  - - - 0 - -

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 implied       CLI           58    1     2*/
		case 0x58: state.setInteruptDisableFlag(false);
		break;

			/*CLV  Clear Overflow Flag

			 0 -> V                           N Z C I D V
			                                  - - - - - 0

			 addressing    assembler    opc  bytes  cyles
			 --------------------------------------------
			 implied       CLV           B8    1     2*/
		case 0xb8: state.setOverflowFlag(false);
		break;
		/*SEC  Set Carry Flag

		 1 -> C                           N Z C I D V
		                                  - - 1 - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       SEC           38    1     2*/
		
		case 0x38: state.setCarryFlag(true);
        break;

		/*SED  Set Decimal Flag

		 1 -> D                           N Z C I D V
		                                  - - - - 1 -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       SED           F8    1     2*/
		case 0xf8: state.setDecimalModeFlag(true);
		break;

		/*SEI  Set Interrupt Disable Status

		 1 -> I                           N Z C I D V
		                                  - - - 1 - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       SEI           78    1     2*/
		case 0x78: state.setInteruptDisableFlag(true);
		break;
		
		/*TAX  Transfer Accumulator to Index X

		 A -> X                           N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TAX           AA    1     2*/
         
		case 0xAA: state.setX(state.getA());
		           setArithmeticFlags(state.getX());
		  break;

		/*TAY  Transfer Accumulator to Index Y

		 A -> Y                           N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TAY           A8    1     2*/
		case 0xA8: state.setY(state.getA());
		           setArithmeticFlags(state.getY());
		           break;

		/*TSX  Transfer Stack Pointer to Index X

		 SP -> X                          N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TSX           BA    1     2*/
		           
		case 0xba: state.setX(state.getStackPointer());
        setArithmeticFlags(state.getX());
        break;



		/*TXA  Transfer Index X to Accumulator

		 X -> A                           N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TXA           8A    1     2*/
		case 0x8A: state.setA(state.getX());
		           setArithmeticFlags(state.getA());
		           break;

		/*TXS  Transfer Index X to Stack Register

		 X -> SP                          N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TXS           9A    1     2*/
		case 0x9a: state.setStackPointer(state.getX());
        setArithmeticFlags(state.getStackPointer());
        break;


		/*TYA  Transfer Index Y to Accumulator

		 Y -> A                           N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       TYA           98    1     2*/
		case 0x98: state.setA(state.getY());
		           setArithmeticFlags(state.getA());
		           break;
		
		/*DEC  Decrement Memory by One

		 M - 1 -> M                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 zeropage      DEC oper      C6    2     5
		 zeropage,X    DEC oper,X    D6    2     6
		 absolute      DEC oper      CE    3     3
		 absolute,X    DEC oper,X    DE    3     7*/

		case 0xC6: 
		case 0xD6:
		case 0xCE:
		case 0xDE: temp = (bus.read(effectiveAddress) - 1) & 0xff;
			      bus.write(effectiveAddress, (byte) temp);
			      setArithmeticFlags(temp);
			      break;

		/*DEX  Decrement Index X by One

		 X - 1 -> X                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       DEC           CA    1     2*/
		case 0xCA: temp = (state.getX() -1) & 0xff;
		           state.setX((byte) temp);
		           setArithmeticFlags(state.getX());
		           break;

		/*DEY  Decrement Index Y by One

		 Y - 1 -> Y                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       DEC           88    1     2*/
		case 0x88: temp = (state.getY() -1) & 0xff;
          state.setY((byte) temp);
          setArithmeticFlags(state.getY());
          break;
		

		/*INC  Increment Memory by One

		 M + 1 -> M                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 zeropage      INC oper      E6    2     5
		 zeropage,X    INC oper,X    F6    2     6
		 absolute      INC oper      EE    3     6
		 absolute,X    INC oper,X    FE    3     7*/
		case 0xE6:
		case 0xF6:
		case 0xEE:
		case 0xFE: temp = (bus.read(effectiveAddress) + 1) & 0xff;
	      bus.write(effectiveAddress, (byte) temp);
	      setArithmeticFlags(temp);
	      break;

		/*INX  Increment Index X by One

		 X + 1 -> X                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       INX           E8    1     2*/

		case 0xE8: temp = (state.getX() + 1) & 0xff;
        state.setX((byte) temp);
        setArithmeticFlags(temp);
        break;


		/*INY  Increment Index Y by One

		 Y + 1 -> Y                       N Z C I D V
		                                  + + - - - -

		 addressing    assembler    opc  bytes  cyles
		 --------------------------------------------
		 implied       INY           C8    1     2*/

		case 0xC8: temp = (state.getY() +1) & 0xff;
        state.setY((byte) temp);
        setArithmeticFlags(temp);
        break;

        /*LDA  Load Accumulator with Memory

        M -> A                           N Z C I D V
                                         + + - - - -

        addressing    assembler    opc  bytes  cyles
        --------------------------------------------
        immidiate     LDA #oper     A9    2     2
        zeropage      LDA oper      A5    2     3
        zeropage,X    LDA oper,X    B5    2     4
        absolute      LDA oper      AD    3     4
        absolute,X    LDA oper,X    BD    3     4*
        absolute,Y    LDA oper,Y    B9    3     4*
        (indirect,X)  LDA (oper,X)  A1    2     6
        (indirect),Y  LDA (oper),Y  B1    2     5* */

		case 0xA9: state.setA((byte) cargs[1]);
		           setArithmeticFlags(state.getA());
		           break;
		case 0xA5:
		case 0xB5:
		case 0xAD:
		case 0xBD:
		case 0xB9:
		case 0xA1:
		case 0xB1: state.setA(bus.read(effectiveAddress));
		           setArithmeticFlags(state.getA());
		           break;

       /*LDX  Load Index X with Memory

        M -> X                           N Z C I D V
                                         + + - - - -

        addressing    assembler    opc  bytes  cyles
        --------------------------------------------
        immidiate     LDX #oper     A2    2     2
        zeropage      LDX oper      A6    2     3
        zeropage,Y    LDX oper,Y    B6    2     4
        absolute      LDX oper      AE    3     4
        absolute,Y    LDX oper,Y    BE    3     4* */

		case 0xA2: state.setX((byte) cargs[1]);
		           setArithmeticFlags(state.getX());
		           break;
		case 0xA6:
		case 0xB6:
		case 0xAE:
		case 0xBE: state.setX(bus.read(effectiveAddress));
		           setArithmeticFlags(state.getX());		   
		           break;

       /*LDY  Load Index Y with Memory

        M -> Y                           N Z C I D V
                                         + + - - - -

        addressing    assembler    opc  bytes  cyles
        --------------------------------------------
        immidiate     LDY #oper     A0    2     2
        zeropage      LDY oper      A4    2     3
        zeropage,X    LDY oper,X    B4    2     4
        absolute      LDY oper      AC    3     4
        absolute,X    LDY oper,X    BC    3     4* */

		case 0xA0: state.setY((byte) cargs[1]);
		           setArithmeticFlags(state.getY());
		           break;
		case 0xA4:
		case 0xB4:
		case 0xAC:
		case 0xBC: state.setY(bus.read(effectiveAddress));
		           setArithmeticFlags(state.getY());
		           break;
		           
		           /*STA  Store Accumulator in Memory

		           A -> M                           N Z C I D V
		                                            - - - - - -

		           addressing    assembler    opc  bytes  cyles
		           --------------------------------------------
		           zeropage      STA oper      85    2     3
		           zeropage,X    STA oper,X    95    2     4
		           absolute      STA oper      8D    3     4
		           absolute,X    STA oper,X    9D    3     5
		           absolute,Y    STA oper,Y    99    3     5
		           (indirect,X)  STA (oper,X)  81    2     6
		           (indirect),Y  STA (oper),Y  91    2     6 */
		           case 0x85:
		           case 0x95:
		           case 0x8D:
		           case 0x9D:
		           case 0x99:
		           case 0x81:
		           case 0x91: bus.write(effectiveAddress, state.getA());
		           break;

		          /*STX  Store Index X in Memory

		           X -> M                           N Z C I D V
		                                            - - - - - -

		           addressing    assembler    opc  bytes  cyles
		           --------------------------------------------
		           zeropage      STX oper      86    2     3
		           zeropage,Y    STX oper,Y    96    2     4
		           absolute      STX oper      8E    3     4 */
		           case 0x86:
		           case 0x96:
		           case 0x8E: bus.write(effectiveAddress, state.getX());
		             break;

		          /*STY  Sore Index Y in Memory

		           Y -> M                           N Z C I D V
		                                            - - - - - -

		           addressing    assembler    opc  bytes  cyles
		           --------------------------------------------
		           zeropage      STY oper      84    2     3
		           zeropage,X    STY oper,X    94    2     4
		           absolute      STY oper      8C    3     4*/
		           case 0x84:
		           case 0x94:
		           case 0x8C: bus.write(effectiveAddress, state.getY());
		             break;

/*		             EOR  Exclusive-OR Memory with Accumulator

		             A EOR M -> A                     N Z C I D V
		                                              + + - - - -

		             addressing    assembler    opc  bytes  cyles
		             --------------------------------------------
		             immidiate     EOR #oper     49    2     2
		             zeropage      EOR oper      45    2     3
		             zeropage,X    EOR oper,X    55    2     4
		             absolute      EOR oper      4D    3     4
		             absolute,X    EOR oper,X    5D    3     4*
		             absolute,Y    EOR oper,Y    59    3     4*
		             (indirect,X)  EOR (oper,X)  41    2     6
		             (indirect),Y  EOR (oper),Y  51    2     5* */
		             
		           case 0x49: state.setA((byte) ((state.getA() ^ cargs[1]) & 0xff));
		                      setArithmeticFlags(state.getA());
		                      break;
		           case 0x45:
		           case 0x55:
		           case 0x4D:
		           case 0x5D:
		           case 0x59:
		           case 0x41:
		           case 0x51: state.setA((byte) ((state.getA() ^ bus.read(effectiveAddress)) & 0xff));
                              setArithmeticFlags(state.getA());
                              break;
		             
/*		             LSR  Shift One Bit Right (Memory or Accumulator)

		             0 -> [76543210] -> C             N Z C I D V
		                                              - + + - - -

		             addressing    assembler    opc  bytes  cyles
		             --------------------------------------------
		             accumulator   LSR A         4A    1     2
		             zeropage      LSR oper      46    2     5
		             zeropage,X    LSR oper,X    56    2     6
		             absolute      LSR oper      4E    3     6
		             absolute,X    LSR oper,X    5E    3     7*/
                              
		           case 0x4A: state.setCarryFlag((state.getA() & 1) == 1);
		                      state.setA((byte) ((state.getA() & 0xff) >> 1));
		                      state.setZeroFlag(state.getA() == 0);
		                      break;
		           case 0x46:
		           case 0x56:
		           case 0x4E:
		           case 0x5E: temp = bus.read(effectiveAddress);
		        	          state.setCarryFlag((temp & 1) == 1);
		        	          temp = (temp & 0xff) >> 1;
                              bus.write(effectiveAddress, (byte) (temp));
                              state.setZeroFlag(temp == 0);
                              break;
                              
                     

/*		             ORA  OR Memory with Accumulator

		             A OR M -> A                      N Z C I D V
		                                              + + - - - -

		             addressing    assembler    opc  bytes  cyles
		             --------------------------------------------
		             immidiate     ORA #oper     09    2     2
		             zeropage      ORA oper      05    2     3
		             zeropage,X    ORA oper,X    15    2     4
		             absolute      ORA oper      0D    3     4
		             absolute,X    ORA oper,X    1D    3     4*
		             absolute,Y    ORA oper,Y    19    3     4*
		             (indirect,X)  ORA (oper,X)  01    2     6
		             (indirect),Y  ORA (oper),Y  11    2     5* */
                              case 0x09: state.setA((byte) ((state.getA() | cargs[1]) & 0xff));
		                                 setArithmeticFlags(state.getA());
		                                 break;
                              case 0x05:
                              case 0x15:
                              case 0xD:
                              case 0x1D:
                              case 0x19:
                              case 0x01:
                              case 0x11: state.setA((byte) ((state.getA() | bus.read(effectiveAddress)) & 0xff));
                                         setArithmeticFlags(state.getA());
                                         break;
                              
                             

/*		             ROL  Rotate One Bit Left (Memory or Accumulator)

		             C <- [76543210] <- C             N Z C I D V
		                                              + + + - - -

		             addressing    assembler    opc  bytes  cyles
		             --------------------------------------------
		             accumulator   ROL A         2A    1     2
		             zeropage      ROL oper      26    2     5
		             zeropage,X    ROL oper,X    36    2     6
		             absolute      ROL oper      2E    3     6
		             absolute,X    ROL oper,X    3E    3     7 */
                              case 0x2A: temp = state.getA() << 1;
                                         temp = temp | (state.getCarryFlag() ? 1 : 0);
                                         state.setCarryFlag((temp & 0x100) != 0);
                                         state.setA((byte) (temp & 0xff));
                                         setArithmeticFlags(temp & 0xff);
                                         break;
                              case 0x26:
                              case 0x36:
                              case 0x2E:
                              case 0x3E: temp = bus.read(effectiveAddress) << 1;
                                         temp = temp | (state.getCarryFlag() ? 1 : 0);
                                         state.setCarryFlag((temp & 0x100) != 0);
                                         bus.write(effectiveAddress,(byte) (temp & 0xff));
                                         setArithmeticFlags(temp & 0xff);
                                         break;
                                         
                                        /* SBC  Subtract Memory from Accumulator with Borrow

                                         A - M - C -> A                   N Z C I D V
                                                                          + + + - - +

                                         addressing    assembler    opc  bytes  cyles
                                         --------------------------------------------
                                         immidiate     SBC #oper     E9    2     2
                                         zeropage      SBC oper      E5    2     3
                                         zeropage,X    SBC oper,X    F5    2     4
                                         absolute      SBC oper      ED    3     4
                                         absolute,X    SBC oper,X    FD    3     4*
                                         absolute,Y    SBC oper,Y    F9    3     4*
                                         (indirect,X)  SBC (oper,X)  E1    2     6
                                         (indirect),Y  SBC (oper),Y  F1    2     5* */

                              case 0xE9: if (state.getDecimalModeFlag()) {
                                  state.setA((byte) sbcDecimal(state.getA(), cargs[1]));
                              } else {
                                  state.setA((byte) sbc(state.getA(), cargs[1]));
                              }
                              break;

                              case 0xE5:
                              case 0xF5:
                              case 0xED:
                              case 0xFD:
                              case 0xF9:
                              case 0xE1:
                              case 0xF1: if (state.getDecimalModeFlag()) {
                                  state.setA((byte) sbcDecimal(state.getA(), bus.read(effectiveAddress)));
                              } else {
                                  state.setA((byte) sbc(state.getA(), bus.read(effectiveAddress)));
                              }
                              break;



/*		            ROR  Rotate One Bit Right (Memory or Accumulator)

		             C -> [76543210] -> C             N Z C I D V
		                                              + + + - - -

		             addressing    assembler    opc  bytes  cyles
		             --------------------------------------------
		             accumulator   ROR A         6A    1     2
		             zeropage      ROR oper      66    2     5
		             zeropage,X    ROR oper,X    76    2     6
		             absolute      ROR oper      6E    3     6
		             absolute,X    ROR oper,X    7E    3     7 */

                              case 0x6A: temp = (state.getA() & 0xff) | (state.getCarryFlag() ? 256 : 0) ;
                                         state.setCarryFlag((temp & 1) != 0);
                                         temp = temp >> 1;                                         
                                         state.setA((byte) (temp & 0xff));
                                         setArithmeticFlags(temp & 0xff);
                                       break;
                              case 0x66:
                              case 0x76:
                              case 0x6E:
                              case 0x7E: temp = bus.read(effectiveAddress) & 0xff;
                            	         temp = temp | (state.getCarryFlag() ? 256 : 0) ;
                                         state.setCarryFlag((temp & 1) != 0);
                                         temp = temp >> 1;                                         
                                         bus.write(effectiveAddress,(byte) (temp & 0xff));
                                         setArithmeticFlags(temp & 0xff);
                                        break;

                                        /*BIT  Test Bits in Memory with Accumulator

                                        bits 7 and 6 of operand are transfered to bit 7 and 6 of SR (N,V);
                                        the zeroflag is set to the result of operand AND accumulator.

                                        A AND M, M7 -> N, M6 -> V        N Z C I D V
                                                                        M7 + - - - M6

                                        addressing    assembler    opc  bytes  cyles
                                        --------------------------------------------
                                        zeropage      BIT oper      24    2     3
                                        absolute      BIT oper      2C    3     4*/
                                        
                              case 0x24:
                              case 0x2c: temp = bus.read(effectiveAddress) & 0xff;
                                         state.setNegativeFlag((temp & 0x80) != 0);
                                         state.setOverflowFlag((temp & 0x40) != 0);
                                         state.setZeroFlag((temp & state.getA()) == 0);
                            	  break;
                                      

                                        /*CMP  Compare Memory with Accumulator

                                        A - M                            N Z C I D V
                                                                       + + + - - -

                                        addressing    assembler    opc  bytes  cyles
                                        --------------------------------------------
                                        immidiate     CMP #oper     C9    2     2
                                        zeropage      CMP oper      C5    2     3
                                        zeropage,X    CMP oper,X    D5    2     4
                                        absolute      CMP oper      CD    3     4
                                        absolute,X    CMP oper,X    DD    3     4*
                                        absolute,Y    CMP oper,Y    D9    3     4*
                                        (indirect,X)  CMP (oper,X)  C1    2     6
                                        (indirect),Y  CMP (oper),Y  D1    2     5* */
                              case 0xC9: compare(state.getA(), cargs[1]);
                                         break;
                              case 0xC5:
                              case 0xD5:
                              case 0xCD:
                              case 0xDD:
                              case 0xD9:
                              case 0xC1:
                              case 0xD1: compare(state.getA() , bus.read(effectiveAddress));
                                         break;


                                      /* CPX  Compare Memory and Index X

                                        X - M                            N Z C I D V
                                                                         + + + - - -

                                        addressing    assembler    opc  bytes  cyles
                                        --------------------------------------------
                                        immidiate     CPX #oper     E0    2     2
                                        zeropage      CPX oper      E4    2     3
                                        absolute      CPX oper      EC    3     4 */
                              case 0xE0: compare(state.getX() , cargs[1]);                            	  
                                         break;
                              case 0xE4:
                              case 0xEC:  compare(state.getX() , bus.read(effectiveAddress)); 
                                         break;


                                       /*CPY  Compare Memory and Index Y

                                        Y - M                            N Z C I D V
                                                                         + + + - - -

                                        addressing    assembler    opc  bytes  cyles
                                        --------------------------------------------
                                        immidiate     CPY #oper     C0    2     2
                                        zeropage      CPY oper      C4    2     3
                                        absolute      CPY oper      CC    3     4  */
                              case 0xC0: compare(state.getY() , cargs[1]); 
                              break;
                   case 0xC4:
                   case 0xCC: compare(state.getY() , bus.read(effectiveAddress));                	   
                              break;

/*                              JSR  Jump to New Location Saving Return Address

                              push (PC+2),                     N Z C I D V
                              (PC+1) -> PCL                    - - - - - -
                              (PC+2) -> PCH

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              absolute      JSR oper      20    3     6  */
                   case 0x20: temp = state.getPc() - 1;                	          
                              bus.write(state.push(), (byte) ((temp >> 8) & 0xff));
                              bus.write(state.push(), (byte) (temp & 0xff));
                              state.setPc(cargs[2] * 256 + cargs[1]);
                              break;

                              /*PHA  Push Accumulator on Stack

                              push A                           N Z C I D V
                                                               - - - - - -

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       PHA           48    1     3  */
                   case 0x48: bus.write(state.push(), state.getA());
                              break;

/*                             PHP  Push Processor Status on Stack

                              push SR                          N Z C I D V
                                                               - - - - - -

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       PHP           08    1     3  */
                   case 0x08: bus.write(state.push(), state.getFlags());
                              break;

/*                             PLA  Pull Accumulator from Stack

                              pull A                           N Z C I D V
                                                               + + - - - -

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       PLA           68    1     4  */
                   case 0x68:  temp =  bus.read(state.pop());
                	           state.setA((byte) temp);
                	           setArithmeticFlags(temp);
                	           break;
                           

/*                             PLP  Pull Processor Status from Stack

                              pull SR                          N Z C I D V
                                                               from stack

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       PHP           28    1     4  */
                   case 0x28: temp =  bus.read(state.pop());
               	           state.setFlags((byte) temp);
             	           break;



/*                             RTI  Return from Interrupt

                              pull SR, pull PC                 N Z C I D V
                                                               from stack

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       RTI           40    1     6  */
                   case 0x40:  temp =  bus.read(state.pop());
       	                       state.setFlags((byte) temp);
       	                       temp = bus.read(state.pop()) & 0xff;
                               temp = bus.read(state.pop()) * 256 + temp;
                               temp = temp & 0xffff;
                               state.setPc(temp);
                               state.setInteruptDisableFlag(false);
                               break;
/*                             RTS  Return from Subroutine

                              pull PC, PC+1 -> PC              N Z C I D V
                                                               - - - - - -

                              addressing    assembler    opc  bytes  cyles
                              --------------------------------------------
                              implied       RTS           60    1     6  */
                   case 0x60: 
                	  temp = (bus.read(state.pop()) &0xff);
                      temp = bus.read(state.pop()) * 256 + temp;
                      temp = temp & 0xffff;
                      state.setPc(temp+1);
                   break;
                   
                   /*JMP  Jump to New Location

                   (PC+1) -> PCL                    N Z C I D V
                   (PC+2) -> PCH                    - - - - - -

                   addressing    assembler    opc  bytes  cyles
                   --------------------------------------------
                   absolute      JMP oper      4C    3     3
                   indirect      JMP (oper)    6C    3     5*/
                   
                   case 0x4c:
                   case 0x6c:
                	          state.setPc(effectiveAddress);
                	   break;
//NOP                	   
                   case 0xea:
                   break;
                                        
			
        default: 
        	System.out.println("ddddddddddddddddddddddd");
            break;
		}
/*












BRK  Force Break

 interrupt,                       N Z C I D V
 push PC+2, push SR               - - - 1 - -

 addressing    assembler    opc  bytes  cyles
 --------------------------------------------
 implied       BRK           00    1     7



















NOP  No Operation

 ---                              N Z C I D V
                                  - - - - - -

 addressing    assembler    opc  bytes  cyles
 --------------------------------------------
 implied       NOP           EA    1     2














*/

	
	
	/*
A  .... Accumulator   OPC A   operand is AC 
!!abs  .... absolute   OPC $HHLL   operand is address $HHLL 
!!abs,X  .... absolute, X-indexed   OPC $HHLL,X   operand is address incremented by X with carry 
!!abs,Y  .... absolute, Y-indexed   OPC $HHLL,Y   operand is address incremented by Y with carry 
#  .... immediate   OPC #$BB   operand is byte (BB) 
impl  .... implied   OPC   operand implied 
!!ind  .... indirect   OPC ($HHLL)   operand is effective address; effective address is value of address 
!!X,ind  .... X-indexed, indirect   OPC ($BB,X)   operand is effective zeropage address; effective address is byte (BB) incremented by X without carry 
!!ind,Y  .... indirect, Y-indexed   OPC ($LL),Y   operand is effective address incremented by Y with carry; effective address is word at zeropage address 
!!rel  .... relative   OPC $BB   branch target is PC + offset (BB), bit 7 signifies negative offset 
!!zpg  .... zeropage   OPC $LL   operand is of address; address hibyte = zero ($00xx) 
!!zpg,X  .... zeropage, X-indexed   OPC $LL,X   operand is address incremented by X; address hibyte = zero ($00xx); no page transition 
!!zpg,Y  .... zeropage, Y-indexed   OPC $LL,Y   operand is address incremented by Y; address hibyte = zero ($00xx); no page transition 

	 */
	//Resolve address
	//TODO bus read/write

		
	}
	
	public int[] getVideoRam() {
		int[] vram = new int[1000];
		for (int i=1024; i < 2024; i++) {
			vram[i-1024] = bus.read(i) & 0xff;
		}
		return vram;
	}
	
	/*public static void main(String[] args) {
      Cpu cpu = new Cpu();
      //cpu.setEnableDebug(true);
      while (cpu.getCycles() < 5210000)  {
        cpu.step();
      }*/
      
      //cpu.setEnableDebug(true);
      
      //for (int i=0; i< 30000; i++) {
   // 	  cpu.step();
    //  }
      
      //System.out.println(cpu.getReadBus(0x0210));
      
      

   //}
	
}
