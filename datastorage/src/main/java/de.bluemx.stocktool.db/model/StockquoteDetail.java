package de.bluemx.stocktool.db.model;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Entity(name = "stockquotedata")
public class StockquoteDetail implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "stockquotedata_id")
    private Long stockquotedataId;
    @Column(name = "fetch_date")
    private LocalDate fetchDate;
    @Column(name = "return_on_equity")
    private BigDecimal roe;
    @Column(name = "ebit_margin")
    private BigDecimal ebitMargin;
    @Column(name = "analysts_count")
    private Integer analystsCount;
    @Column(name = "analystsOpinion")
    private Integer analystsOpinion;
    @Column(name = "equityRatio")
    private BigDecimal equityRatio;
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
                "stockquotedata_id=" + stockquotedataId +
                ", fetch_date=" + fetchDate +
                ", return_on_equity=" + roe +
                ", ebit_margin=" + ebitMargin +
                ", analysts_count=" + analystsCount +
                ", analysts_opinion=" + analystsOpinion +
                ", equity_ratio=" + equityRatio +
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

        return stockquotedataId != null ? stockquotedataId.equals(that.stockquotedataId) : that.stockquotedataId == null;
    }

    @Override
    public int hashCode() {
        return stockquotedataId != null ? stockquotedataId.hashCode() : 0;
    }

    public Long getStockquotedataId() {
        return stockquotedataId;
    }

    public void setStockquotedataId(Long stockquotedataId) {
        this.stockquotedataId = stockquotedataId;
    }

    public LocalDate getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(LocalDate fetchDate) {
        this.fetchDate = fetchDate;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public void setRoe(BigDecimal return_on_equity) {
        this.roe = return_on_equity;
    }

    public BigDecimal getEbitMargin() {
        return ebitMargin;
    }

    public void setEbitMargin(BigDecimal ebitMargin) {
        this.ebitMargin = ebitMargin;
    }

    public Integer getAnalystsCount() {
        return analystsCount;
    }

    public void setAnalystsCount(Integer analystsCount) {
        this.analystsCount = analystsCount;
    }

    public Integer getAnalystsOpinion() {
        return analystsOpinion;
    }

    public void setAnalystsOpinion(Integer analysts_opinion) {
        this.analystsOpinion = analysts_opinion;
    }

    public BigDecimal getEquityRatio() {
        return equityRatio;
    }

    public void setEquityRatio(BigDecimal equity_ratio) {
        this.equityRatio = equity_ratio;
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
