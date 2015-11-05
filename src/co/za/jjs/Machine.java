package co.za.jjs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Machine {

	private ArrayList<MemoryRegion> memoryRegions = new ArrayList<MemoryRegion>();
	private ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	private CIA cia2;
	private InterruptInterface interruptSource1;
	private InterruptInterface interruptSource2;
	private static final int BASIC_ROM_VISIBLE = 1;
	private static final int KERNEL_ROM_VISIBLE = 2;
	private static final int CHAR_ROM_VISIBLE = 4;
	private static final int IO_VISIBLE = 8;
	
	private static final int[] MEMORY_ENABLED = {
			0,//000
			CHAR_ROM_VISIBLE,//001 -> RAM visible in two code roms                                         | CHAR ROM visible
			CHAR_ROM_VISIBLE | KERNEL_ROM_VISIBLE,//010 -> RAM visible at $A000-$BFFF; KERNAL ROM visible at $E000-$FFFF.       | CHAR ROM visible
			CHAR_ROM_VISIBLE | BASIC_ROM_VISIBLE | KERNEL_ROM_VISIBLE, //011 -> BASIC ROM visible at $A000-$BFFF; KERNAL ROM visible at $E000-$FFFF. | CHAR ROM visible
			0,//100
			IO_VISIBLE, //101 -> RAM visible in two code roms                                         | IO visible
			IO_VISIBLE | KERNEL_ROM_VISIBLE,//110 -> RAM visible at $A000-$BFFF; KERNAL ROM visible at $E000-$FFFF.       | IO visible
			IO_VISIBLE | BASIC_ROM_VISIBLE | KERNEL_ROM_VISIBLE //111 -> BASIC ROM visible at $A000-$BFFF; KERNAL ROM visible at $E000-$FFFF. | IO visible
			
	};
	
	private boolean playPressed = false;
	private int joyBits = 0;
	private int inputMode = 0;
	
	public void setPlayPressed(boolean playPressed) {
		this.playPressed = playPressed;
	}
	
	public int getJoyBits() {
		return ~joyBits & 0xff;
	}
	public int getInputMode() {
		return inputMode;
	}
	
	private byte[] mainMem = new byte[65536];
	private int[] keys  =null;
	
	private Cpu cpu;
	private Tape tape;
	private Keyboard keyboard;
	private VICII vicii;
	
	public byte[] getDumpOfMainMem() {
		return mainMem;
	}
	
	public byte[] getDumpOfScreenColor() {
		return vicii.getColorRAM();
	}
	public void setKeys(int[] keys) {
		//loop through keys
		//Arrays.cop
		//f1 112 - keyboard
		//f2 113 - Joy A
		//f3 114 - Joy B
		//Num Up 224
		//Num Down 225 
		//Num Left 226
		//Num Right 227
		//Num 0 fire 155
    	/*Bit 0 (weight 1) goes low if the "up" switch is activated, 
		Bit 1 (weight 2) goes low if the "down" switch is activated, 
		Bit 2 (weight 4) goes low if the "left" switch is activated, 
		Bit 3 (weight 8) goes low if the "right" switch is activated, 
		Bit 4 (weight 16) goes low if the fire button is pressed. */
		//TODO: create val inputmode
		//varaible joy bits
		//create reada + readb in keyboard
		ArrayList<Integer> myList = new ArrayList<Integer>();
		joyBits = 0;
		for (int currentChar : keys) {
			switch (currentChar) {
    			case 112: this.inputMode = 0; //f1 keyboard
    			          break;
	     		case 113: this.inputMode = 1; //f2 Joy A
	     		          break;
	     		case 114: this.inputMode = 2;//f3 Joy B
	     		          break;
	     		case 224: joyBits |= 1;//Num Up
	     		  		  break;
	     		case 225: joyBits |= 2;//Num Down
	     		          break;
	     		case 226: joyBits |= 4;//Num Left
	     		          break;
	     		case 227: joyBits |= 8;//Num Right
	     		          break;
	     		case 155: joyBits |= 16;//Num Fire
	     		          break;
	     		default:  myList.add(currentChar);
			}
		}
		this.keys = new int[myList.size()];
		int i = 0;
		for (int currentChar : myList) {
			this.keys[i] = currentChar;
			i++;
		}

	}
	
	public void stepCPU() {
		cpu.step();
		processAlarms();
	}
	
	public int[] getKeys() {
		return this.keys;
	}
	
	public byte read(int address) {
	  address = address & 0xffff;
		//if (address < 3)
		//System.out.println("read " + address + " " + mainMem[address]);
      int i = 0;
	  for (MemoryRegion region : memoryRegions) {
		  if ((address >= region.getRegionStart()) & (address <= region.getRegionEnd())) {
			  if (regionIsEnabled(i))
			    return region.read(address);
		  }
		  i++;
	  }
	  int temp = mainMem[address] & 0xff;
	  if (address == 1) {
		  if (!playPressed)
		    temp = temp | 16;
		  else
			temp = temp & (255 -16);
	  }	  
	  return (byte) temp;//mainMem[address];(bankBits == 3)
	}
	
	public void addAlarm(Alarm alarm) {
		if (!alarms.contains(alarm)) {
			alarms.add(alarm);
		}
	}
	
	public void clearCPUInterrupts() {
		cpu.clearInterupt();
	}
	
	public void interruptCPU()  {
		cpu.setInterrupt();
	}
	
	public boolean regionIsEnabled(int regionNumber) {
		//0 basic
		//1 kernel
		//2 char
		//3 io
		int bankBits = mainMem[1] & 7;
		switch (regionNumber) {
		  case 0 : return (bankBits == 3) | (bankBits == 7);
		  case 1 : return (bankBits == 2) | (bankBits == 3) | (bankBits == 7) | (bankBits == 6);
		  case 2 : return (bankBits == 1) | (bankBits == 2) | (bankBits == 3);
		  case 3 : return (bankBits == 5) | (bankBits == 6) | (bankBits == 7);
		  default: return false;
		}

	}
			
	
	public void write(int address, byte num) {
		  address = address & 0xffff;
		  
			//if (address < 3)
			//	System.out.println("write " + address + " " + num);
          //TODO: make memory regiona array
		  //loop using a counter
		  //make character rom also region of its own
		  //change region is enabled to pass number
		  //do same process for read
		  //create class for IO and stash all IO instances, e.g. VIC, Color ram, CIA1 and CIA2
		  int i = 0;
		  for (MemoryRegion region : memoryRegions) {
			  if ((address >= region.getRegionStart()) & (address <= region.getRegionEnd())) {
				  if (region.isWritable() & regionIsEnabled(i)) {
				    region.write(address, num);
				    return;
				  }
			  }
			  i++;
		  }
		  mainMem[address] = num;
		  
		  if (address == 1) {
			  if ((num & 32) == 0)
				  tape.start();
			  else
				  tape.stop();
		  }
		  
		  return;
	}
	
	public long getCycles() { 
		return cpu.getCycles();
	}
	
	private void processAlarms() {
		for (Alarm alarm : alarms) {
			if ((alarm.getExpiry() < getCycles()) & alarm.processAlarm()) {
				alarm.alarmCallback();
			}
		}
	}
	
	public boolean hasInterruptOccured() {
		return this.interruptSource1.hasInterruptOccured() | this.interruptSource2.hasInterruptOccured(); 
	}
	
	public int readVIC(int address) {
		address = address & 0x3fff;		
		int regDD00 = cia2.reada();
		regDD00 = (~regDD00) & 3;
		if((regDD00 == 0) | (regDD00 == 2))
			if ((address >=0x1000) & (address <=0x1fff))
    			return memoryRegions.get(2).read((address & 0xfff) + 53248);
		regDD00 = regDD00 << 14;
        return mainMem[regDD00 + address];
	}
	
	public int[] getTextRAM() {
		long cyclesToEnd = cpu.getCycles() + 16666;
		//cpu.setEnableDebug(true);
		while(cpu.getCycles() < cyclesToEnd) {
			cpu.step();
			processAlarms();
		}
		int[] vram = new int[1000];
		for (int i=1024; i < 2024; i++) {
			vram[i-1024] = this.read(i) & 0xff;
		}
		return vram;
	}
    
	public int[] getFrame() {
		return vicii.getFrame();
	}

	
	public Machine() {
		//TODO: memory Region object
		//TODO: Remember character rom
		memoryRegions.add(new MemoryRegionROM("/home/johan/basic.901226-01.bin", 0xa000, 0xbfff));
		memoryRegions.add(new MemoryRegionROM("/home/johan/kernal.901227-03.bin", 0xe000, 0xffff));		
		memoryRegions.add(new MemoryRegionROM("/home/johan/characters.901225-01.bin", 0xd000, 0xdfff));
		ColorRAM colorRAM = new ColorRAM();
		//TODO: move color ram to new IO object		
		//memoryRegions.add(new ColorRAM());
		//TODO: VIC: Remove character rom out
		//Remove machine.read + charrom access
		//TODO: VIC read model
		//create method readVIC
		//vic bank access -> remember access to charrom
		//create a class for VICmemory model
		//pass vicmodel class link to mainmem + charrom + cia2
		//vicii will access vicmemomdel via machine with readvicmod
		vicii = new VICII(this, colorRAM);
		//memoryRegions.add(vicii);
		//TODO: + CIA2 add to IO object
		CIA cia1 = new CIA(this);
		this.interruptSource1 = cia1.getInterruptHandler();
		this.interruptSource2 = vicii;
		//memoryRegions.add(cia1);
		tape = new Tape(this, cia1.getInterruptHandler());
		keyboard = new Keyboard(this);
		cia1.setReadPoint(keyboard);
		keyboard.setReadPoint(cia1);
		cia2 = new CIA(this);
		memoryRegions.add(new IOMem(vicii, colorRAM, cia1, cia2));
		try {
			FileInputStream fis = new FileInputStream("/home/johan/dump.bin");
			fis.read(mainMem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainMem[1] = 7;
		cpu = new Cpu(this);
		
/*		bus = new Bus();
		state = new State();
		int temp = bus.read(0xfffd) * 256 + (bus.read(0xfffc) &0xff );
		temp = temp & 0xffff;
		state.setPc(temp);*/ 

	}
}
