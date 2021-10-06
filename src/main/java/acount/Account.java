package acount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Account {
	private BigDecimal balance;
	private List<Operation> operationList = new ArrayList<Operation>();

	public Account(BigDecimal balance) {
		this.balance = balance;
	}

	public Account() {
		super();
		this.balance = new BigDecimal("0");
	}

	public void deposit(BigDecimal amount) throws OperationAmountNotPermit {
		if (amount == null)
			throw new OperationAmountNotPermit("Deposit a null amount is not permitted");
		if (amount.intValue() < 0)
			throw new OperationAmountNotPermit("Deposit a negative amount is not permitted");
		balance = balance.add(amount);
		Operation operation = new Operation(OperationType.DEPOSIT, LocalDate.now(), amount);
		operationList.add(operation);
	}

	public void withdraw(BigDecimal amount) throws OperationAmountNotPermit {
		if (amount == null)
			throw new OperationAmountNotPermit("Withraw a null amount is not permitted");
		if (amount.intValue() < 0)
			throw new OperationAmountNotPermit("Withraw a negative amount is not permitted");

		if (balance.intValue() < amount.intValue())
			throw new OperationAmountNotPermit("Insufficient balance");
		balance = balance.subtract(amount);
		Operation operation = new Operation(OperationType.WITHDRAW, LocalDate.now(), amount);
		operationList.add(operation);
	}

}
