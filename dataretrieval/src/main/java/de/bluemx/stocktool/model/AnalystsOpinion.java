package de.bluemx.stocktool.model;

/**
 * Created by teclis on 21.12.16.
 */

public enum AnalystsOpinion {
    BUY(1),
    INCREASE(2),
    HOLD(3),
    DECREASE(4),
    SELL(5);

    private final int value;

    AnalystsOpinion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}