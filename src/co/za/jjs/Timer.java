package co.za.jjs;

public class Timer implements Alarm{
  private int timerLoadedValue = 0;
  private int timerLatchedValue = 0xffff;
  private boolean started = false;
  private Machine machine;
  private Interrupt interrupt;
  private boolean continious = false;
  private String timerID;
  private long expiry = 0;
@Override
public boolean processAlarm() {
	// TODO Auto-generated method stub
	return started;
}

public boolean getContinious() {
	return continious;
}

public void setContinious(boolean bit) {
	this.continious = bit;
}

public boolean getStartedBit() {
	return this.started;
}

private void startTimer() {
	this.started = true;
	machine.addAlarm(this);
	expiry = machine.getCycles() + timerLoadedValue;	
}

private void stopTimer() {
	this.started = false;
	timerLoadedValue = (int)(expiry - machine.getCycles());
	if (timerLoadedValue < 0)
		timerLoadedValue = timerLatchedValue;
}

public void ForceReload() {
	timerLoadedValue = timerLatchedValue;
	if( started)
	  startTimer();
}

public void setStartTimerBit(boolean bit) {
	if (!this.started & bit) {
		startTimer();
		return;
	}
	
	if (this.started & !bit) {
		stopTimer();
		return;
	}
	//start
	//stop
	
}

public Timer(Machine machine, String timerID, Interrupt interrupt) {
	this.machine = machine;
	this.timerID = timerID;
	this.interrupt = interrupt;
}

public void setTimerLow(byte low) {
	timerLatchedValue = timerLatchedValue & (255 * 256);
	timerLatchedValue = timerLatchedValue | (low & 0xff);
}

public void setTimerHigh(byte high) {
	timerLatchedValue = timerLatchedValue & (255);
	timerLatchedValue = timerLatchedValue | ((high & 0xff) *256);
}

public byte getTimerLow() {
	if (started)
		return (byte) ((expiry - machine.getCycles()) & 0xff);
	else
		return (byte)(timerLoadedValue & 0xff);
}

public byte getTimerHigh() {
	if (started)
		return (byte) (((expiry - machine.getCycles()) & 0xff00) >> 8);
	else
		return (byte)((timerLoadedValue & 0xff00) >> 8);
}

@Override
public long getExpiry() {
	// TODO Auto-generated method stub
	return expiry;
}
@Override
public void alarmCallback() {
	// underflow occured
	timerLoadedValue = timerLatchedValue;
	interrupt.setInterrupt(timerID);
	if (continious)
		startTimer();
	else
		stopTimer();
}
  
  
}
