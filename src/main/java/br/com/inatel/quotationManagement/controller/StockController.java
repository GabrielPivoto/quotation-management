package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.form.QuoteForm;
import br.com.inatel.quotationManagement.service.StockService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<?> getStockByStockId(@PathVariable String id){
        return stockService.getStockByStockId(id);
    }

    @PostMapping("/postQuotes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> postQuotes(@RequestBody QuoteForm quoteForm){
        return stockService.postQuotes(quoteForm);
    }

}
