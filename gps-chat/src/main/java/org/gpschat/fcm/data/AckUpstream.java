package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AckUpstream
{
	@JsonProperty("message_type")
	private final String	type	= "ack";
	@JsonProperty("message_id")
	private final String	id;
	@JsonProperty("to")
	private final String	to;

	public AckUpstream(String id, String to)
	{
		this.id = id;
		this.to = to;
	}
}