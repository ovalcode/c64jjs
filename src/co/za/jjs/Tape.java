package co.za.jjs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tape implements Alarm{
	FileInputStream fis = null;
	Interrupt interrupt;
	private byte[] buffer = new byte[4096];
	private boolean processAlarm = false;
	private boolean level = false;
	private long expiry = 0;
	private long remainingPulseTime = 0;
	private int posInBuffer = 5000;
	private int pulseNumber = 0;
	private Machine machine;
	public Tape(Machine machine, Interrupt interrupt) {
		this.machine = machine;
		this.interrupt = interrupt;
		try {
			fis = new FileInputStream(new File("/home/johan/Dan Dare.tap"));
			//fis = new FileInputStream(new File("/home/johan/Introduction to Basic - Part 1 _ Tape 1_5016.tap"));
			fis.skip(0x14);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public byte getByteInBuffer() {
		if (posInBuffer > 4095) {
			posInBuffer = 0;
			try {
				fis.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		byte temp = buffer[posInBuffer];
		posInBuffer++;
		return temp;
	}
	
	public int getDelayValue() {
		int value1 = getByteInBuffer() & 0xff;
		int temp = 0;
		if (value1 == 0) {
			value1 = getByteInBuffer() & 0xff;
			int value2 = getByteInBuffer() & 0xff;
			int value3 = getByteInBuffer() & 0xff;
			temp = value3 * 256 * 256 + value2 * 256 + value1;
		} else {
			temp = value1 * 8;
		}	

		pulseNumber++;
		//if (pulseNumber > 0x69fc)
		//	System.out.println("Pulse Number: " + pulseNumber);
		//System.out.println("Delay value: "+temp + " pulse num: " + pulseNumber);
		return temp;
	}
	
	public void start() {
		//System.out.println("Tape started");
		//fis.read(buffer);
		//if started already then exit;
		if (processAlarm)
		  return;
		machine.addAlarm(this);
		if (remainingPulseTime > 0) {
			expiry = remainingPulseTime + machine.getCycles();
			remainingPulseTime = 0;
		} else {
		  expiry = getDelayValue() + machine.getCycles();
		}
		processAlarm = true;
		//fis.skip(0x14);
	}
	
	public void stop() {
		//System.out.println("Tape stopped");
		processAlarm = false;
		remainingPulseTime = expiry - machine.getCycles();
		if (remainingPulseTime < 0)
			remainingPulseTime = 0;
	}

	@Override
	public long getExpiry() {
		// TODO Auto-generated method stub
		return expiry;
	}

	@Override
	public void alarmCallback() {
		//set !flag
		expiry = getDelayValue() + machine.getCycles();
		processAlarm = true;
		level = !level;
		interrupt.setInterrupt("FL");
	}

	@Override
	public boolean processAlarm() {
		// TODO Auto-generated method stub
		return processAlarm;
	}

}
