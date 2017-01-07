package de.bluemx.stocktool.db.model;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Entity(name = "stockquotedata")
public class StockquoteDetail implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "stockquotedata_id")
    private Long stockquotedata_id;
    @Column(name = "fetch_date")
    private LocalDate fetch_date;
    @Column(name = "return_on_equity")
    private Double return_on_equity;
    @Column(name = "ebit_margin")
    private Double ebit_margin;
    @Column(name = "analysts_count")
    private Long analysts_count;
    @Column(name = "analysts_opinion")
    private Long analysts_opinion;
    @Column(name = "equity_ratio")
    private Double equity_ratio;
    @ManyToOne
    @JoinColumn(name = "stockquotedatabasic_id")
    private StockquoteBasic stockquoteBasics;

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<Eps> epsList;

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<PriceEarningsRatio> priceEarningsRatioList;

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<HistoricalQuote> historicalQuoteList;

    public void addEps(Eps eps) {
        if (epsList == null) {
            epsList = new Vector<>();
        }
        epsList.add(eps);
        eps.setStockquoteDetail(this);
    }

    public void removeEps(Eps eps) {
        if (epsList == null) {
            return;
        }
        epsList.remove(eps);
        eps.setStockquoteDetail(null);
    }

    public void addPer(PriceEarningsRatio per) {
        if (priceEarningsRatioList == null) {
            priceEarningsRatioList = new Vector<>();
        }
        priceEarningsRatioList.add(per);
        per.setStockquoteDetail(this);
    }

    public void removePer(PriceEarningsRatio per) {
        if (priceEarningsRatioList == null) {
            return;
        }
        priceEarningsRatioList.remove(per);
        per.setStockquoteDetail(null);
    }

    public void addQuote(HistoricalQuote quote) {
        if (historicalQuoteList == null) {
            historicalQuoteList = new Vector<>();
        }
        historicalQuoteList.add(quote);
        quote.setStockquoteDetail(this);
    }

    public void removeDetail(HistoricalQuote quote) {
        if (historicalQuoteList == null) {
            return;
        }
        historicalQuoteList.remove(quote);
        quote.setStockquoteDetail(null);
    }


    @Override
    public String toString() {
        return "StockquoteDetail{" +
                "stockquotedata_id=" + stockquotedata_id +
                ", fetch_date=" + fetch_date +
                ", return_on_equity=" + return_on_equity +
                ", ebit_margin=" + ebit_margin +
                ", analysts_count=" + analysts_count +
                ", analysts_opinion=" + analysts_opinion +
                ", equity_ratio=" + equity_ratio +
                ", epsList=" + epsList +
                ", priceEarningsRatioList=" + priceEarningsRatioList +
                ", historicalQuotesList=" + historicalQuoteList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockquoteDetail that = (StockquoteDetail) o;

        return stockquotedata_id != null ? stockquotedata_id.equals(that.stockquotedata_id) : that.stockquotedata_id == null;
    }

    @Override
    public int hashCode() {
        return stockquotedata_id != null ? stockquotedata_id.hashCode() : 0;
    }

    public Long getStockquotedata_id() {
        return stockquotedata_id;
    }

    public void setStockquotedata_id(Long stockquotedata_id) {
        this.stockquotedata_id = stockquotedata_id;
    }

    public LocalDate getFetch_date() {
        return fetch_date;
    }

    public void setFetch_date(LocalDate fetch_date) {
        this.fetch_date = fetch_date;
    }

    public Double getReturn_on_equity() {
        return return_on_equity;
    }

    public void setReturn_on_equity(Double return_on_equity) {
        this.return_on_equity = return_on_equity;
    }

    public Double getEbit_margin() {
        return ebit_margin;
    }

    public void setEbit_margin(Double ebit_margin) {
        this.ebit_margin = ebit_margin;
    }

    public Long getAnalysts_count() {
        return analysts_count;
    }

    public void setAnalysts_count(Long analysts_count) {
        this.analysts_count = analysts_count;
    }

    public Long getAnalysts_opinion() {
        return analysts_opinion;
    }

    public void setAnalysts_opinion(Long analysts_opinion) {
        this.analysts_opinion = analysts_opinion;
    }

    public Double getEquity_ratio() {
        return equity_ratio;
    }

    public void setEquity_ratio(Double equity_ratio) {
        this.equity_ratio = equity_ratio;
    }

    public StockquoteBasic getStockquoteBasics() {
        return stockquoteBasics;
    }

    public void setStockquoteBasics(StockquoteBasic stockquoteBasics) {
        this.stockquoteBasics = stockquoteBasics;
    }

    public List<Eps> getEpsList() {
        return epsList;
    }

    public void setEpsList(List<Eps> epsList) {
        this.epsList = epsList;
    }

    public List<PriceEarningsRatio> getPriceEarningsRatioList() {
        return priceEarningsRatioList;
    }

    public void setPriceEarningsRatioList(List<PriceEarningsRatio> priceEarningsRatioList) {
        this.priceEarningsRatioList = priceEarningsRatioList;
    }

    public List<HistoricalQuote> getHistoricalQuoteList() {
        return historicalQuoteList;
    }

    public void setHistoricalQuoteList(List<HistoricalQuote> historicalQuoteList) {
        this.historicalQuoteList = historicalQuoteList;
    }
}
