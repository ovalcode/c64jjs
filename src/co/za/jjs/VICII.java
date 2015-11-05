package co.za.jjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VICII implements Alarm, MemoryRegion, InterruptInterface{

	private byte[] mem = new byte[0x400];
	private Machine machine;
	private long lastFrameCycle = 0;
	private long currentFrameCycle = 0;
	private static final int BORDER_CYCLES = 6;
	private static final int BORDER_LINES = 42;
	private static final int SCREEN_CYCLES = 40;
	private static final int SCREEN_LINES = 200;
	private ColorRAM colorRAM;
	private static final int VISIBLE_SCREEN_PIXEL_WIDTH = (BORDER_CYCLES + SCREEN_CYCLES + BORDER_CYCLES) * 8;
	private static final int VISIBLE_SCREEN_PIXEL_HEIGHT = (BORDER_LINES + SCREEN_LINES + BORDER_LINES);
	private int[] pixels = new int[VISIBLE_SCREEN_PIXEL_WIDTH * 
	                               VISIBLE_SCREEN_PIXEL_HEIGHT * 3
	                               ];
	byte[] charRom= new byte[4 * 1024];
	
	private static final RGB[] COLOR_TABLET = {		
			new RGB(0,0,0), //black
            new RGB(255,255,255), //white
            new RGB(245,12,0), //red
            new RGB(0,227,246), //cyan
            new RGB(222,0,255), //purple
            new RGB(1,223,0), //green
            new RGB(64,20,255), //blue
            new RGB(191,235,0), //yellow
            new RGB(222,62,0), //orange
            new RGB(132,69,0), //brown
            new RGB(255,60,41), //light red
            new RGB(80,80,80), //dark grey
            new RGB(120,120,120), //grey
            new RGB(65,255,32), // light green
            new RGB(120,76,255),
            new RGB(159,159,159)
	};
	
    public byte[] getColorRAM() {
    	return colorRAM.getArray();
    }
	@Override
	public int getRegionStart() {
		// TODO Auto-generated method stub
		return 0xd000;
	}
	
	public VICII(Machine machine, ColorRAM colorRAM) {
		this.colorRAM = colorRAM;
		this.machine = machine;
		try {
			FileInputStream fis = new FileInputStream("/home/johan/dumpcolorram.bin");
			try {
				fis.read(colorRAM.colorRAM);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mem[0] = (byte)0x96; 
    	mem[1] = (byte)0xA0; 
    	mem[2] = (byte)0x96; 
    	mem[3] = (byte)0xB5; 
    	mem[4] = (byte)0x96; 
    	mem[5] = (byte)0xA1; 
    	mem[6] = (byte)0x96; 
    	mem[7] = (byte)0xB5; 
    	mem[8] = (byte)0x96; 
    	mem[9] = (byte)0xB5; 
    	mem[10] = 0x00; 
    	mem[11] = 0x76; 
    	mem[12] = 0x07; 
    	mem[13] = 0x00; 
    	mem[14] = 0x07; 
    	mem[15] = 0x15; 
    	mem[16] = 0x00; 
    	mem[17] = 0x1B; 
    	mem[18] = 0x00; 
    	mem[19] = 0x00; 
    	mem[20] = 0x00; 
    	mem[21] = 0x1F; 
    	mem[22] = 0x18; 
    	mem[23] = 0x04; 
    	mem[24] = 0x12; //1a 12
    	mem[25] = 0x00; 
    	mem[26] = (byte)0xF1; 
    	mem[27] = (byte)0xFF; 
    	mem[28] = (byte)0xE3; 
    	mem[29] = 0x00; 
    	mem[30] = 0x00; 
    	mem[31] = (byte)0x00 ;
    	mem[32] = (byte)0x01; 
    	mem[33] = 0x00; 
    	mem[34] = 0x0B; 
    	mem[35] = 0x0E; 
    	mem[36] = 0x03; 
    	mem[37] = 0x05; 
    	mem[38] = 0x08; 
    	mem[39] = 0x0D; 
    	mem[40] = 0x0D; 
    	mem[41] = 0x00; 
    	mem[42] = (byte)0x01  ;

	}

	@Override
	public int getRegionEnd() {
		// TODO Auto-generated method stub
		return 0xd3ff;
	}

	@Override
	public byte read(int address) {
		if (address != 53280) System.out.println("VIC READ " + address + " " + mem[address - 0xd000]);
		address = address & 0xffff;
		if (address == 0xd012) return (byte)(((currentFrameCycle / 63) + 16) & 0xff);
		return mem[address - 0xd000];
	}
	
	private boolean inScreenRegion(int row, int col) {
		return (col >= BORDER_CYCLES) & (col < BORDER_CYCLES + SCREEN_CYCLES) &
			   (row >= BORDER_LINES) & (row < (BORDER_LINES + SCREEN_LINES));
	}
	
	private void fillBorder(int row, int col) {
		draw8bits(col << 3, row, 0, 0, mem[0x20] & 0xf);
		/*int pixelColPos = col * 8;
		for (int i = 0; i < 8; i++) {
			int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * row + pixelColPos + i;
			pixelPos_linear = pixelPos_linear * 3;
			pixels[pixelPos_linear + 0] =  COLOR_TABLET[mem[0x20] & 0xf].red;
			pixels[pixelPos_linear + 1] =  COLOR_TABLET[mem[0x20] & 0xf].green;
			pixels[pixelPos_linear + 2] =  COLOR_TABLET[mem[0x20] & 0xf].blue;
		}*/
	}
	
	private void draw8bits(int posX, int posY, int bits, int bitcolor, int background) {
		for (int i = 0; i < 8; i++) {
			bits = bits << 1;
			boolean setPixel = (bits & 256) != 0;
			int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * posY + posX + i;
			pixelPos_linear = pixelPos_linear * 3;
			int color;
			if (setPixel) {
				color = bitcolor & 0xf;
			} else {
				color = background & 0xf;
			}
			pixels[pixelPos_linear + 0] =  COLOR_TABLET[color].red;
			pixels[pixelPos_linear + 1] =  COLOR_TABLET[color].green;
			pixels[pixelPos_linear + 2] =  COLOR_TABLET[color].blue;
		}		
	}
	
	private int[] getMultiColorPalleteHighRes(int screenChar, int colorByte) {
		int[] result = new int[4];
		result[0] = mem[0x21] & 0xf;
		result[1] = (screenChar & 0xf0) >> 4;
		result[2] = (screenChar & 0xf);
		result[3] = colorByte & 0xf;
		return result;
	}
	
	private int[] getMultiColorPalleteTextMode(int screenChar, int colorByte) {
		int[] result = new int[4];
		result[0] = mem[0x21] & 0xf;
		result[1] = mem[0x22] & 0xf;
		result[2] = mem[0x23] & 0xf;
		result[3] = colorByte & 0x7;
		return result;
	}

	
	private void draw8bitsMulticolor (int posX, int posY, int bits, int[] palette) {
		for (int i = 0; i < 4; i++) {
			bits = bits << 2;
			int palletteEntry = (bits & (256 + 512)) >> 8;
			int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * posY + posX + i * 2;
			pixelPos_linear = pixelPos_linear * 3;
			int color;
			color = palette[palletteEntry];
			pixels[pixelPos_linear + 0] =  COLOR_TABLET[color].red;
			pixels[pixelPos_linear + 1] =  COLOR_TABLET[color].green;
			pixels[pixelPos_linear + 2] =  COLOR_TABLET[color].blue;
			pixels[pixelPos_linear + 3] =  COLOR_TABLET[color].red;
			pixels[pixelPos_linear + 4] =  COLOR_TABLET[color].green;
			pixels[pixelPos_linear + 5] =  COLOR_TABLET[color].blue;
			
		}		
	}
	
	public boolean isMulticolor(int colorCode) {
		boolean result = (mem[0x16] & 16) == 16;
		if (result & ((mem[0x11] & 32) != 32)) {
			result = (colorCode & 8) == 8;
		}
		return result;
		//(mem[0x16] & 16) == 16) { is mul  //
		//mem[0x11] & 32) == 32) { bmm
	}

	
	public void drawCharacterLine(int row, int col) {
		//d016 bit 4 - multicolor mode
		//d011 bit 5 - bit map mode
		//24| $d018 |VM13|VM12|VM11|VM10|CB13|CB12|CB11|  - | Memory pointers
		int reg18 = mem[0x18] & 0xff;
		int screenBase = ((reg18 >> 4) << 10);
		int graphicsBase = ((reg18 & 0xe) << 10);
		int charPosY = (row -42) >> 3;
		int charPosX = col - 6;
		int charPosLinear = charPosY * 40 + charPosX;
		int charCode = machine.readVIC(charPosLinear + screenBase) & 0xff;
		int colorCode = colorRAM.read(charPosLinear + 0xd800) & 0xf;
		//if BMM get from b
		int charRasterLine;
		if ((mem[0x11] & 32) == 32)
			charRasterLine = machine.readVIC(((charPosLinear << 3) + ((row - 42) & 7) + graphicsBase));
		else 
		    charRasterLine = machine.readVIC((charCode << 3) + ((row -42) & 7) + graphicsBase);
		
		if (isMulticolor(colorCode)) {
			int[] colorTablet = ((mem[0x11] & 32) == 32) ? getMultiColorPalleteHighRes(charCode, colorCode) : getMultiColorPalleteTextMode(charCode, colorCode);
			draw8bitsMulticolor(col << 3, row, charRasterLine, colorTablet);
		} else {
			draw8bits(col << 3, row, charRasterLine, colorCode, mem[0x21]);
		}
		//if multicolor and charcode is more than 7 then multicolor
		//else if multicolor then high res with color code
		//else if high res
		//else standard color
		//draw8bits(col << 3, row, charRasterLine, colorCode, mem[0x21]);
	}
	
	//row - 42
	//col -6
	//determine character pos
	//get charcater code from memory
	//get color code
	//lookup charcter line
	//draw character line
	//TODO: add color ram region
	/*
	 $D800-$DBE7  55296-56295   1 kB (1000 bytes) of color memory 
	 */
	
	public void processSpritesForLine(int row , int col) {
		int reg18 = mem[0x18] & 0xff;
		int spritePointerBase = ((reg18 >> 4) << 10);
		spritePointerBase = spritePointerBase + 1024 - 8;
		for (int i = 0; i < 8; i++) {
			if (((1 << i) & mem[21]) == (1 << i)) {
				int spriteFromX = mem[i << 2] & 0xff;
				if (((1 << i) & mem[16]) == (1 << i))
					spriteFromX = spriteFromX + 256;
				int spriteFromY = mem[(i << 2) + 1] & 0xff;
				int spriteToX = spriteFromX + 24;
				int spriteToY = spriteFromY + 21;
				int spriteStartAddress = (machine.readVIC(spritePointerBase + i) & 0xff) * 64;
				for (int j = 0; j < 8; j++) {
					int pixelPosX = (col << 3) + j;
					if (((pixelPosX >= spriteFromX) & (pixelPosX < spriteToX)) &
						((row >= spriteFromY) & (row < spriteToY))) {
					 int spriteCanvasX = pixelPosX - spriteFromX;
					 int spriteCanvasY = row - spriteFromY;
					 int spriteLinearPos = spriteCanvasY * 3 + spriteCanvasX >> 3;
					 int spritePOSinByte = spriteCanvasX & 7;
					 int spriteByteData = machine.readVIC(spriteStartAddress + spriteLinearPos) & 0xff;
					 if ((spriteByteData & (0x80 >> spritePOSinByte)) != 0) {
							int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * row + pixelPosX + spritePOSinByte;
							pixelPos_linear = pixelPos_linear * 3;
							pixels[pixelPos_linear + 0] =  255;
							pixels[pixelPos_linear + 1] =  255;
							pixels[pixelPos_linear + 2] =  255;

					 }
					}
					//24x21
					
//					int charPosY = (row -42) >> 3;
//				int charPosX = col - 6;

				}
			}
		}
	}
	
	private void processRowColumn(int row , int column) {
		//6 cycles border in line
		//6 + 6 + 40 visible line
		//42 + 200 + 42 visible height
		//42 lines vborder
		if ((column >= (BORDER_CYCLES + BORDER_CYCLES + SCREEN_CYCLES)) | (row >= (BORDER_LINES + BORDER_LINES + SCREEN_LINES)) ) {
			//outside visible region
			return;
		}
		//We are in visible region
		// $D011
		if (!inScreenRegion(row, column) | ((mem[0x11] & 16) == 0) ) {
			fillBorder(row, column);
			return;
		}
		
		drawCharacterLine(row, column);
		processSpritesForLine(row, column);
		//draw spritesfor line -> row pixel, col = 8bits
		//for each sprite if enabled
		//  -> 
		/*for (int i = 0; i < 8; i++) {
			bits = bits << 1;
			boolean setPixel = (bits & 256) != 0;
			int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * posY + posX + i;
			pixelPos_linear = pixelPos_linear * 3;
			int color;
			if (setPixel) {
				color = bitcolor & 0xf;
			} else {
				color = background & 0xf;
			}
			pixels[pixelPos_linear + 0] =  COLOR_TABLET[color].red;
			pixels[pixelPos_linear + 1] =  COLOR_TABLET[color].green;
			pixels[pixelPos_linear + 2] =  COLOR_TABLET[color].blue;
		}*/		

	}
	
	public int[] getFrame() {
		long beginCycle = 0;
		long endCycle = 0;
		//while (endCycle < (312 * 63)) {
			//beginCycle = endCycle;
			//machine.stepCPU();
			endCycle = machine.getCycles() - lastFrameCycle;
			endCycle = Math.min(endCycle, 312 * 63);
			for (currentFrameCycle = 0; currentFrameCycle < 312 * 63; currentFrameCycle++) {
				int row = (int) (currentFrameCycle / 63);
				int col = (int) (currentFrameCycle - row * 63);
				if ((mem[26] & 1) == 1) {
					if ((((row + 16) & 0xff) == (mem[18] & 0xff)) & (col == 0))
						mem[25] &= 0xfe;
				}
				processRowColumn(row, col);
			}			 
		//}
		
		lastFrameCycle = machine.getCycles();
        return pixels;
	}

	@Override
	public void write(int address, byte num) {
		address = address & 0xffff;
		if (address != 53280) System.out.println("VIC WRITE " + address + " " + num);
		if (address == 0xd021)
			System.out.println(num);
		mem[address - 0xd000] = num;
		
	}

	@Override
	public long getExpiry() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void alarmCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean processAlarm() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static class RGB {
		private int red;
		private int blue;
		private int green;
		public RGB(int red, int green, int blue) {
			this.red = red;
			this.blue = blue;
			this.green = green;
		}
	}

	@Override
	public boolean isWritable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasInterruptOccured() {
		// TODO Auto-generated method stub
		if ((mem[26] & 1) == 1) {
			return ((mem[25] & 1) == 0);
		}
		
		return false;
	}

}
