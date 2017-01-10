package de.bluemx.stocktool.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "eps")
public class Eps implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="eps_id")
    private int epsId;
    @Column(name = "eps_year")
    private LocalDate epsYear;
    @Column(name = "eps_value")
    private BigDecimal epsValue;
    @Column(name="estimated")
    private boolean estimated;
    @ManyToOne
    @JoinColumn(name = "stockquotedata_id")
    private StockquoteDetail stockquoteDetail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eps eps = (Eps) o;

        return epsId == eps.epsId;
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

    public LocalDate getEpsYear() {
        return epsYear;
    }

    public void setEpsYear(LocalDate epsYear) {
        this.epsYear = epsYear;
    }

    public BigDecimal getEpsValue() {
        return epsValue;
    }

    public void setEpsValue(BigDecimal epsValue) {
        this.epsValue = epsValue;
    }

    public boolean isEstimated() {
        return estimated;
    }

    public void setEstimated(boolean estimated) {
        this.estimated = estimated;
    }

    public StockquoteDetail getStockquoteDetail() {
        return stockquoteDetail;
    }

    public void setStockquoteDetail(StockquoteDetail stockquoteDetail) {
        this.stockquoteDetail = stockquoteDetail;
    }
}
