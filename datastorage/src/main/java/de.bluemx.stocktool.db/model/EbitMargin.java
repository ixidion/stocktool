package de.bluemx.stocktool.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EBIT_MARGIN")
public class EbitMargin extends TableKeyValues {

}
