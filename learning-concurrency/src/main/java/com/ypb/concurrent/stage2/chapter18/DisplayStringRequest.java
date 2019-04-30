package com.ypb.concurrent.stage2.chapter18;

public class DisplayStringRequest extends MethodRequest {

    private final String text;

    public DisplayStringRequest(Servant servant, String text) {
        super(servant);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(text);
    }
}
