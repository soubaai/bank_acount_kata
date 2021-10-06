package acount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocalDate.class, Account.class })
public class AccountTest {
	private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 10, 05);

	@Before
	public void setUp() {
		PowerMockito.stub(PowerMockito.method(LocalDate.class, "now")).toReturn(LOCAL_DATE);
	}

	@Test
	public void depositNegativeAmountTest() {
		Account acount = new Account();
		OperationAmountNotPermit operationAmountNotPermit = assertThrows(OperationAmountNotPermit.class, () -> {
			acount.deposit(new BigDecimal(-2));
		});
		String expectedMessage = "Deposit a negative amount is not permitted";
		String actualMessage = operationAmountNotPermit.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void depositNullAmountTest() {
		Account acount = new Account();
		OperationAmountNotPermit operationAmountNotPermit = assertThrows(OperationAmountNotPermit.class, () -> {
			acount.deposit(null);
		});
		String expectedMessage = "Deposit a null amount is not permitted";
		String actualMessage = operationAmountNotPermit.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void depositPositiveFirstAmountTest() throws OperationAmountNotPermit {
		Account acount = new Account();
		acount.deposit(new BigDecimal(20));
		assertEquals(20, acount.getBalance().intValue());
	}

	@Test
	public void depositPositiveAmountTest() throws OperationAmountNotPermit {
		Account acount = new Account(new BigDecimal(20));
		acount.deposit(new BigDecimal(10));
		assertEquals(30, acount.getBalance().intValue());
	}

	@Test
	public void withdawNegativeAmountTest() {
		Account acount = new Account();
		OperationAmountNotPermit operationAmountNotPermit = assertThrows(OperationAmountNotPermit.class, () -> {
			acount.withdraw(new BigDecimal(-2l));
		});
		String expectedMessage = "Withraw a negative amount is not permitted";
		String actualMessage = operationAmountNotPermit.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void withdawNullAmountTest() {
		Account acount = new Account();
		OperationAmountNotPermit operationAmountNotPermit = assertThrows(OperationAmountNotPermit.class, () -> {
			acount.withdraw(null);
		});
		String expectedMessage = "Withraw a null amount is not permitted";
		String actualMessage = operationAmountNotPermit.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void withdawInsufficientBalanceTest() {
		Account acount = new Account(new BigDecimal(20));
		OperationAmountNotPermit operationAmountNotPermit = assertThrows(OperationAmountNotPermit.class, () -> {
			acount.withdraw(new BigDecimal(21));
		});
		String expectedMessage = "Insufficient balance";
		String actualMessage = operationAmountNotPermit.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void withdrawAmountTest() throws OperationAmountNotPermit {
		Account acount = new Account(new BigDecimal(20));
		acount.withdraw(new BigDecimal(10));
		assertEquals(10, acount.getBalance().intValue());
	}

	@Test
	public void withdrawAllTest() throws OperationAmountNotPermit {
		Account acount = new Account(new BigDecimal(20));
		acount.withdraw(new BigDecimal(20));
		assertEquals(0, acount.getBalance().intValue());
	}

	@Test
	public void showOpertionsHistoryTest() throws OperationAmountNotPermit {
		Account acount = new Account(new BigDecimal(100));
		acount.deposit(new BigDecimal(100));
		acount.withdraw(new BigDecimal(10));
		acount.deposit(new BigDecimal(20));
		acount.withdraw(new BigDecimal(10l));

		assertEquals(acount.getOperationList().size(), 4);
		// First operation
		assertEquals(acount.getOperationList().get(0).getAmount().intValue(), 100);
		assertEquals(acount.getOperationList().get(0).getOperationType(), OperationType.DEPOSIT);
		assertEquals(acount.getOperationList().get(0).getDate(), LOCAL_DATE);

		// Second operation
		assertEquals(acount.getOperationList().get(1).getAmount().intValue(), 10);
		assertEquals(acount.getOperationList().get(1).getOperationType(), OperationType.WITHDRAW);
		assertEquals(acount.getOperationList().get(1).getDate(), LOCAL_DATE);

		// Third operation
		assertEquals(acount.getOperationList().get(2).getAmount().intValue(), 20);
		assertEquals(acount.getOperationList().get(2).getOperationType(), OperationType.DEPOSIT);
		assertEquals(acount.getOperationList().get(2).getDate(), LOCAL_DATE);

		// Fourth operation
		assertEquals(acount.getOperationList().get(3).getAmount().intValue(), 10);
		assertEquals(acount.getOperationList().get(3).getOperationType(), OperationType.WITHDRAW);
		assertEquals(acount.getOperationList().get(3).getDate(), LOCAL_DATE);

		// Balance of the account
		assertEquals(acount.getBalance().intValue(), 200);

	}

}
