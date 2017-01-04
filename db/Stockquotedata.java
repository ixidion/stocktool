package com.my.db;

public class Stockquotedata {
  private Long stockquotedata_id;
  private java.sql.Date fetch_date;
  private Double return_on_equity;
  private Double ebit_margin;
  private Long analysts_count;
  private Long analysts_opinion;
  private Long stockquotedatabasic_id;
  private Double equity_ration;

  public Long getStockquotedata_id() {
    return stockquotedata_id;
  }

  public void setStockquotedata_id(Long stockquotedata_id) {
    this.stockquotedata_id = stockquotedata_id;
  }

  public java.sql.Date getFetch_date() {
    return fetch_date;
  }

  public void setFetch_date(java.sql.Date fetch_date) {
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

  public Long getStockquotedatabasic_id() {
    return stockquotedatabasic_id;
  }

  public void setStockquotedatabasic_id(Long stockquotedatabasic_id) {
    this.stockquotedatabasic_id = stockquotedatabasic_id;
  }

  public Double getEquity_ration() {
    return equity_ration;
  }

  public void setEquity_ration(Double equity_ration) {
    this.equity_ration = equity_ration;
  }
}
