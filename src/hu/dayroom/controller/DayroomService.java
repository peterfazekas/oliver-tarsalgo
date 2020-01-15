package hu.dayroom.controller;

import hu.dayroom.model.domain.Direction;
import hu.dayroom.model.domain.LogEntry;
import hu.dayroom.model.domain.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DayroomService {

    private final List<LogEntry> logEntries;

    public DayroomService(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public int getFirstInId() {
        return logEntries.get(0).getId();
    }

    public int getLastOutId() {
        return logEntries.stream()
                .sorted((j, i) -> i.getLogTime().toMinute().compareTo(j.getLogTime().toMinute()))
                .findFirst()
                .map(LogEntry::getId)
                .get();
    }

    public List<String> getIdMap() {
        return createMap().entrySet().stream()
                .map(i -> i.getKey() + " " + i.getValue())
                .collect(Collectors.toList());
    }

    public String getStayedIds() {
        return createMap().entrySet().stream()
                .filter(i -> i.getValue() % 2 == 1)
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    private Map<Integer, Long> createMap() {
        return logEntries.stream()
                .collect(Collectors.groupingBy(LogEntry::getId, TreeMap::new, Collectors.counting()));
    }

    public String getMostCrowdedTime() {
        int counter = 0;
        List<Pair> pairs = new ArrayList<>();
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getDirection() == Direction.IN) {
                counter++;
            } else {
                counter--;
            }
            pairs.add(new Pair(logEntry.getLogTime(), counter));
        }
        return pairs.size() + "";
    }
}
