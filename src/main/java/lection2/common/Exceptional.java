package lection2.common;

public class Exceptional implements Thread.UncaughtExceptionHandler {
    private Exceptional() {
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
