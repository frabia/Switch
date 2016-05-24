package oscSwitch;

import cc.arduino.*;
import netP5.*;
import oscP5.*;
import processing.core.PApplet;
import org.firmata.*;

public class OscSwitch {

    private static final int NUM_ANALOG_PINS = 5;
    private static final int NUM_DIGITAL_PINS = 13;
    public Pin[] pins = new Pin[NUM_ANALOG_PINS + NUM_DIGITAL_PINS];
    public NetAddress remoteLocation;
    public OscP5 oscP5;
    public Arduino arduino;
    public Firmata firmata;
    public PApplet applet;


    public OscSwitch(PApplet applet, String address, int port, int irate) {

        this.applet=applet;
        arduino = new Arduino(this.applet, Arduino.list()[0], irate);
        oscP5 = new OscP5(this, port);
        remoteLocation = new NetAddress(address, port);

        int k;
        for (k = 0; k < NUM_ANALOG_PINS; ++k)
            pins[k] = new AnalogPin(arduino,k);
            arduino.pinMode(k, Arduino.INPUT);


        for (; k < pins.length; ++k)
            pins[k] = new DigitalPin(arduino,k-NUM_ANALOG_PINS);
            arduino.pinMode(k, Arduino.INPUT);
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

    public void oscThisOut(int k) {
        OscMessage msg = new OscMessage("/" + pins[k].tag);
        msg.add(pins[k].value());
        oscP5.send(msg, remoteLocation);

    }
}
