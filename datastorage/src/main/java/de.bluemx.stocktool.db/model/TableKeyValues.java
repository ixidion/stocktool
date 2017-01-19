package de.bluemx.stocktool.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "table_key_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public class TableKeyValues implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_key_value_id")
    private int tableKeyValuesId;
    @Column(name = "table_year")
    private LocalDate tableYear;
    @Column(name = "table_value")
    private BigDecimal tableValue;
    @Column(name = "estimated")
    private boolean estimated;
    @ManyToOne
    @JoinColumn(name = "stockquotedata_id")
    private StockquoteDetail stockquoteDetail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableKeyValues that = (TableKeyValues) o;

        return tableKeyValuesId == that.tableKeyValuesId;
    }

    @Override
    public int hashCode() {
        return tableKeyValuesId;
    }

    @Override
    public String toString() {
        return "TableKeyValues{" +
                "tableKeyValuesId=" + tableKeyValuesId +
                ", tableYear=" + tableYear +
                ", tableValue=" + tableValue +
                ", estimated=" + estimated +
                ", stockquoteDetail=" + stockquoteDetail +
                '}';
    }

    public int getTableKeyValuesId() {
        return tableKeyValuesId;
    }

    public void setTableKeyValuesId(int tableKeyValuesId) {
        this.tableKeyValuesId = tableKeyValuesId;
    }

    public LocalDate getTableYear() {
        return tableYear;
    }

    public void setTableYear(LocalDate tableYear) {
        this.tableYear = tableYear;
    }

    public BigDecimal getTableValue() {
        return tableValue;
    }

    public void setTableValue(BigDecimal tableValue) {
        this.tableValue = tableValue;
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

