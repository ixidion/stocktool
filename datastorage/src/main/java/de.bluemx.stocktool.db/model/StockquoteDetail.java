package de.bluemx.stocktool.db.model;

import de.bluemx.stocktool.db.converter.LocalDateAttributeConverter;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Entity
@Table(name = "stockquotedata")
public class StockquoteDetail implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "stockquotedata_id")
    private Long stockquotedataId;
    @Column(name = "fetch_date")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fetchDate;
    @Column(name = "analysts_count")
    private Integer analystsCount;
    @Column(name = "analysts_opinion")
    private Integer analystsOpinion;
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

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<EquityRatio> equityRatioList;

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<ReturnOnEquity> returnOnEquityList;

    @OneToMany(mappedBy = "stockquoteDetail", cascade = CascadeType.ALL)
    @JoinFetch(JoinFetchType.OUTER)
    private List<EbitMargin> ebitMarginList;

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

    public void removeQuote(HistoricalQuote quote) {
        if (historicalQuoteList == null) {
            return;
        }
        historicalQuoteList.remove(quote);
        quote.setStockquoteDetail(null);
    }

    public void addEquityRatio(EquityRatio equityRatio) {
        if (equityRatioList == null) {
            equityRatioList = new Vector<>();
        }
        equityRatioList.add(equityRatio);
        equityRatio.setStockquoteDetail(this);
    }

    public void removeEquityRatio(EquityRatio equityRatio) {
        if (equityRatioList == null) {
            return;
        }
        equityRatioList.remove(equityRatio);
        equityRatio.setStockquoteDetail(null);
    }

    public void addEquityRatio(ReturnOnEquity returnOnEquity) {
        if (returnOnEquityList == null) {
            returnOnEquityList = new Vector<>();
        }
        returnOnEquityList.add(returnOnEquity);
        returnOnEquity.setStockquoteDetail(this);
    }

    public void removeEquityRatio(ReturnOnEquity returnOnEquity) {
        if (returnOnEquityList == null) {
            return;
        }
        returnOnEquityList.remove(returnOnEquity);
        returnOnEquity.setStockquoteDetail(null);
    }

    public void addEquityRatio(EbitMargin ebitMargin) {
        if (ebitMarginList == null) {
            ebitMarginList = new Vector<>();
        }
        ebitMarginList.add(ebitMargin);
        ebitMargin.setStockquoteDetail(this);
    }

    public void removeEquityRatio(EbitMargin ebitMargin) {
        if (ebitMarginList == null) {
            return;
        }
        ebitMarginList.remove(ebitMargin);
        ebitMargin.setStockquoteDetail(null);
    }

    @Override
    public String toString() {
        return "StockquoteDetail{" +
                "stockquotedataId=" + stockquotedataId +
                ", fetchDate=" + fetchDate +
                ", analystsCount=" + analystsCount +
                ", analystsOpinion=" + analystsOpinion +
                ", epsList=" + epsList +
                ", priceEarningsRatioList=" + priceEarningsRatioList +
                ", historicalQuoteList=" + historicalQuoteList +
                ", equityRatioList=" + equityRatioList +
                ", returnOnEquityList=" + returnOnEquityList +
                ", ebitMarginList=" + ebitMarginList +
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

    public List<EquityRatio> getEquityRatioList() {
        return equityRatioList;
    }

    public void setEquityRatioList(List<EquityRatio> equityRatioList) {
        this.equityRatioList = equityRatioList;
    }

    public List<ReturnOnEquity> getReturnOnEquityList() {
        return returnOnEquityList;
    }

    public void setReturnOnEquityList(List<ReturnOnEquity> returnOnEquityList) {
        this.returnOnEquityList = returnOnEquityList;
    }

    public List<EbitMargin> getEbitMarginList() {
        return ebitMarginList;
    }

    public void setEbitMarginList(List<EbitMargin> ebitMarginList) {
        this.ebitMarginList = ebitMarginList;
    }
}
