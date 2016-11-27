package org.gpschat.web.config;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.GeoNearOperation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GeoJsonNearOperation implements AggregationOperation
{
	private final GeoJsonPoint	point;
	private final String		distanceField;

	/**
	 * Creates a new {@link GeoNearOperation} from the given {@link NearQuery}
	 * and the given distance field. The {@code distanceField} defines output
	 * field that contains the calculated distance.
	 * 
	 * @param query
	 *            must not be {@literal null}.
	 * @param distanceField
	 *            must not be {@literal null}.
	 */
	public GeoJsonNearOperation(GeoJsonPoint point, String distanceField)
	{
		this.distanceField = distanceField;
		this.point = point;
	}

	@Override
	public DBObject toDBObject(AggregationOperationContext context)
	{
		BasicDBObject pointObject = new BasicDBObject();
		pointObject.put("type", point.getType());
		pointObject.put("coordinates", point.getCoordinates());

		BasicDBObject command = new BasicDBObject();

		command.put("near", pointObject);
		command.put("spherical", true);
		command.put("distanceMultiplier", 0.001);
		command.put("distanceField", distanceField);

		return new BasicDBObject("$geoNear", command);
	}
}
