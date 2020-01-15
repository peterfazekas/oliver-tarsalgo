package hu.dayroom.model.domain;

public class LogTime {

    private final int hour;
    private final int minute;

    public LogTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getDuration(LogTime otherTime) {
        return Math.abs(toMinute() - otherTime.toMinute());
    }

    public Integer toMinute() {
        return hour * 60 + minute;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
