package de.bluemx.stocktool.model;

import java.time.LocalDate;
import java.time.Year;

/**
 * A pimped Year, because some figures are estimated and needed to be saved anywhere.
 */
public class YearEstimated implements Comparable<YearEstimated> {
    private Year yearEstimated;
    private boolean estimated;

    public YearEstimated(Year year, boolean estimated) {
        this.yearEstimated = year;
        this.estimated = estimated;
    }

    public Year getYear() {
        return yearEstimated;
    }

    public void setYear(Year year) {
        this.yearEstimated = yearEstimated;
    }

    /**
     * @return the local date with month and day set to 1
     */
    public LocalDate getLocalDate() {
        return LocalDate.of(yearEstimated.getValue(), 1, 1);
    }

    public boolean isEstimated() {
        return estimated;
    }

    public void setEstimated(boolean estimated) {
        this.estimated = estimated;
    }

    @Override
    public int compareTo(YearEstimated o) {
        if (o == null || o.getYear() == null) return -1;
        return this.getYear().compareTo(o.getYear());
    }

}
