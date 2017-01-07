package de.bluemx.stocktool.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name="price_earnings_ratio")
public class PriceEarningsRatio implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="price_earnings_ratio_id")
    private int epsId;
    @Column(name="per_year")
    private LocalDate perYear;
    @Column(name="per_value")
    private BigDecimal perValue;
    @ManyToOne
    @JoinColumn(name = "stockquotedata_id")
    private StockquoteDetail stockquoteDetail;

    @Override
    public String toString() {
        return "PriceEarningsRatio{" +
                "epsId=" + epsId +
                ", perYear='" + perYear + '\'' +
                ", perValue=" + perValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEarningsRatio that = (PriceEarningsRatio) o;

        return epsId == that.epsId;
    }

    @Override
    public int hashCode() {
        return epsId;
    }

    public int getEpsId() {
        return epsId;
    }

    public void setEpsId(int epsId) {
        this.epsId = epsId;
    }

    public LocalDate getPerYear() {
        return perYear;
    }

    public void setPerYear(LocalDate perYear) {
        this.perYear = perYear;
    }

    public BigDecimal getPerValue() {
        return perValue;
    }

    public void setPerValue(BigDecimal perValue) {
        this.perValue = perValue;
    }

    public StockquoteDetail getStockquoteDetail() {
        return stockquoteDetail;
    }

    public void setStockquoteDetail(StockquoteDetail stockquoteDetail) {
        this.stockquoteDetail = stockquoteDetail;
    }
}
