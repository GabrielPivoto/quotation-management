package br.com.inatel.quotationManagement.repository;

import br.com.inatel.quotationManagement.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, String> {
}
