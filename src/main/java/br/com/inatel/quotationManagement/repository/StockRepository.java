package br.com.inatel.quotationManagement.repository;

import br.com.inatel.quotationManagement.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {

    Optional<Stock> findByStockId(String id);

}
