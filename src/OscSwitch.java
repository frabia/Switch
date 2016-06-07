package oscSwitch;

import cc.arduino.*;
import junit.framework.TestListener;
import netP5.*;
import oscP5.*;
import processing.core.PApplet;

public class OscSwitch {

    private static final int NUM_ANALOG_PINS = 6;
    private static final int NUM_DIGITAL_PINS = 14;
    // SERIAL_RATE=9600 implies that you load the FB_StandarFirmata firmaware inside the arduino board
    private static final int SERIAL_RATE=9600;
    public Pin[] pins = new Pin[NUM_ANALOG_PINS + NUM_DIGITAL_PINS];
    public NetAddress remoteLocation;
    public OscP5 oscP5;
    public Arduino arduino;
    public PApplet applet;
    public int inIVal;
    public OscMessage theOscMessage;

    public OscSwitch(PApplet applet, String address, int oscOutPort, int oscInPort, String addrPattern) {

        this.applet = applet;
        arduino = new Arduino(this.applet, Arduino.list()[0], SERIAL_RATE);
        oscP5 = new OscP5(this, oscInPort);
        remoteLocation = new NetAddress(address, oscOutPort);
        //oscP5.plug(this, "oscThisIn", addrPattern);

        int k;
        for (k = 0; k < NUM_ANALOG_PINS; ++k)
            pins[k] = new AnalogPin(arduino,k, k);


        for (; k < pins.length; ++k)
            pins[k] = new DigitalPin(arduino,k-NUM_ANALOG_PINS, k-NUM_ANALOG_PINS);
    }
    
    
    
    public Pin getPin(int index){
        return pins[index];
    }


    public void oscOut() {
        for (int k = 0; k < pins.length; ++k) {
            if (pins[k].hasChanged()) {
                oscThisOut(k);
            }
        }
    }

    public void oscOutPin(String pinTag, int pinNum) {
        String anPin = "A";
        String digPin = "D";
        if (pinTag.equals(anPin)) {
            if (pins[pinNum].hasChanged()) {
                oscThisOut(pinNum);
            }
        }
        if(pinTag.equals(digPin)){
                if (pins[pinNum + NUM_ANALOG_PINS].hasChanged()) {
                    oscThisOut(pinNum +NUM_ANALOG_PINS);
                }
            }
    }


    private void oscThisOut(int k) {
        OscMessage msg = new OscMessage("/" + pins[k].tag);
        msg.add(pins[k].value());
        oscP5.send(msg, remoteLocation);
    }
/*
    public void oscThisIn(int iVal){
        inIVal = iVal;
    }
*/

    public void oscEvent(OscMessage theOscMessage){
        this.theOscMessage=theOscMessage;
        System.out.print("### received an osc message.");
        System.out.print(" message: "+theOscMessage);
        System.out.print(" addrpattern: "+theOscMessage.addrPattern());
        System.out.println(" typetag: "+theOscMessage.typetag());
        inIVal = theOscMessage.get(0).intValue();
    }

    public void oscInPin (String pinTag, int pinNum){
        String anPin = "A";
        String digPin = "D";
        if (pinTag.equals(anPin)) {
            oscEvent(theOscMessage);
            pins[pinNum].writePin(inIVal);
        }
        if(pinTag.equals(digPin)){
            oscEvent(theOscMessage);
            pins[pinNum + NUM_ANALOG_PINS].writePin(inIVal);
        }
    }

}