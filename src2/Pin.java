package oscSwitch;


import org.firmata.Firmata;
import cc.arduino.*;
import processing.core.PApplet;


public abstract class Pin{
    
    public int lastValue;
    public String tag;
    public Firmata firm;
    public Arduino arduino;
    public int pin;

    public class FirmataWriter implements Firmata.Writer {
        public void write(int val) {
            //not implemented yet
        }
    }

    public  Pin(Arduino arduino,String type, int num){
        //this.firm = new Firmata(new FirmataWriter());
        this.arduino= arduino;
        this.tag = type+String.valueOf(num);
        this.pin = num;
    }
    
    public boolean hasChanged(){
        int v = read();
        boolean result = v != lastValue;
        lastValue = v;
        return (result);
    }
    
    public abstract int read();
    
    public int value(){
        return lastValue;
    }
    
}