package de.bluemx.stocktool.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EPS")
public class Eps extends TableKeyValues {

}
