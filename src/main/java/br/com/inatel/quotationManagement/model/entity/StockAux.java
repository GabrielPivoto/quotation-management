package br.com.inatel.quotationManagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class model of Stock
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAux {

    @Id
    private String id;

    private String stockId;

    @OneToMany(mappedBy = "stock")
    private List<Quote> quotes = new ArrayList<>();

    public StockAux(String stockId, List<Quote> quotes){
        this.stockId = stockId;
        this.quotes = quotes;
    }

    @PrePersist
    private void onPersist() {
        this.id = UUID.randomUUID().toString();
    }

}
