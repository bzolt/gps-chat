package org.gpschat.swagger;

import java.text.FieldPosition;
import java.util.Date;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class RFC3339DateFormat extends ISO8601DateFormat
{

	// Same as ISO8601DateFormat but serializing milliseconds.
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
	{
		String value = String.valueOf(date.getTime());// ISO8601Utils.format(date,
														// true);
		toAppendTo.append(value);
		return toAppendTo;
	}

}