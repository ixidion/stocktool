package de.bluemx.stocktool.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PER")
public class PriceEarningsRatio extends TableKeyValues {
}