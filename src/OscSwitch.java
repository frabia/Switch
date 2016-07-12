package oscSwitch;

import cc.arduino.*;
import oscP5.*;
import processing.core.PApplet;

import java.util.ArrayList;

public class OscSwitch {

    public static final int NUM_ANALOG_PINS = 6;
    public static final int NUM_DIGITAL_PINS = 14;
    // SERIAL_RATE=9600 implies that you load the FB_StandarFirmata firmaware inside the arduino board
    private static final int SERIAL_RATE=9600;

    public int msgGetVal;
    public OscP5 oscP5;
    public Arduino arduino;
    public PApplet applet;
    public ArrayList<Pin> activeInDigPins = new ArrayList<Pin>();
    public ArrayList<Pin> activeOutDigPins = new ArrayList<Pin>();
    public ArrayList<Pin> activeInAnPins = new ArrayList<Pin>();
    public ArrayList<Pin> activeOutAnPins = new ArrayList<Pin>();
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
    }

    public void setActiveInPins(int nPin) {
        if (nPin >= NUM_ANALOG_PINS){
          this.activeInDigPins.add(new DigitalPin(arduino, nPin-NUM_ANALOG_PINS));
        }
        else {
            this.activeInAnPins.add(new AnalogPin(arduino, nPin));
        }
    }

    public void setActiveOutPins(int nPin) {
        if (nPin >= NUM_ANALOG_PINS){
            this.activeOutDigPins.add(new DigitalPin(arduino, nPin-NUM_ANALOG_PINS));
        }
        else {
            this.activeOutAnPins.add(new AnalogPin(arduino, nPin));
        }
    }

    public void removeInPin(int nPin){
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeInDigPins.size(); i++) {
                Pin pin = this.activeInDigPins.get(i);
                if(nPin-NUM_ANALOG_PINS==pin.num) {
                    this.activeInDigPins.remove(i);
                }
            }
        }else{
            for (int i = 0; i < this.activeInAnPins.size(); i++) {
                Pin pin = this.activeInAnPins.get(i);
                if(nPin==pin.num) {
                    this.activeInAnPins.remove(i);
                }
            }
        }
    }

    public void removeOutPin(int nPin){
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeOutDigPins.size(); i++) {
                Pin pin = this.activeOutDigPins.get(i);
                if(nPin-NUM_ANALOG_PINS==pin.num) {
                    this.activeOutDigPins.remove(i);
                }
            }
        }else{
            for (int i = 0; i < this.activeOutAnPins.size(); i++) {
                Pin pin = this.activeOutAnPins.get(i);
                if(nPin==pin.num) {
                    this.activeOutAnPins.remove(i);
                }
            }
        }
    }


    public void readFromArd(int nPin) {
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeOutDigPins.size(); i++) {
                Pin pin = this.activeOutDigPins.get(i);
                if (pin.hasChanged(nPin)){
                    oscThisOutDig();
                }
            }
        }else{
            for (int i = 0; i < this.activeOutAnPins.size(); i++) {
                Pin pin = this.activeOutAnPins.get(i);
                if (pin.hasChanged(nPin)){
                    oscThisOutAn();
                }
            }
        }
    }

    private void oscThisOutAn() {
        for (int i = 0; i < this.activeOutAnPins.size(); i++) {
            Pin pin = this.activeOutAnPins.get(i);
            OscMessage msg = new OscMessage("/" + pin.tag);
            msg.add(pin.value());
            oscP5.send(msg, properties.remoteAddress());
        }
    }
    private void oscThisOutDig() {
        for (int i = 0; i < this.activeOutDigPins.size(); i++) {
            Pin pin = this.activeOutDigPins.get(i);
            OscMessage msg = new OscMessage("/" + pin.tag);
            msg.add(pin.value());
            oscP5.send(msg, properties.remoteAddress());
        }
    }

    public void writeToArd(int nPin, int oscMsgGetVal){
        msgGetVal=oscMsgGetVal;
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeInDigPins.size(); i++) {
                Pin pin = this.activeInDigPins.get(i);
                pin.writePin(nPin-NUM_ANALOG_PINS, inIVal);
            }
        }else{
            for (int i = 0; i < this.activeInAnPins.size(); i++) {
                Pin pin = this.activeInAnPins.get(i);
                pin.writePin(nPin, inIVal);
            }
        }
    }

    public void oscEvent(OscMessage theOscMessage) {
        oscMessage = theOscMessage;
        if (theOscMessage.checkAddrPattern(addrPattern) == true) {
            inIVal = theOscMessage.get(msgGetVal).intValue();
        }
    }

    public boolean isInActive(int nPin) {
        boolean n = false;
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeInDigPins.size(); i++) {
                Pin pin = this.activeInDigPins.get(i);
                if(pin.num == nPin-NUM_ANALOG_PINS) {
                    n = true;
                }
            }
        }else {
            for (int i = 0; i < this.activeInAnPins.size(); i++) {
                Pin pin = this.activeInAnPins.get(i);
                if (pin.num == nPin) {
                    n = true;
                }
            }
        }
        return n;
    }
    public boolean isOutActive(int nPin) {
        boolean n = false;
        if (nPin >= NUM_ANALOG_PINS){
            for (int i = 0; i < this.activeOutDigPins.size(); i++) {
                Pin pin = this.activeOutDigPins.get(i);
                if(pin.num == nPin-NUM_ANALOG_PINS) {
                    n = true;
                }
            }
        }else{
            for (int i = 0; i < this.activeOutAnPins.size(); i++) {
                Pin pin = this.activeOutAnPins.get(i);
                if(pin.num == nPin) {
                    n = true;
                }
            }
        }
        return n;
    }

}