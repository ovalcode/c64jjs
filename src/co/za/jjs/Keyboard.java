package co.za.jjs;

public class Keyboard implements ReadHook {

	private Machine machine;
	private ReadHook readPoint;
	private int[] matrix = new int[8];
	@Override
	public int readb() {
		clearKeyboardMatrix();
		int[] keys = machine.getKeys();
		for (int i = 0; i < keys.length; i++) {
			int scanCode = getScanCodeForKey(keys[i]);
			matrix[scanCode / 8] = matrix[scanCode / 8] | (1 << ( (scanCode % 8)));
		}
		int scanCode = readKeyboard(readPoint.reada() & 0xff);
		if (machine.getInputMode() == 2)
			scanCode = scanCode & (machine.getJoyBits());
		return scanCode;
	}
	
	public Keyboard (Machine machine) {
		this.machine = machine;
	}

	public void setReadPoint(ReadHook readPoint) {
		this.readPoint = readPoint;
	}
	
	public byte readKeyboard(int col) {
		int result = 0;
		for (int i = 0; i < 8; i++) {
			//if current col selected
			if (((col >> i) & 1) == 0) {
              result = result | matrix[i];
					
			}
		}
		result = ~result;
		result = result & 0xff;
		return (byte) result;
	}
	
	public void clearKeyboardMatrix() {
		for (int i = 0; i < 8; i++) {
			matrix[i] = 0;
		}
	}
	
	public int getScanCodeForKey(int key) {
		int temp = 0;
		switch (key) {
		case 'A': temp = 0xa; 
			      break;
		case 'B': temp = 0x1c;
		          break;
		case 'C': temp = 0x14;
		          break;
		case 'D': temp = 0x12;
        break;
		case 'E': temp = 0xe;
        break;
		case 'F': temp = 0x15;
        break;
		case 'G': temp = 0x1a;
        break;
		case 'H': temp = 0x1d;
        break;
		case 'I': temp = 0x21;
        break;
		case 'J': temp = 0x22;
        break;
		case 'K': temp = 0x25;
        break;
		case 'L': temp = 0x2a;
        break;
		case 'M': temp = 0x24;
        break;
		case 'N': temp = 0x27;
        break;
		case 'O': temp = 0x26;
        break;
		case 'P': temp = 0x29;
        break;
		case 'Q': temp = 0x3e;
        break;
		case 'R': temp = 0x11;
        break;
		case 'S': temp = 0xd;
        break;
		case 'T': temp = 0x16;
        break;
		case 'U': temp = 0x1e;
        break;
		case 'V': temp = 0x1f;
        break;
		case 'W': temp = 0x9;
        break;
		case 'X': temp = 0x17;
        break;
		case 'Y': temp = 0x19;
        break;
		case 'Z': temp = 0xc;
        break;
		case '0': temp = 0x23;
        break;
		case '1': temp = 0x38;
        break;
		case '2': temp = 0x3b;
        break;
		case '3': temp = 0x8;
        break;
		case '4': temp = 0xb;
        break;
		case '5': temp = 0x10;
        break;
		case '6': temp = 0x13;
        break;
		case '7': temp = 0x18;
        break;
		case '8': temp = 0x1b;
        break;
		case '9': temp = 0x20;
        break;
		case ' ': temp = 0x3c;
		break;
		case '=': temp = 0x35;
		break;
		
        //shift key
		case 16: temp = 0xf;
        break;
        //return
		case 0xa: temp = 1;
        break;
        
        
		 default: break;
		}
		return temp;
	}

	@Override
	public int reada() {
		// TODO Auto-generated method stub
		int a = 0xff;
		if (machine.getInputMode() == 1)
			a = a & machine.getJoyBits();
		return a;
	}

}
