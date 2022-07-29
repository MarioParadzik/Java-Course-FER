package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	@Test
	public void testConditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		StudentRecord student = new StudentRecord("123456789", "Bosec", "Damir", "4");
		assertTrue(expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(student),
				expr.getStringLiteral()));
	}
}
