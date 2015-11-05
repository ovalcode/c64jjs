package co.za.jjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestTape {
	
	public static int getByte(FileInputStream fis) {
		int shiftReg = 0;
		for (int i = 0; i < 8; i++) {
			int rr = 0;
			try {
				rr = fis.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rr = rr * 8;
			shiftReg = shiftReg << 1;
			int bit = (rr > 0x15e) ? 1 : 0;
			shiftReg = shiftReg | bit;
			shiftReg = shiftReg & 0xff;
		}
		
		return shiftReg;

	}
	
	public static void skipSync(FileInputStream fis) {
		int shiftReg = 0;
		for (int i = 0; i < 8000; i++) {
			int rr = 0;
			try {
				rr = fis.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rr = rr * 8;
			shiftReg = shiftReg << 1;
			int bit = (rr > 0x15e) ? 1 : 0;
			shiftReg = shiftReg | bit;
			shiftReg = shiftReg & 0xff;
			if (shiftReg == 0xa0)
				break;
		}

	}
	
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(new File("/home/johan/Dan Dare.tap"));
			fis.skip(0xb00b);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			int checksum = 0;
			for (int i = 0; i < 84; i++) {
				int r = getByte(fis);
				System.out.print(r);
				checksum = checksum ^ r;
				System.out.println(" " + checksum);
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 1002; i++) {
				int r = getByte(fis);
				System.out.println(r);
				checksum = checksum ^ r;
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 8002; i++) {
				int r = getByte(fis);
				System.out.println(r);
				checksum = checksum ^ r;
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 14338 ; i++) {
				int r = getByte(fis);
				System.out.println(r);
				checksum = checksum ^ r;
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 16386 ; i++) {
				int r = getByte(fis);
				System.out.print(r);
				checksum = checksum ^ r;
				System.out.println(" " + checksum);
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 16386 ; i++) {
				int r = getByte(fis);
				System.out.println(r);
				checksum = checksum ^ r;
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 3586 ; i++) {
				int r = getByte(fis);
				System.out.println(r);
				checksum = checksum ^ r;
			}
			System.out.println("Checksum: " + checksum);
			skipSync(fis);
			for (int i = 0; i < 8; i++) {
    			System.out.println(getByte(fis));
			}
			checksum = 0;
			System.out.println("========================");
			for (int i = 0; i < 0x11f4 ; i++) {
				int r = getByte(fis);				
				checksum = checksum ^ r;
				System.out.println("Address: "+(i+0xce0d)+"    "+r + " " + checksum);
			}
			System.out.println("Checksum: " + checksum);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
