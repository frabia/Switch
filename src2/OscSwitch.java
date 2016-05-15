package oscSwitch;

import cc.arduino.Arduino;
import netP5.*;
import oscP5.*;
import processing.core.PApplet;
import processing.serial.*;


import java.util.ArrayList;
import java.util.List;

public class OscSwitch {

    private static final int NUM_ANALOG_PINS = 5;
    private static final int NUM_DIGITAL_PINS = 13;
    public Pin[] pins = new Pin[NUM_ANALOG_PINS + NUM_DIGITAL_PINS];
    public NetAddress remoteLocation;
    public OscP5 oscP5;
    public Arduino arduino;
    public PApplet applet;



    public OscSwitch(String address, int port, String serialPort, int irate) {

        arduino = new Arduino(this.applet, serialPort, irate);

        oscP5 = new OscP5(this, port);

        remoteLocation = new NetAddress(address, port);

        int k;
        for (k = 0; k <= NUM_ANALOG_PINS; ++k)
            pins[k] = new AnalogPin(k);

        for (k = 0; k <= NUM_DIGITAL_PINS; ++k)
            pins[k] = new DigitalPin(k);

    }

    public Pin getPin(int index){
        return pins[index];
    }

    public void oscOut() {
        for (int k = 0; k <= NUM_ANALOG_PINS + NUM_DIGITAL_PINS; ++k) {
            if (pins[k].hasChanged()) {
                oscThisOut(k);
            }
        }
    }

    private void oscThisOut(int k) {
        OscMessage msg = new OscMessage("/" + pins[k].tag);
        msg.add(pins[k].value());
        oscP5.send(msg, remoteLocation);

    }
}
