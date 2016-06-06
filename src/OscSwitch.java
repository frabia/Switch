package oscSwitch;

import cc.arduino.*;
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
    int inIVal;


    public OscSwitch(PApplet applet, String address, int oscOutPort, int oscInPort, String addrPattern) {

        this.applet = applet;
        arduino = new Arduino(this.applet, Arduino.list()[0], SERIAL_RATE);
        oscP5 = new OscP5(this, oscInPort);
        remoteLocation = new NetAddress(address, oscOutPort);
        oscP5.plug(this, "oscThisIn", addrPattern);

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

    public void oscOutPin(int pinNum) {
            if (pins[pinNum].hasChanged()) {
                oscThisOut(pinNum);
            }
    }

    private void oscThisOut(int k) {
        OscMessage msg = new OscMessage("/" + pins[k].tag);
        msg.add(pins[k].value());
        oscP5.send(msg, remoteLocation);
    }

    public void oscThisIn(int iVal){
        inIVal = iVal;
    }

    public void oscInPin (int nPin){
        oscThisIn(inIVal);
        pins[nPin].writePin(inIVal);
        System.out.println("value: "+inIVal);
    }

}