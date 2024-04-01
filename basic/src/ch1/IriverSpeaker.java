package ch1;

public class IriverSpeaker implements Speaker {

    public IriverSpeaker() {
        System.out.println("IriverSpeaker 생성");
    }

    @Override
    public void volumeUp() {
        System.out.println("IriverSpeaker volume up");
    }

    @Override
    public void voluemDown() {
        System.out.println("IriverSpeaker volume down");
    }

}
