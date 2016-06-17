package oscSwitch;

import cc.arduino.*;

public abstract class Pin{
    
    public int lastValue;
    public String tag;
    public Arduino arduino;
    public int readPin;
    public int writePin;


    public  Pin(Arduino arduino,String type, int rNum, int wNum){
        this.arduino= arduino;
        this.tag = type+String.valueOf(rNum);
        this.readPin = rNum;
        this.writePin = wNum;
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

    public int writePin(int val){
        write(val);
        return val;

    }

    public abstract int write(int val);
    
}