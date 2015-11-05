package co.za.jjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MemoryRegionROM implements MemoryRegion {

    private byte[] romMemory;
    private int regionStart;
    private int regionEnd;
	
	@Override
	public int getRegionStart() {
		// TODO Auto-generated method stub
		return regionStart;
	}

	@Override
	public int getRegionEnd() {
		// TODO Auto-generated method stub
		return regionEnd;
	}

	@Override
	public byte read(int address) {
		// TODO Auto-generated method stub
		address = address & 0xffff;
		return romMemory[address - (regionStart)];
		
	}

	@Override
	public void write(int address, byte num) {
		// TODO Auto-generated method stub
		
	}
	
	public MemoryRegionROM(String romFileName, int startAddress, int endAddress) {
		try {
			FileInputStream fis = new FileInputStream(new File(romFileName));
			romMemory = new byte[endAddress - startAddress +1];
			regionStart = startAddress;
			regionEnd = endAddress;
			try {
				fis.read(romMemory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return false;
	}

}
