package co.za.jjs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.SysexMessage;

public class CreateSource {
	
	/*public static final int[] ADDRESS_MODES = {
			MODES.IMPLIED,MODES.XINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.NONE,MODES.IMPLIED,MODES.IMMEDIATE, MODES.A,MODES.NONE,MODES.NONE,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE,MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGEXINDEXED,MODES.ZEROPAGEXINDEXED,MODES.NONE,MODES.MODES.IMPLIEDIED,MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ABSOLUTEXINDEXED,MODES.ABSOLUTEXINDEXED,MODES.NONE,
			MODES.ABSOLUTE,MODES.XINDEXED,MODES.NONE,MODES.NONE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.NONE,MODES.IMPLIED,MODES.IMMEDIATE,MODES.A,MODES.NONE,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE,MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGEXINDEXED,MODES.ZEROPAGEXINDEXED,MODES.NONE,MODES.IMPLIEDIED,MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ABSOLUTEXINDEXED,MODES.ABSOLUTEXINDEXED,MODES.NONE,
			MODES.IMPLIED,MODES.XINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.NONE,MODES.IMPLIED,MODES.IMMEDIATE,MODES.A,MODES.NONE,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE,MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGEXINDEXED,MODES.ZEROPAGEXINDEXED,MODES.NONE,MODES.IMPLIED,MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ABSOLUTEXINDEXED,MODES.ABSOLUTEXINDEXED,MODES.NONE,
			MODES.IMPLIED,MODES.XINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.NONE,MODES.IMPLIED,MODES.IMMEDIATE,MODES.A,MODES.NONE,MODES.ind,MODES.ABSOLUTE,MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE,MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ZEROPAGEXINDEXED,MODES.ZEROPAGEXINDEXED,MODES.NONE, MODES.IMPLIED,MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE,MODES.ABSOLUTEXINDEXED,MODES.ABSOLUTEXINDEXED,MODES.NONE,
			MODES.NONE, MODES.XINDEXED,MODES.NONE,MODES.NONE, MODES.ZEROPAGE, MODES.ZEROPAGE, MODES.ZEROPAGE,MODES.NONE, MODES.IMPLIED,MODES.NONE, MODES.IMPLIED,MODES.NONE, MODES.ABSOLUTE, MODES.ABSOLUTE, MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE, MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGE,Y,MODES.NONE, MODES.IMPLIED, MODES.ABSOLUTEYINDEXED, MODES.IMPLIED,MODES.NONE,MODES.NONE, MODES.ABSOLUTEXINDEXED,MODES.NONE,MODES.NONE,
			MODES.IMMEDIATE,MODES.XINDEXED,MODES.IMMEDIATE,MODES.NONE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.ZEROPAGE,MODES.NONE,MODES.IMPLIED, MODES.IMMEDIATE, MODES.IMPLIED,MODES.NONE, MODES.ABSOLUTE, MODES.ABSOLUTE, MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE, MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGE,Y,MODES.NONE, MODES.IMPLIED, MODES.ABSOLUTEYINDEXED, MODES.IMPLIED,MODES.NONE, MODES.ABSOLUTEXINDEXED, MODES.ABSOLUTEXINDEXED, MODES.ABSOLUTEYINDEXED,MODES.NONE,
			MODES.IMMEDIATE, MODES.XINDEXED,MODES.NONE,MODES.NONE, MODES.ZEROPAGE, MODES.ZEROPAGE, MODES.ZEROPAGE,MODES.NONE, MODES.IMPLIED, MODES.IMMEDIATE, MODES.IMPLIED,MODES.NONE, MODES.ABSOLUTE, MODES.ABSOLUTE, MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE, MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGEXINDEXED,MODES.NONE, MODES.IMPLIED, MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE, MODES.ABSOLUTEXINDEXED, MODES.ABSOLUTEXINDEXED,MODES.NONE,
			MODES.IMMEDIATE, MODES.XINDEXED,MODES.NONE,MODES.NONE, MODES.ZEROPAGE, MODES.ZEROPAGE, MODES.ZEROPAGE,MODES.NONE, MODES.IMPLIED, MODES.IMMEDIATE, MODES.IMPLIED,MODES.NONE, MODES.ABSOLUTE, MODES.ABSOLUTE, MODES.ABSOLUTE,MODES.NONE,
			MODES.RELATIVE, MODES.INDIRECTYIDEXED,MODES.NONE,MODES.NONE,MODES.NONE, MODES.ZEROPAGEXINDEXED, MODES.ZEROPAGEXINDEXED,MODES.NONE, MODES.IMPLIED, MODES.ABSOLUTEYINDEXED,MODES.NONE,MODES.NONE,MODES.NONE, MODES.ABSOLUTEXINDEXED, MODES.ABSOLUTEXINDEXED,MODES.NONE
	
	};*/
	
