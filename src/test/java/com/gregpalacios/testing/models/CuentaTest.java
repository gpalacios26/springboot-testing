package com.gregpalacios.testing.models;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.gregpalacios.testing.exceptions.DineroInsuficienteException;

class CuentaTest {

	Cuenta cuenta;

	@BeforeEach
	void initMetodoTest(TestInfo testInfo, TestReporter testReporter) throws Exception {
		this.cuenta = new Cuenta("Gregory", new BigDecimal("1000.12345"));
		System.out.println("Iniciando el metodo");
		testReporter.publishEntry(
				" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName());
		System.out.println("--------------------------------------------");
	}

	@Test
	@DisplayName("Prueba Nombre Cuenta")
	void testNombreCuenta() {
		Assertions.assertEquals("Gregory", cuenta.getPersona(), "El nombre de la persona no es el esperado");
	}

	@Test
	@DisplayName("Prueba Saldo Cuenta")
	void testSaldoCuenta() {
		Assertions.assertNotNull(cuenta.getSaldo(), "La cuenta no puede ser nula");
		Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
		Assertions.assertTrue(cuenta.getSaldo().compareTo(new BigDecimal("0")) > 0);
		Assertions.assertFalse(cuenta.getSaldo().compareTo(new BigDecimal("0")) < 0);
	}

	@Test
	@DisplayName("Prueba Referencia Cuenta")
	void testReferenciaCuenta() {
		Cuenta cuenta_1 = new Cuenta("Gregory Palacios", new BigDecimal("1200.12345"));
		Cuenta cuenta_2 = new Cuenta("Gregory Palacios", new BigDecimal("1200.12345"));
		Assertions.assertEquals(cuenta_2, cuenta_1);
	}

	@Test
	@DisplayName("Prueba Debito Cuenta")
	void testDebitoCuenta() {
		cuenta.debito(new BigDecimal("100"));
		Assertions.assertNotNull(cuenta.getSaldo());
		Assertions.assertEquals(900, cuenta.getSaldo().intValue());
		Assertions.assertEquals("900.12345", cuenta.getSaldo().toPlainString());
	}

	@Test
	@DisplayName("Prueba Crédito Cuenta")
	void testCreditoCuenta() {
		cuenta.credito(new BigDecimal("100"));
		Assertions.assertNotNull(cuenta.getSaldo());
		Assertions.assertEquals(1100, cuenta.getSaldo().intValue());
		Assertions.assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
	}

	@Test
	@DisplayName("Prueba Dinero Insuficiente Cuenta - Exception")
	void testDineroInsuficienteExceptionCuenta() {
		Exception exception = Assertions.assertThrows(DineroInsuficienteException.class, () -> {
			cuenta.debito(new BigDecimal("1200"));
		});
		String actual = exception.getMessage();
		String esperado = "Dinero Insuficiente";
		Assertions.assertEquals(esperado, actual);
	}

	@Test
	@DisplayName("Prueba Transferir Dinero Cuenta")
	void testTransferirDineroCuenta() {
		Cuenta origen = new Cuenta("Gregory Palacios", new BigDecimal("2500"));
		Cuenta destino = new Cuenta("Flavio Palacios", new BigDecimal("1600"));
		Banco banco = new Banco();
		banco.setNombre("Banco de Crédito");
		banco.transferir(origen, destino, new BigDecimal(500));
		Assertions.assertEquals("2000", origen.getSaldo().toPlainString());
		Assertions.assertEquals("2100", destino.getSaldo().toPlainString());
	}

	@Test
	@DisplayName("Prueba Relación Banco Cuentas")
	void testRelacionBancoCuentas() {
		Cuenta origen = new Cuenta("Gregory Palacios", new BigDecimal("2500"));
		Cuenta destino = new Cuenta("Flavio Palacios", new BigDecimal("1600"));
		Banco banco = new Banco();
		banco.setNombre("Banco de Crédito");
		banco.addCuenta(origen);
		banco.addCuenta(destino);

		Assertions.assertAll(() -> {
			Assertions.assertEquals(2, banco.getCuentas().size());
		}, () -> {
			Assertions.assertEquals("Banco de Crédito", origen.getBanco().getNombre());
		}, () -> {
			Assertions.assertEquals("Gregory Palacios", banco.getCuentas().stream()
					.filter(c -> c.getPersona().equals("Gregory Palacios")).findFirst().get().getPersona());
		}, () -> {
			Assertions.assertTrue(banco.getCuentas().stream().filter(c -> c.getPersona().equals("Gregory Palacios"))
					.findFirst().isPresent());
		});
	}

	@Nested
	class PruebasParametrizadasTest {

		@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
		@ValueSource(strings = { "100", "200", "300", "500", "700", "1000" })
		void testDebitoCuentaValueSource(String monto) {
			cuenta.debito(new BigDecimal(monto));
			Assertions.assertNotNull(cuenta.getSaldo());
			Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
		}

		@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
		@CsvSource({ "1,100", "2,200", "3,300", "4,500", "5,700", "6,1000" })
		void testDebitoCuentaCsvSource(String index, String monto) {
			System.out.println(index + " -> " + monto);
			cuenta.debito(new BigDecimal(monto));
			Assertions.assertNotNull(cuenta.getSaldo());
			Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
		}

	}

}
