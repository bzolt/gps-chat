package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmUpstreamMessage
{
	@JsonProperty("message_id")
	private String	id;
	@JsonProperty("from")
	private String	from;
	@JsonProperty("category")
	private String	category;
	@JsonProperty("data")
	private String	data;

	public FcmUpstreamMessage(String id, String from, String category, String data)
	{
		this.id = id;
		this.from = from;
		this.category = category;
		this.data = data;
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

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
