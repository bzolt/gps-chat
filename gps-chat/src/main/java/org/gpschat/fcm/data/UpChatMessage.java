package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpChatMessage
{
	@JsonProperty("text")
	private String	text		= null;
	@JsonProperty("chatId")
	private String	chatId		= null;
	@JsonProperty("longitude")
	private Double	longitude	= null;
	@JsonProperty("latitude")
	private Double	latitude	= null;

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getChatId()
	{
		return chatId;
	}

	public void setChatId(String chatId)
	{
		this.chatId = chatId;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}
}
