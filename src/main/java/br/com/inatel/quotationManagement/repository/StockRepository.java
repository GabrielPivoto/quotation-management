package br.com.inatel.quotationManagement.repository;

import br.com.inatel.quotationManagement.model.entity.StockAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Repository
public interface StockRepository extends JpaRepository<StockAux,String> {

    /**
     * Method responsible for finding a Stock by stockId
     * @param stockId String
     * @return an Optional of Stock
     */
    Optional<StockAux> findByStockId(String stockId);

}
