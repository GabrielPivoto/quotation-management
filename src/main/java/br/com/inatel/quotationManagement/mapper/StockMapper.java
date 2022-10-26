package br.com.inatel.quotationManagement.mapper;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class. Responsible for conversions concerning stocks.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
public class StockMapper {

    public static List<StockDto> convertToDto(List<StockAux> stocks){
        return stocks.stream().map(StockDto::new).collect(Collectors.toList());
    }

    public static StockAux convertToEntity(Form form){
        return new StockAux(form.getStockId(),QuoteMapper.convertMapToList(form));
    }


}
