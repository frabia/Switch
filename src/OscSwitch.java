package oscSwitch;

import cc.arduino.*;
import oscP5.*;
import processing.core.PApplet;

public class OscSwitch {

    private static final int NUM_ANALOG_PINS = 6;
    private static final int NUM_DIGITAL_PINS = 14;
    // SERIAL_RATE=9600 implies that you load the FB_StandarFirmata firmaware inside the arduino board
    private static final int SERIAL_RATE=9600;
    public Pin[] pins = new Pin[NUM_ANALOG_PINS + NUM_DIGITAL_PINS];
    public int pinIndx[] = new int[NUM_ANALOG_PINS + NUM_DIGITAL_PINS];
    //public int activeInPins[]
    //public int activeOutPins[]
    //readPins()

    public int msgGetVal;
    public OscP5 oscP5;
    public Arduino arduino;
    public PApplet applet;
    public int inIVal;
    public OscMessage oscMessage;
    public OscProperties properties;

    String addrPattern;

    public OscSwitch(PApplet applet, String address, int oscOutPort, int oscInPort, String addrPat) {

        this.applet = applet;
        arduino = new Arduino(this.applet, Arduino.list()[0], SERIAL_RATE);
        properties = new OscProperties();
        properties.setRemoteAddress(address,oscOutPort);
        properties.setListeningPort(oscInPort);
        properties.setDatagramSize(1024);
        oscP5 = new OscP5(this, properties.listeningPort());

        addrPattern=addrPat;

        OscMessage initMsg = new OscMessage(addrPat);
        for (int n=0; n < pins.length; ++n) {
            pinIndx[n] = 0;
        }
        initMsg.add(pinIndx);
        oscMessage=initMsg;
        System.out.print("### Osc init message.");
        System.out.println(" addrpattern: "+oscMessage.addrPattern());
        //System.out.print(" typetag: "+oscMessage.typetag());
        //remoteLocation = new NetAddress(address, oscOutPort);
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

        if (pinTag.equals(anPin))
            if (pinNum >= NUM_ANALOG_PINS)
                System.out.println("OUT pin out of ANALOG bound");
            else if (pins[pinNum].hasChanged())
                    oscThisOut(pinNum);

        if (pinTag.equals(digPin))
            if (pinNum >= NUM_DIGITAL_PINS)
                System.out.println("OUT pin out of DIGITAL bound");
            else if (pins[pinNum + NUM_ANALOG_PINS].hasChanged())
                    oscThisOut(pinNum + NUM_ANALOG_PINS);

    }

    private void oscThisOut(int k) {
        OscMessage msg = new OscMessage("/" + pins[k].tag);
        msg.add(pins[k].value());
        oscP5.send(msg, properties.remoteAddress());
    }


    public void oscEvent(OscMessage theOscMessage) {

        int msgGetVal1= msgGetVal-1;
        if (msgGetVal1 < 0) {
            msgGetVal1 = 0;
        }

        String oscLength = new String(theOscMessage.typetag());
        int intOscLength = oscLength.length();
        if (msgGetVal1 < intOscLength) {
            System.out.println("Array length: "+intOscLength+", msgGetVal: "+msgGetVal+", msgGetVal-1: "+(msgGetVal1));

            oscMessage = theOscMessage;
            if (theOscMessage.checkAddrPattern(addrPattern) == true) {
                    inIVal = theOscMessage.get(msgGetVal1).intValue();
                    System.out.println("Array position: " + (msgGetVal1) + ", Val: " + inIVal);
            }
        }else {
            System.out.println("GetOscMesg out of bound");
        }
    }

    public void oscInPin (String pinTag, int pinNum, int oscMsgGetVal){
        msgGetVal=oscMsgGetVal;
        String anPin = "A";
        String digPin = "D";

        if (pinTag.equals(anPin))
            if (pinNum >= NUM_ANALOG_PINS)
                System.out.println("IN pin out of ANALOG bound");
            else {oscEvent(oscMessage);
                pins[pinNum].writePin(inIVal);}

        if(pinTag.equals(digPin))
            if (pinNum >= NUM_DIGITAL_PINS)
                System.out.println("IN pin out of DIGITAL bound");
        else {oscEvent(oscMessage);
            pins[pinNum + NUM_ANALOG_PINS].writePin(inIVal);}

    }

}