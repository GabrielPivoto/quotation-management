package br.com.inatel.quotationManagement.mapper;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Stock;

import java.util.List;
import java.util.stream.Collectors;

public class StockMapper {

    public static List<StockDto> convertToDto(List<Stock> stocks){
        return stocks.stream().map(StockDto::new).collect(Collectors.toList());
    }


}