	public static int  findLine(BufferedReader in) throws IOException {
		while (true) {
			String line = in.readLine();
			if (line == null)
				return -1;
			if (line.indexOf("------------------") != -1)
				return 0;
		}
	}
	
	public static int getAddMode (String line) {
		String strippedLine = line.substring(5, 19).trim();
		
		if (strippedLine.equals("accumulator"))
		  return 0;
		else if (strippedLine.equals("absolute"))
		  return 1;
		else if (strippedLine.equals("absolute,X"))
		  return 2;
		else if (strippedLine.equals("absolute,Y"))
		  return 3;
		else if (strippedLine.equals("immidiate"))
		  return 4;
		else if (strippedLine.equals("implied"))
		  return 5;
		else if (strippedLine.equals("indirect"))
		  return 6;
		else if (strippedLine.equals("(indirect,X)"))
		  return 7;
		else if (strippedLine.equals("(indirect),Y"))
		  return 8;
		else if (strippedLine.equals("relative"))
		  return 9;
		else if (strippedLine.equals("zeropage"))
		  return 10;
		else if (strippedLine.equals("zeropage,X"))
		  return 11;
		else if (strippedLine.equals("zeropage,Y"))
		  return 12;
		
		return -1;

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] addModes = new int[256];
		for (int i = 0; i < 256; i++) {
			addModes[i] = 0;
		}
		int[] instLen = new int[256];
		for (int i = 0; i < 256; i++) {
			instLen[i] = 0;
		}
		int[] instCycles = new int[256];
		for (int i = 0; i < 256; i++) {
			instCycles[i] = 0;
		}

		BufferedReader in
		   = new BufferedReader(new FileReader("/home/johan/instr.txt"));
		 
		
		while (true) {
			if (findLine(in) == -1)
				break;
			/*
     accumulator   ASL A         0A    1     2
			 * */
			while (true) {
				String currentLine = in.readLine();
				if (currentLine == null)
					break;
				if (currentLine.equals(""))
					break;
				//System.out.println(currentLine.substring(5, 19));
				
				int insIndex = Integer.parseInt(currentLine.substring(33, 35),16);
				addModes[insIndex] = getAddMode(currentLine);
				instLen[insIndex] = Integer.parseInt(currentLine.substring(39, 40),16);
				instCycles[insIndex] = Integer.parseInt(currentLine.substring(45, 46),16);
			}
		}
		
		System.out.println("Printing array Address Modes");
		int currentIndex = 0;
		for (int row = 0; row < 16; row++) {
			for (int col = 0; col < 16; col++) {
				System.out.print(addModes[currentIndex] + ", ");
				currentIndex++;
			}
			System.out.println();
		}

		System.out.println("Printing array with Byte Lengths");
		currentIndex = 0;
		for (int row = 0; row < 16; row++) {
			for (int col = 0; col < 16; col++) {
				System.out.print(instLen[currentIndex] + ", ");
				currentIndex++;
			}
			System.out.println();
		}
		
		System.out.println("Printing array with Instruction Cycles");
		currentIndex = 0;
		for (int row = 0; row < 16; row++) {
			for (int col = 0; col < 16; col++) {
				System.out.print(instCycles[currentIndex] + ", ");
				currentIndex++;
			}
			System.out.println();
		}
      byte ff = (byte) 0xff;
      int x = 100 + ff;
      System.out.println(x);
	}

}
