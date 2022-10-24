package br.com.inatel.quotationManagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    private String id;

    private String stockId;

    @OneToMany(mappedBy = "stock")
    private List<Quote> quotes = new ArrayList<>();

    @PrePersist
    private void onPersist() {
        this.id = UUID.randomUUID().toString();
    }

}