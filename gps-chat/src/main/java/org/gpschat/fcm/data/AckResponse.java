package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AckResponse
{
	@JsonProperty("message_id")
	private String	id;
	@JsonProperty("from")
	private String	from;

	public AckResponse()
	{
		// For JSON instantiation
	}

	public AckResponse(String id, String from)
	{
		this.id = id;
		this.from = from;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}
}
