package co.za.jjs;

public class CIA implements MemoryRegion, ReadHook{

	private byte[] mem = new byte[0x400];
	private Machine machine = null;
	private Timer timerA;
	private Timer timerB;
	private ReadHook readhook;
	private Interrupt interrupt;
	//private long nextAlarm = 0;
	
	
	@Override
	public int getRegionStart() {
		// TODO Auto-generated method stub
		return 0xdc00;
	}
	
	public Interrupt getInterruptHandler() {
		return this.interrupt;
	}
	
	public void setReadPoint(ReadHook readPoint) {
		readhook = readPoint;
	}

	@Override
	public int getRegionEnd() {
		// TODO Auto-generated method stub
		return 0xdd00;
	}
	
	public CIA(Machine machine) {
		this.machine = machine;
		this.interrupt = new Interrupt(machine);
		this.timerA = new Timer(machine, "TA", interrupt);
		this.timerB = new Timer(machine, "TB", interrupt);
	}
	

	@Override
	public byte read(int address) {
		address = address & 0xf;
		//if (address == 0xd012) return 0;
		
		//if ((address > 0xdc03) & !((address == 0xdc0d) & (mem[0xdc0d-0xdc00] == -127)))
		//	System.out.println("READ " + address + " " + mem[address-0xdc00]);

		
		switch (address) {
		case 0: if (readhook != null) return (byte) readhook.reada(); else return mem[0];
		case 1: if (readhook != null) return (byte) readhook.readb(); else return mem[1];
		case 4: return timerA.getTimerLow();
		case 5: return timerA.getTimerHigh();
		case 6: return timerB.getTimerLow();
		case 7: return timerB.getTimerHigh();
		case 0xd : return (byte) (interrupt.readInterrupts() & 0xff);
		case 0xe : return (byte) ((byte) ( (timerA.getStartedBit() ? 1 : 0) | 
				((timerA.getContinious() ? 0 : 1) << 3  )));  
		case 0xf : return (byte) ((byte) ( (timerB.getStartedBit() ? 1 : 0) | 
				((timerB.getContinious() ? 0 : 1) << 3  )));  

		 
		}
		
		//System.out.print(Integer.toHexString(address & 0xf) + ": ");
		//System.out.println(Integer.toHexString(mem[address - 0xdc00] & 0xff));
		/* 
		 4 = timer a low
		 5 = timer a high
		 6 = timer b low
		 7 = timer b high
		 8 = TOD
		 9 = TOD
		 a = tod
		 b = tod
		 c = sdr
		 d = interrupts
		 e = bit 0 = start time a
		     bit 3 1= oneshot 0=continuous
		     bit 4 force load
		 f = bit 0 = start time b
		     bit 3 1= oneshot 0=continuous
		     bit 4 force load

		 */
		byte temp = mem[address];
		return temp;
	}

	@Override
	public void write(int address, byte num) {
		address = address & 0xf;
		
		switch (address) {
		case 4: timerA.setTimerLow(num);
		        return;
		case 5: timerA.setTimerHigh(num);
		        return;
		case 6: timerB.setTimerLow(num);
		        return;
		case 7: timerB.setTimerHigh(num);
		        return;
		case 0xd : interrupt.setInterruptMask(num);
		           return;
		case 0xe : if ((num & 16) != 0)
			         timerA.ForceReload();
			       timerA.setStartTimerBit((num & 1) != 0);
		           timerA.setContinious((num & 8) == 0);			
			       return;
			
		case 0xf : if ((num & 16) != 0)
	                 timerB.ForceReload();
			       timerB.setStartTimerBit((num & 1) != 0);
                   timerB.setContinious((num & 8) == 0);			
         	       return;  

		 
		}
		
		
		mem[address] = num;
		
	}

	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int reada() {
		
		return mem[0] & 0xff;
	}

	@Override
	public int readb() {
		
		return 0;
	}

}
