package hu.dayroom.controller;

import hu.dayroom.model.domain.Direction;
import hu.dayroom.model.domain.LogEntry;
import hu.dayroom.model.domain.LogTime;
import hu.dayroom.model.domain.Pair;

import java.util.*;
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
                .filter(i -> i.getDirection() == Direction.OUT)
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
                .filter(i -> isOdd(i.getValue()))
                .map(Map.Entry::getKey)
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    private Map<Integer, Long> createMap() {
        return logEntries.stream()
                .collect(Collectors.groupingBy(LogEntry::getId, TreeMap::new, Collectors.counting()));
    }

    public String getMostCrowdedTime() {
        return createPairs().stream()
                .max(Comparator.comparing(Pair::getRight))
                .map(Pair::getLeft)
                .map(LogTime::toString)
                .get();
    }

    private List<Pair> createPairs() {
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
        return pairs;
    }

    public String getFormattedLogTimesById(int id) {
        StringBuilder sb = new StringBuilder();
        var logTimesById = getLogTimesById(id);
        int size = isOdd(logTimesById.size()) ? logTimesById.size() - 1 : logTimesById.size();
        for (int i = 0; i < size; i += 2) {
            sb.append(logTimesById.get(i)).append("-").append(logTimesById.get(i + 1)).append("\n");
        }
        if (isOdd(logTimesById.size())) {
            sb.append(logTimesById.get(size)).append("-\n");
        }
        return sb.toString();
    }

    public String getTotalDuration(int id) {
        var logTimesById = getLogTimesById(id);
        var logTimes = new ArrayList<>(logTimesById);
        if (isOdd(logTimesById.size())) {
            logTimes.add(new LogTime(15, 0));
        }
        int minute = 0;
        for (int i = 0; i < logTimes.size(); i += 2) {
            minute += logTimes.get(i).getDuration(logTimes.get(i + 1));
        }
        String status = isOdd(logTimesById.size()) ? "a társalgóban volt" : "nem volt a társalgóban";
        return String.format("A(z) %d. személy összesen %d percet volt bent, a megfigyelés végén %s.", id, minute, status);
    }

    private boolean isOdd(int value) {
        return value % 2 == 1;
    }

    private boolean isOdd(long value) {
        return value % 2 == 1;
    }

    private List<LogTime> getLogTimesById(int id) {
        return logEntries.stream()
                .filter(i -> i.getId() == id)
                .map(LogEntry::getLogTime)
                .collect(Collectors.toList());
    }
}
