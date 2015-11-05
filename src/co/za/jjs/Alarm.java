package co.za.jjs;

public interface Alarm {
  public boolean processAlarm();
  public long getExpiry();
  public void alarmCallback();
}
