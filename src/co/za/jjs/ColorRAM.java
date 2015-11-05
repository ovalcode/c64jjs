package co.za.jjs;

public class ColorRAM implements MemoryRegion {
//$D800-$DBE7  55296-56295   1 kB (1000 bytes) of color memory
	byte[] colorRAM = new byte[1000];
	@Override
	public int getRegionStart() {
		// TODO Auto-generated method stub
		return 0xd800;
	}
	
	public byte[] getArray() {
		return colorRAM;
	}

	@Override
	public int getRegionEnd() {
		// TODO Auto-generated method stub
		return 0xdbe7;
	}

	@Override
	public byte read(int address) {
		// TODO Auto-generated method stub
		return colorRAM[address - getRegionStart()];
	}

	@Override
	public void write(int address, byte num) {
		colorRAM[address - getRegionStart()] = (byte) (num & 0xf);
		
	}

	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

}
