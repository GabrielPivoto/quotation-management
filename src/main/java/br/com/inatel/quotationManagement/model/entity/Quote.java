package br.com.inatel.quotationManagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Class model of Quote.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quote {

    @Id
    private String id;

    @NotNull
    private LocalDate date;

    @NotNull
    private double cost;

    @ManyToOne
    private StockAux stock;

    @PrePersist
    private void onPersist() {
        this.id = UUID.randomUUID().toString();
    }

    public Quote(Double cost, LocalDate date){
        this.cost = cost;
        this.date = date;
    }

    public Quote(Double cost, LocalDate date, StockAux stock){
        this.cost = cost;
        this.date = date;
        this.stock = stock;
    }

}
