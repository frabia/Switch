import org.firmata.Firmata;

public class Pin{
    
    public int lastValue;
    public String tag;
    public Firmata firm;
    public int pin;
    
    public class FirmataWriter implements Firmata.Writer {
    public void write(int val) {
      //not implemented yet
    }
  }
    public  Pin(String type, int num){
        this.firm = new Firmata(new FirmataWriter());
        this.tag = type+String.valueOf(num);
        this.pin = num;
    }
    
    public boolean hasChanged(int v){
        return (v != lastValue);
    }
    
    public int read(){
        return 0; //does nothing succesfully
    }
    
    public int value(){
        int res;
        int v = read();
        res = hasChanged(v) ? (lastValue = v):lastValue;
        return res;
    }
}