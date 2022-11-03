package br.com.inatel.quotationManagement.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Form class. Used for sending data to DataBase.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {

    @NotNull @NotEmpty @Length(min = 2)
    private String stockId;

    @NotNull @NotEmpty
    private Map<LocalDate, Double> quotesMap = new HashMap<>();

}
