package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.form.QuoteForm;
import br.com.inatel.quotationManagement.model.form.StockForm;
import br.com.inatel.quotationManagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockDto> listAllStocks(){
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getStockByStockId(@PathVariable String id){
        return stockService.getStockByStockId(id);
    }

    @PostMapping("/postQuotes")
    public void postQuotes(@RequestBody QuoteForm quoteForm){
        stockService.postQuotes(quoteForm);
    }

}
