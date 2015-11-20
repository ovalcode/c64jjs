package co.za.jjs;

import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class VICII implements Alarm, MemoryRegion, InterruptInterface{

	private byte[] mem = new byte[0x400];
	private Machine machine;
	private long lastFrameCycle = 0;
	private long currentFrameCycle = 0;
	private static final int BORDER_CYCLES = 3;
	private static final int BORDER_LINES = 51;
	private static final int SCREEN_CYCLES = 40;
	private static final int SCREEN_LINES = 200;
	private static final int RASTER_CORRECTION_TERM = 0;
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

	}

	@Override
	public int getRegionEnd() {
		// TODO Auto-generated method stub
		return 0xd3ff;
	}
	
	private long limitRaster(long rasterLine) {
		if (rasterLine < 0)
			rasterLine = 0;
		return rasterLine;
	}

	@Override
	public byte read(int address) {
		//if (address != 53280) System.out.println("VIC READ " + address + " " + mem[address - 0xd000]);
		address = address & 0xffff;
		if (address == 0xd012) return (byte)(limitRaster((currentFrameCycle / 63) + RASTER_CORRECTION_TERM /*+ 21*/) & 0xff);
		int temp =  mem[address - 0xd000];
		if (address == 0xd011) {
			temp = temp & 0x7f;
			long currentLine = limitRaster((currentFrameCycle / 63) + RASTER_CORRECTION_TERM)/*+ 21*/;
			if (currentLine > 255)
				temp = temp | 128;
		}
		return (byte) temp;
	}
	
	private boolean inScreenRegion(int row, int col) {
		return (col >= BORDER_CYCLES) & (col < BORDER_CYCLES + SCREEN_CYCLES) &
			   (row >= BORDER_LINES) & (row < (BORDER_LINES + SCREEN_LINES));
	}
	
	private void fillBorder(int row, int col) {
		int pixelColPos = col << 3;
		int pixelPos_linear = (VISIBLE_SCREEN_PIXEL_WIDTH * row + pixelColPos) *3;
		for (int i = 0; i < 8; i++) {			
			pixels[pixelPos_linear + 0] =  COLOR_TABLET[mem[0x20] & 0xf].red;
			pixels[pixelPos_linear + 1] =  COLOR_TABLET[mem[0x20] & 0xf].green;
			pixels[pixelPos_linear + 2] =  COLOR_TABLET[mem[0x20] & 0xf].blue;
			pixelPos_linear = pixelPos_linear + 3;
		}
	}
	
	private RasterByte draw8bits(int posX, int posY, int bits, int bitcolor, int background) {
		int transparencyInfo = 0;
		RGB[] colors = new RGB[8]; 
		for (int i = 0; i < 8; i++) {
			bits = bits << 1;
			boolean setPixel = (bits & 256) != 0;
			int color;
			if (setPixel) {
				color = bitcolor & 0xf;
			} else {
				color = background & 0xf;
			}
			transparencyInfo = transparencyInfo << 1;
			transparencyInfo = transparencyInfo | (setPixel ? 1 : 0);
			colors[i] = COLOR_TABLET[color];
		}
		return new RasterByte(transparencyInfo, colors);
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

	
	private RasterByte draw8bitsMulticolor (int posX, int posY, int bits, int[] palette) {
		int transparencyInfo = 0;
		RGB[] colors = new RGB[8]; 

		for (int i = 0; i < 4; i++) {
			bits = bits << 2;
			int palletteEntry = (bits & (256 + 512)) >> 8;
			transparencyInfo = transparencyInfo << 2;
			/*if ((mem[0x11] & 32) == 32)
				charRasterLine = machine.readVIC(((charPosLinear << 3) + ((row - BORDER_LINES) & 7) + graphicsBase));
			else 
			    charRasterLine = machine.readVIC((charCode << 3) + ((row -BORDER_LINES) & 7) + graphicsBase);*/
			if ((mem[0x11] & 32) != 32) {
				if ((palletteEntry != 0) & (palletteEntry != 1)) {
					transparencyInfo = transparencyInfo | 3;
				}				
			} else {
				if (palletteEntry != 0) {
					transparencyInfo = transparencyInfo | 3;
				}				
			}
			colors[(i <<1) + 0] = COLOR_TABLET[palette[palletteEntry]];
			colors[(i <<1) + 1] = COLOR_TABLET[palette[palletteEntry]];			
		}
		return new RasterByte(transparencyInfo, colors);
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

	
	public RasterByte drawCharacterLine(int row, int col) {
		//d016 bit 4 - multicolor mode
		//d011 bit 5 - bit map mode
		//24| $d018 |VM13|VM12|VM11|VM10|CB13|CB12|CB11|  - | Memory pointers
		int reg18 = mem[0x18] & 0xff;
		int screenBase = ((reg18 >> 4) << 10);
		int graphicsBase = ((reg18 & 0xe) << 10);
		int charPosY = (row - BORDER_LINES) >> 3;
		int charPosX = col - BORDER_CYCLES;
		int charPosLinear = charPosY * 40 + charPosX;
		int charCode = machine.readVIC(charPosLinear + screenBase) & 0xff;
		int colorCode = colorRAM.read(charPosLinear + 0xd800) & 0xf;
		//if BMM get from b
		int charRasterLine;
		if ((mem[0x11] & 32) == 32)
			charRasterLine = machine.readVIC(((charPosLinear << 3) + ((row - BORDER_LINES) & 7) + graphicsBase));
		else 
		    charRasterLine = machine.readVIC((charCode << 3) + ((row -BORDER_LINES) & 7) + graphicsBase);
		
		if (isMulticolor(colorCode)) {
			int[] colorTablet = ((mem[0x11] & 32) == 32) ? getMultiColorPalleteHighRes(charCode, colorCode) : getMultiColorPalleteTextMode(charCode, colorCode);
			return draw8bitsMulticolor(col << 3, row, charRasterLine, colorTablet);
		} else {
			return draw8bits(col << 3, row, charRasterLine, colorCode, mem[0x21]);
		}
		//if multicolor and charcode is more than 7 then multicolor
		//else if multicolor then high res with color code
		//else if high res
		//else standard color
		//draw8bits(col << 3, row, charRasterLine, colorCode, mem[0x21]);
	}
	
	protected boolean isSpriteMulticolor(int spriteNumber) {
		return ((1 << spriteNumber) & mem[28]) == (1 << spriteNumber);
	}
	
	protected int getSpriteDataPair(int spriteNumber, int row, int col, int pixelPosInCol) {
		boolean xExpanded = ((1 << spriteNumber) & mem[29]) == (1 << spriteNumber);
		boolean yExpanded = ((1 << spriteNumber) & mem[23]) == (1 << spriteNumber);
		int spriteFromX = mem[spriteNumber << 1] & 0xff;
		if (((1 << spriteNumber) & mem[16]) == (1 << spriteNumber))
			spriteFromX = spriteFromX + 256;
		int spriteFromY = mem[(spriteNumber << 1) + 1] & 0xff;
		int pixelPosX = (col << 3) + pixelPosInCol;
    	 int spriteCanvasX = pixelPosX - spriteFromX;
		 int spriteCanvasY = row - spriteFromY;
		if (xExpanded) spriteCanvasX = spriteCanvasX >> 1;
		if (yExpanded) spriteCanvasY = spriteCanvasY >> 1;
		
		 int spriteLinearPos = (spriteCanvasY * 3) + (spriteCanvasX >> 3);
		 int spritePOSinByte = spriteCanvasX & 7;
		 int reg18 = mem[0x18] & 0xff;
		int spritePointerBase = ((reg18 >> 4) << 10);
		spritePointerBase = spritePointerBase + 1024 - 8;

		 int spriteStartAddress = (machine.readVIC(spritePointerBase + spriteNumber) & 0xff) * 64;
		 int spriteByteData = machine.readVIC(spriteStartAddress + spriteLinearPos) & 0xff;
			if (isSpriteMulticolor(spriteNumber)) {
				spritePOSinByte = spritePOSinByte >> 1;
		        spritePOSinByte = spritePOSinByte << 1;
				int dataPair = spriteByteData & ((128 + 64) >> spritePOSinByte);
				dataPair = dataPair >> (6 - spritePOSinByte);
	            return dataPair;
			} else {
				int dataPair = spriteByteData & ((128) >> spritePOSinByte);
				dataPair = dataPair >> (7 - spritePOSinByte);
	            return dataPair;			
			}

	}
		
	protected boolean isPixelTransparent(int spriteNumber, int row, int col, int pixelPosInCol) {
      return getSpriteDataPair(spriteNumber, row, col, pixelPosInCol) == 0;
	}
	
	protected RGB getPixelColor(int row, int col, int posInByte, int spriteNumber) {
		if (isSpriteMulticolor(spriteNumber)) {
			int dataPair = getSpriteDataPair(spriteNumber,row,col,posInByte);
			if (dataPair == 1) {
				return COLOR_TABLET[mem[0x25] & 0xf];//0x25
			} else if (dataPair == 3) {
				return COLOR_TABLET[mem[0x26] & 0xf];//0x26
			} else {
				//d027 sprite 0
				return COLOR_TABLET[mem[0x27 + spriteNumber] & 0xf];//0x27
			}
            
		} else {
            return COLOR_TABLET[mem[0x27 + spriteNumber] & 0xf];//0x27;			
		}
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
	
	
	private boolean pixelInSpriteRange(int spriteNumber, int row, int col, int pixelPosInCol) {
		boolean xExpanded = ((1 << spriteNumber) & mem[29]) == (1 << spriteNumber);
		boolean yExpanded = ((1 << spriteNumber) & mem[23]) == (1 << spriteNumber);
		int spriteFromX = mem[spriteNumber << 1] & 0xff;
		if (((1 << spriteNumber) & mem[16]) == (1 << spriteNumber))
			spriteFromX = spriteFromX + 256;
		int spriteFromY = mem[(spriteNumber << 1) + 1] & 0xff;
		
		int spriteToX = spriteFromX +  (xExpanded ? 48 : 24);//24;
		int spriteToY = spriteFromY + (yExpanded ? 42 : 21);

		int pixelPosX = (col << 3) + pixelPosInCol;
		if (((pixelPosX >= spriteFromX) & (pixelPosX < spriteToX)) &
		   ((row >= spriteFromY) & (row < spriteToY))) {
			return true;
		} else {
			return false;
		}

	}
	
	public RasterByte[] processSpritesForLine(int row , int col) {
		int reg18 = mem[0x18] & 0xff;
		int spritePointerBase = ((reg18 >> 4) << 10);
		spritePointerBase = spritePointerBase + 1024 - 8;
		RasterByte[] rasterByteArray = new RasterByte[8];
		for (int spriteNumber = 7; spriteNumber >= 0; spriteNumber--) {
			int transparency = 0;
			RGB[] colors = new RGB[8];
			if (((1 << spriteNumber) & mem[21]) == (1 << spriteNumber)) {
			  for (int currentPixelInCol = 0; currentPixelInCol < 8; currentPixelInCol++) {
				  transparency = transparency << 1;
				  if (pixelInSpriteRange(spriteNumber, (int)limitRaster(row + RASTER_CORRECTION_TERM)/* + 21*/, col, currentPixelInCol)) {
					  if (!isPixelTransparent(spriteNumber, (int)limitRaster(row + RASTER_CORRECTION_TERM)/*+ 21*/, col, currentPixelInCol)) {
						    transparency = transparency | 1;
							RGB pixColor = getPixelColor((int)limitRaster(row + RASTER_CORRECTION_TERM)/*+21*/, col, currentPixelInCol, spriteNumber);
                            colors[currentPixelInCol] = pixColor;
					  }
				  }
			  }
			  	
//if sprite is enabled
//loop through col pixels and call is transparent -> NB!! if not within sprite range transparent is also true
//if not transparent get sprite pixel color
				}
			rasterByteArray[spriteNumber] = new RasterByte(transparency, colors);
			}
		return rasterByteArray;
		}
	
	private RGB[] mergeColorsWithForeground(RGB[] mergedSpriteBGColors, int mergedSpriteTransparency, RasterByte screenData) {
		RGB[] newColors = new RGB[8];
		for (int currentPixel = 0; currentPixel < 8; currentPixel++) {
			if ((screenData.transparancyInfo & (128 >> currentPixel)) != 0) {
				newColors[currentPixel] = screenData.pixelColors[currentPixel];
			} else if ((mergedSpriteTransparency & (128 >> currentPixel)) != 0) {
				newColors[currentPixel] = mergedSpriteBGColors[currentPixel];
			} else {
				newColors[currentPixel] = screenData.pixelColors[currentPixel];
			}
		}
		return newColors;
	}
	
	private RGB[] mergeForegroundSprite(RasterByte SpriteData, RGB[] currentColors) {
		RGB[] newColors = new RGB[8];
		for (int currentPixel = 0; currentPixel < 8; currentPixel++) {
            if ((SpriteData.getTransparencyInfo() & (128 >> currentPixel)) != 0) {
				newColors[currentPixel] = SpriteData.pixelColors[currentPixel];
			} else {
				newColors[currentPixel] = currentColors[currentPixel];
			}
		}
		return newColors;
	}

	
	private RGB[] writeColors(RasterByte screenData, RGB[] existingColors) {
		for (int currentPixel = 0; currentPixel < 8; currentPixel++) {
				existingColors[currentPixel] = screenData.pixelColors[currentPixel];
		}
		return existingColors;
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
		
		//for each dot
		// for each sprite		
		//   
		RGB[] colorsToWrite = new RGB[8];
		for (int i = 0; i < 8; i++) {
			colorsToWrite[i] = COLOR_TABLET[mem[0x21] & 0xff];
		}
		RasterByte screenData = drawCharacterLine(row, column);
		RasterByte[] spriteData = processSpritesForLine(row, column);
		int priority = mem[0x1b] & 0xff;
		//for (int currentPixel = 0; currentPixel < 8; currentPixel++) {
		ArrayList<Integer> backgroundSprites = new ArrayList<Integer>(); 
		ArrayList<Integer> forgroundSprites = new ArrayList<Integer>();
		boolean spriteDataWritten = false;
		for (int spriteNumber = 0; spriteNumber < 7; spriteNumber++) {
			if (spriteData[spriteNumber].transparancyInfo != 0) {
				spriteDataWritten = true;
				int currentPriority = ((priority & (1 << spriteNumber)) != 0) ? 1 : 0;
				if (currentPriority == 1) {
					backgroundSprites.add(spriteNumber);
				} else {
					forgroundSprites.add(spriteNumber);
				}	
			}
		}
			
		int tranparencyAcc = 0;
			
		for (int backgroundNum = 0; backgroundNum < backgroundSprites.size(); backgroundNum++) {
			int currentSprite = backgroundSprites.get(backgroundSprites.size() - backgroundNum - 1);
			colorsToWrite = mergeForegroundSprite(spriteData[currentSprite], colorsToWrite);
			tranparencyAcc = tranparencyAcc | (spriteData[currentSprite].getTransparencyInfo());
			spriteDataWritten = true;
		}
		
		colorsToWrite = mergeColorsWithForeground(colorsToWrite, tranparencyAcc, screenData);
		for (int foregroundNum = 0; foregroundNum < forgroundSprites.size(); foregroundNum++) {
			int currentSprite = forgroundSprites.get(forgroundSprites.size() - foregroundNum -1);
			spriteDataWritten = true;
			colorsToWrite = mergeForegroundSprite(spriteData[currentSprite], colorsToWrite);			
		}

			if (!spriteDataWritten) {
				colorsToWrite = screenData.pixelColors;
			}
			int pixelPos_linear = VISIBLE_SCREEN_PIXEL_WIDTH * row + (column << 3);
			pixelPos_linear = pixelPos_linear * 3;
			for (int i = 0; i < 8; i++) {
				pixels[pixelPos_linear + 0] = colorsToWrite[i].red;
				pixels[pixelPos_linear + 1] = colorsToWrite[i].green;
				pixels[pixelPos_linear + 2] = colorsToWrite[i].blue;
				pixelPos_linear = pixelPos_linear + 3;
			}
		
		//}
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
	
	private boolean rasterlineReached(int currentLine) {
		//if ((((row + 21) & 0xff) == (mem[18] & 0xff))
		int destinatedLine = mem[18] & 0xff;
		if ((mem[0x11] & 128) != 0) {
			destinatedLine = destinatedLine + 256;
		}
		
		return (currentLine == destinatedLine);
		
	}
	
	public int[] getFrame() {
		long beginCycle = 0;
		long endCycle = 0;
		while (endCycle < (312 * 63)) {
			beginCycle = endCycle;
			machine.stepCPU();
			endCycle = machine.getCycles() - lastFrameCycle;
			endCycle = Math.min(endCycle, 312 * 63);
			for (currentFrameCycle = beginCycle; currentFrameCycle < endCycle; currentFrameCycle++) {
				int row = (int) (currentFrameCycle / 63);
				int col = (int) (currentFrameCycle - row * 63);
				if ((mem[26] & 1) == 1) {
					if (rasterlineReached((int)limitRaster(row + RASTER_CORRECTION_TERM) /*+ 21*/) & (col == 0))
						mem[25] &= 0xfe;
				}
				processRowColumn(row, col);
			}			 
		}
		
		lastFrameCycle = machine.getCycles();
        return pixels;
	}

	@Override
	public void write(int address, byte num) {
		address = address & 0xffff;
		//if (address != 53280) System.out.println("VIC WRITE " + address + " " + num);
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
	

	private static class RasterByte {
		  private int transparancyInfo;
		  private RGB[] pixelColors;
		  
		  public RasterByte(int transparancyInfo, RGB[] pixelColors) {
			  this.transparancyInfo = transparancyInfo;
			  this.pixelColors = pixelColors;
		  }
		  
		  public int getTransparencyInfo() {
			  return transparancyInfo;
		  }
		  
		  public RGB[] getColors() {
			  return pixelColors;
		  }
		
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
