package gestion_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class TestConversion {

	@Test
	void test1() {
		BigDecimal valorPrueba = new BigDecimal(5000.00);
		DecimalFormat decimalFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ITALIAN));
		assertEquals(decimalFormat.format(valorPrueba), "5000,12");
	}
	
	@Test
	void test3() {
		BigDecimal valorPrueba = new BigDecimal(5000.12);
		DecimalFormat decimalFormat = new DecimalFormat("###.##", new DecimalFormatSymbols(new Locale("es", "AR")));
		assertEquals(decimalFormat.format(valorPrueba), "5000,12");
	}
	@Test
	void test2() {
		BigDecimal valorPrueba = new BigDecimal(5000.12);
		String pattern = "###.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String formattedNumber = decimalFormat.format(valorPrueba).replace(".",",");
		assertEquals(formattedNumber, "5000,12");
	}

}
