package hu.dayroom.model.domain;

public class Pair {

    private final LogTime left;
    private final int right;

    public Pair(LogTime left, int right) {
        this.left = left;
        this.right = right;
    }

    public LogTime getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
