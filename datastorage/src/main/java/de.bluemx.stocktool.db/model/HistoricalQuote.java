package de.bluemx.stocktool.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name="historical_quotes")
public class HistoricalQuote implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="historical_quotes_id")
    private int quoteId;
    @Column(name="quote_date")
    private LocalDate quoteDate;
    @Column(name="quote")
    private BigDecimal quote;
    @ManyToOne
    @JoinColumn(name = "stockquotedata_id")
    private StockquoteDetail stockquoteDetail;

    @Override
    public String toString() {
        return "HistoricalQuotes{" +
                "epsId=" + quoteId +
                ", quoteDate=" + quoteDate +
                ", quote=" + quote +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoricalQuote that = (HistoricalQuote) o;

        return quoteId == that.quoteId;
    }

    @Override
    public int hashCode() {
        return quoteId;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int epsId) {
        this.quoteId = epsId;
    }

    public LocalDate getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(LocalDate quoteDate) {
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
