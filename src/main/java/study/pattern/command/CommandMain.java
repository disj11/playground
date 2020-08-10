package study.pattern.command;

public class CommandMain {
    public static void main(String[] args) {
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);
        LightOffCommand lightOff = new LightOffCommand(light);

        RemoteControl remote = new RemoteControl();
        remote.setCommand(lightOn, lightOff);

        remote.on();
        remote.off();
    }
}
