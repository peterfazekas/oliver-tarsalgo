package hu.dayroom.model.service;

import hu.dayroom.model.domain.Direction;
import hu.dayroom.model.domain.LogEntry;
import hu.dayroom.model.domain.LogTime;

import java.util.List;
import java.util.stream.Collectors;

public class DataParser {

    public List<LogEntry> parse(List<String> lines) {
        return lines.stream()
                .map(this::createLogEntry)
                .collect(Collectors.toList());
    }

    private LogEntry createLogEntry(String line) {
        String[] items = line.split(" ");
        LogTime logTime = new LogTime(getValue(items[0]), getValue(items[1]));
        int id = getValue(items[2]);
        Direction direction = Direction.getValue(items[3]);
        return new LogEntry(logTime, id, direction);
    }

    private int getValue(String data) {
        return Integer.parseInt(data);
    }
}
