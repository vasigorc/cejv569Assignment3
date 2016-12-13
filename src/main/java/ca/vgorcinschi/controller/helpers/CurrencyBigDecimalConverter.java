package ca.vgorcinschi.controller.helpers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.converter.BigDecimalStringConverter;

/**
 *
 * @author vgorcinschi
 */
public class CurrencyBigDecimalConverter extends BigDecimalStringConverter {

    private final NumberFormat currencyFormat;

    public CurrencyBigDecimalConverter(Locale locale) {
        currencyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    public String toString(BigDecimal bd) {
        return bd == null ? currencyFormat.format(BigDecimal.ZERO) : currencyFormat.format(bd);
    }
}
