package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 *This class is used to filter the records on given query.
 */
public class QueryFilter implements IFilter{

	private List<ConditionalExpression> queries;
	
	
	/**
	 *Basic constructor.
	 * @param queries List of given queries.
	 */
	public QueryFilter(List<ConditionalExpression> queries) {
		this.queries = queries;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		int num = 0;
		for(int i= 0; i < queries.size(); i++, num++) {
			if(queries.get(i).getFieldGetter() == FieldValueGetters.JMBAG) {
				if(queries.get(i).getComparisonOperator().satisfied(record.getJmbag(), queries.get(i).getStringLiteral())) continue;
				break;
			} else if(queries.get(i).getFieldGetter() == FieldValueGetters.FIRST_NAME) {
				if(queries.get(i).getComparisonOperator().satisfied(record.getFirstName(), queries.get(i).getStringLiteral())) continue;
				break;
			} else if(queries.get(i).getFieldGetter() == FieldValueGetters.LAST_NAME) {
				if(queries.get(i).getComparisonOperator().satisfied(record.getLastName(), queries.get(i).getStringLiteral())) continue;
				break;
			} else {
				throw new QueryParserException("Unknown atribute.");
			}
		}
		if(num == queries.size()) return true;
		return false;
	}

}
