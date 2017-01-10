package de.bluemx.stocktool.db.model;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Entity
@Table(name = "stockquotedata_basic")
@NamedQuery(name = "StockquoteBasic.findAll", query = "SELECT basic FROM StockquoteBasic basic")
public class StockquoteBasic implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="stockquotedatabasic_id")
    private int stockquotedatabasicId;

    @Column(name="isin")
    private String isin;
    @Column(name="stockname")
    private String stockname;
    @Column(name="stockindex")
    private String index;
    @Column(name="symbol")
    private String symbol;
    @Column(name="financial_year")
    private LocalDate financialYear;
    @OneToMany(mappedBy = "stockquoteBasics", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<StockquoteDetail> fetches;

    public void addDetail(StockquoteDetail detail) {
        if (fetches == null) {
            fetches = new Vector<>();
        }
        fetches.add(detail);
        detail.setStockquoteBasics(this);
    }

    public void removeDetail(StockquoteDetail detail) {
        if (fetches == null) {
            return;
        }
        fetches.remove(detail);
        detail.setStockquoteBasics(null);
    }

    @Override
    public String toString() {
        return "StockquoteBasic{" +
                "stockquotedatabasicId=" + stockquotedatabasicId +
                ", isin='" + isin + '\'' +
                ", stockname='" + stockname + '\'' +
                ", stockindex='" + index + '\'' +
                ", symbol='" + symbol + '\'' +
                ", financialYear=" + financialYear +
                ", fetches=" + fetches +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockquoteBasic that = (StockquoteBasic) o;

        if (stockquotedatabasicId != that.stockquotedatabasicId) return false;
        return isin.equals(that.isin);
    }

    @Override
    public int hashCode() {
        int result = stockquotedatabasicId;
        result = 31 * result + isin.hashCode();
        return result;
    }

    public int getStockquotedatabasicId() {
        return stockquotedatabasicId;
    }

    public void setStockquotedatabasicId(int stockquotedatabasicId) {
        this.stockquotedatabasicId = stockquotedatabasicId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(LocalDate financialYear) {
        this.financialYear = financialYear;
    }

    public List<StockquoteDetail> getFetches() {
        return fetches;
    }

    public void setFetches(List<StockquoteDetail> fetches) {
        this.fetches = fetches;
    }
}
