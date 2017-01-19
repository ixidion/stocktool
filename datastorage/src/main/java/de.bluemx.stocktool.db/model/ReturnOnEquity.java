package de.bluemx.stocktool.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ROE")
public class ReturnOnEquity extends TableKeyValues {

}
