package de.bluemx.stocktool.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EQUITY_RATIO")
public class EquityRatio extends TableKeyValues {

}
