package oscSwitch;

import cc.arduino.*;

public abstract class Pin{
    
    public int lastValue;
    public String tag;
    public Arduino arduino;
    public int num;
    public String type;


    public  Pin(Arduino arduino, String type, int nPin){
        this.arduino= arduino;
        this.type = type;
        this.tag = type+String.valueOf(nPin);
        this.num = nPin;
    }

    public boolean hasChanged(int nPin){
        int v = read(nPin);
        boolean result = v != lastValue;
        lastValue = v;
        return (result);
    }

    public abstract int read(int nPin);
    
    public int value(){
        return lastValue;
    }

    public int writePin(int nPin, int val){
        write(nPin, val);
        return val;

    }

    public abstract int write(int nPin, int val);


}