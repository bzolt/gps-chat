package org.gpschat.web.config;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class WithinOperation implements AggregationOperation
{

	@Override
	public DBObject toDBObject(AggregationOperationContext context)
	{
		BasicDBObject within = new BasicDBObject();
		within.put("$lt", new String[] { "$distance", "$viewDistance" });

		BasicDBObject command = new BasicDBObject();
		command.put("viewDistance", 1);
		command.put("distance", 1);
		command.put("within", within);

		return new BasicDBObject("$project", command);
	}

}
