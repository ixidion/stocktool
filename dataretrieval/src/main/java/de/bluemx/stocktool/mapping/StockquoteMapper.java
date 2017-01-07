package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.model.StockQuoteData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockquoteMapper {
    StockquoteMapper INSTANCE = Mappers.getMapper(StockquoteMapper.class);

    @Mapping(source = "stockname", target = "stockname")
    StockquoteBasic sourceToTarget(StockQuoteData source);


}
