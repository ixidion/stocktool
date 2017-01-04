package de.bluemx.stocktool.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity(name="historical_quotes")
public class HistoricalQuote implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="historical_quotes_id")
    private int epsId;
    @Column(name="quote_date")
    private Date quoteDate;
    @Column(name="quote")
    private BigDecimal quote;
    @ManyToOne
    @JoinColumn(name = "stockquotedata_id")
    private StockquoteDetail stockquoteDetail;

    @Override
    public String toString() {
        return "HistoricalQuotes{" +
                "epsId=" + epsId +
                ", quoteDate=" + quoteDate +
                ", quote=" + quote +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoricalQuote that = (HistoricalQuote) o;

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

    public Date getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(BigDecimal quote) {
        this.quote = quote;
    }

    public StockquoteDetail getStockquoteDetail() {
        return stockquoteDetail;
    }

    public void setStockquoteDetail(StockquoteDetail stockquoteDetail) {
        this.stockquoteDetail = stockquoteDetail;
    }
}
