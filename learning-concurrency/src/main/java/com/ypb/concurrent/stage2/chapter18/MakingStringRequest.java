package com.ypb.concurrent.stage2.chapter18;

public class MakingStringRequest extends MethodRequest {

    private final int count;
    private final char fillChar;

    public MakingStringRequest(Servant servant, FutureResult result, int count, char fillChar) {
        super(servant, result);
        this.count = count;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result<String> result = servant.makeString(count, fillChar);

        futureResult.setResut(result);
    }
}
