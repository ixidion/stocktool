package de.bluemx.stocktool.model;

import java.time.Year;

/**
 * A pimped Year, because some figures are estimated and needed to be saved anywhere.
 */
public class YearEstimated {
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

    public boolean isEstimated() {
        return estimated;
    }

    public void setEstimated(boolean estimated) {
        this.estimated = estimated;
    }
}
