import oscP5.*;
import netP5.*;

public class Switch {
    private static final int NUM_ANALOG_PINS = 5;
    private static final int NUM_DIGITAL_PINS = 13;
    public Pin [] pins;
    public NetAddress remoteLocation;
    public OscP5 oscP5;
    
    
    public Switch (String address, int port){
        
        oscP5 = new OscP5(this, port);
        remoteLocation = new NetAddress(address, port);
        int k = 0;
        for (k = 0; k < NUM_ANALOG_PINS; ++k)
            pins[k]= new AnalogPin(k);
            
        for (;k < NUM_ANALOG_PINS+NUM_DIGITAL_PINS; ++k)
            pins[k]= new DigitalPin(k);
    }
    
    public void oscOut(){
        for (int k = 0;k<NUM_ANALOG_PINS+NUM_DIGITAL_PINS; ++k){
            if (pins[k].hasChanged()){
                oscThisOut(k);
            }
        }
    }
    
    private void oscThisOut(int k){
        OscMessage msg = new OscMessage("/"+pins[k].tag);
        msg.add (pins[k].value()); 
        oscP5.send(msg,remoteLocation);
    }
    

    
}