package ch1;

public class BritzSpeaker implements Speaker {

    public BritzSpeaker() {
        System.out.println("BritzSpeaker 생성");
    }

    @Override
    public void volumeUp() {
        System.out.println("BritzSpeaker volume up");
    }

    @Override
    public void voluemDown() {
        System.out.println("BritzSpeaker volume down");
    }

}
