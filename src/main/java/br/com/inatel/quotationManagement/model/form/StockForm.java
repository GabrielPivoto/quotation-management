package br.com.inatel.quotationManagement.model.form;

import br.com.inatel.quotationManagement.model.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockForm {

    private String stockId;
    private List<Quote> quotesList = new ArrayList<>();

}
