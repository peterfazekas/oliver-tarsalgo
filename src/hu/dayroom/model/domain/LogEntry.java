package hu.dayroom.model.domain;

public class LogEntry {

    private final LogTime logTime;
    private final int id;
    private final Direction direction;

    public LogEntry(LogTime logTime, int id, Direction direction) {
        this.logTime = logTime;
        this.id = id;
        this.direction = direction;
    }

    public LogTime getLogTime() {
        return logTime;
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }
}
