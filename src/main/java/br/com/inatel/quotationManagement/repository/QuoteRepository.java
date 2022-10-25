package br.com.inatel.quotationManagement.repository;

import br.com.inatel.quotationManagement.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
public interface QuoteRepository extends JpaRepository<Quote, String> {
}
