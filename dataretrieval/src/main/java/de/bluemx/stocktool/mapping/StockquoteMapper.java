package de.bluemx.stocktool.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockquoteMapper {
    StockquoteMapper INSTANCE = Mappers.getMapper(StockquoteMapper.class);

//    @Mapping(source = "stockname", target = "stockname")
//    StockquoteBasic sourceToTarget(StockQuoteData source);
//

}
