package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpChatMessage
{
	@JsonProperty("text")
	private String	text	= null;
	@JsonProperty("chatId")
	private String	chatId	= null;
	@JsonProperty("x")
	private Double	x		= null;
	@JsonProperty("y")
	private Double	y		= null;

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

	public Double getX()
	{
		return x;
	}

	public void setX(Double x)
	{
		this.x = x;
	}

	public Double getY()
	{
		return y;
	}

	public void setY(Double y)
	{
		this.y = y;
	}
}
