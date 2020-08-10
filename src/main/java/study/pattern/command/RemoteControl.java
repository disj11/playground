package study.pattern.command;

public class RemoteControl {
    private Command onCommand;
    private Command offCommand;

    public void setCommand(Command onCommand, Command offCommand) {
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }

    public void on() {
        onCommand.execute();
    }

    public void off() {
        offCommand.execute();
    }
}
