package co.za.jjs;

public class IOMem implements MemoryRegion{

	//vic
	//colorram
	//cia1 + 2
	
	
	private static VICII vicii;
	private static ColorRAM colorRAM;
	private static CIA cia1;
	private static CIA cia2;
	public int getRegionStart() {
		return 0xd000;
	}

	@Override
	public int getRegionEnd() {
		return 0xdfff;
	}

	@Override
	public byte read(int address) {
		address = address & 0xffff;
		if ((address>=0xd000) & (address<=0xd100))
			return vicii.read(address);
		else if ((address>=0xdc00) & (address<=0xdcff))
		    return cia1.read(address);
		else if ((address>=0xdd00) & (address<=0xddff))
			return cia2.read(address);
		else if ((address>=0xd800) & (address<=0xdbe7))
			return colorRAM.read(address);
		
		return 0;
	}

	@Override
	public void write(int address, byte num) {
		address = address & 0xffff;
		if ((address>=0xd000) & (address<=0xd100))
			vicii.write(address,num);
		else if ((address>=0xdc00) & (address<=0xdcff))
		    cia1.write(address,num);
		else if ((address>=0xdd00) & (address<=0xddff))
			cia2.write(address,num);
		else if ((address>=0xd800) & (address<=0xdbe7))
			colorRAM.write(address,num);		
	}

	@Override
	public boolean isWritable() {		
		return true;
	}
	
	public IOMem(VICII vicii, ColorRAM colorram, CIA cia1, CIA cia2) {
		this.vicii = vicii;
		this.cia1 = cia1;
		this.cia2 = cia2;
		this.colorRAM = colorram;
	}

}
