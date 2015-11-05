package co.za.jjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Bus {
	
	private byte[] kernel = new byte[8192];
	private byte[] basic = new byte[8192];
	private byte[] testbin = new byte[48 * 1024];
	private byte[] mainMem = new byte[65536];
	public byte read(int address) {
		address = address & 0xffff;
		if (((address & 0xffff) >= 0xd000) & ((address & 0xffff) <= 0xdfff))
			System.out.println(address + " " + mainMem[address & 0xffff] + " read");
			
		if (address >= 0xe000)
			return kernel[address - 0xe000];
		if ((address >= 0xa000) & (address <= 0xbfff))
			return basic[address - 0xa000];
		
		
		return mainMem[address & 0xffff];
	}
	
	public void write(int address, byte num) {
		if (((address & 0xffff) >= 0xd000) & ((address & 0xffff) <= 0xdfff))
			System.out.println(address + " " + num + " write");

		//if (address >= 0xe000)
		//	kernel[address - 0xe000] = num;
		mainMem[address & 0xffff] = num;
	}
	
	public Bus() {
		try {
			FileInputStream fis = new FileInputStream(new File("/home/johan/kernal.901227-03.bin"));
			//FileInputStream fis = new FileInputStream(new File("/home/johan/AllSuiteA.bin"));
			try {
				fis.read(kernel);
				//for (int i=0; i<48 *1024; i++) {
				//	mainMem[i + 0x4000] = testbin[i];
				//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fis = new FileInputStream(new File("/home/johan/basic.901226-01.bin"));
			//FileInputStream fis = new FileInputStream(new File("/home/johan/AllSuiteA.bin"));
			try {
				fis.read(basic);
				//for (int i=0; i<48 *1024; i++) {
				//	mainMem[i + 0x4000] = testbin[i];
				//}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
