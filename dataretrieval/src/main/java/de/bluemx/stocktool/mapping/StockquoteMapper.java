package de.bluemx.stocktool.mapping;

import de.bluemx.stocktool.db.model.StockquoteBasic;
import de.bluemx.stocktool.db.model.StockquoteDetail;
import de.bluemx.stocktool.model.StockQuoteData;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {IndexMapper.class, AnalystsMapper.class})
@DecoratedWith(StockquoteMapperDecorator.class)
public interface StockquoteMapper {
    StockquoteMapper INSTANCE = Mappers.getMapper(StockquoteMapper.class);

    StockquoteBasic quoteToBasic(StockQuoteData source);

    StockquoteDetail quoteToDetail(StockQuoteData source);

}
