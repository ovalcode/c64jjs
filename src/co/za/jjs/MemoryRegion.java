package co.za.jjs;

public interface MemoryRegion {
  public int getRegionStart();
  public int getRegionEnd();
  public byte read(int address);
  public void write(int address, byte num);
  public boolean isWritable();
}
