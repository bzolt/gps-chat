package org.gpschat.web.data;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Message
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-17T14:00:30.041Z")

public class ChatMessage
{
	@JsonProperty("senderId")
	private String senderId = null;

	@JsonProperty("senderUserName")
	private String senderUserName = null;

	@JsonProperty("text")
	private String text = null;

	@JsonProperty("chatId")
	private String chatId = null;

	@JsonProperty("dateTime")
	private OffsetDateTime dateTime = null;

	public ChatMessage senderId(String senderId)
	{
		this.senderId = senderId;
		return this;
	}

	/**
	 * Get senderId
	 * 
	 * @return senderId
	 **/
	@ApiModelProperty(value = "")
	public String getSenderId()
	{
		return senderId;
	}

	public void setSenderId(String senderId)
	{
		this.senderId = senderId;
	}

	public ChatMessage senderUserName(String senderUserName)
	{
		this.senderUserName = senderUserName;
		return this;
	}

	/**
	 * Get senderUserName
	 * 
	 * @return senderUserName
	 **/
	@ApiModelProperty(value = "")
	public String getSenderUserName()
	{
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName)
	{
		this.senderUserName = senderUserName;
	}

	public ChatMessage text(String text)
	{
		this.text = text;
		return this;
	}

	/**
	 * Get chatId
	 * 
	 * @return chatId
	 **/
	@ApiModelProperty(value = "")
	public String getChatId()
	{
		return chatId;
	}

	public void setChatId(String chatId)
	{
		this.chatId = chatId;
	}

	public ChatMessage chatId(String chatId)
	{
		this.chatId = chatId;
		return this;
	}

	/**
	 * Get text
	 * 
	 * @return text
	 **/
	@ApiModelProperty(value = "")
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public ChatMessage dateTime(OffsetDateTime dateTime)
	{
		this.dateTime = dateTime;
		return this;
	}

	/**
	 * Get dateTime
	 * 
	 * @return dateTime
	 **/
	@ApiModelProperty(value = "")
	public OffsetDateTime getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(OffsetDateTime dateTime)
	{
		this.dateTime = dateTime;
	}

	@Override
	public boolean equals(java.lang.Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ChatMessage message = (ChatMessage) o;
		return Objects.equals(this.senderId, message.senderId)
				&& Objects.equals(this.senderUserName, message.senderUserName)
				&& Objects.equals(this.text, message.text)
				&& Objects.equals(this.dateTime, message.dateTime);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(senderId, senderUserName, text, dateTime);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class Message {\n");

		sb.append("    senderId: ").append(toIndentedString(senderId)).append("\n");
		sb.append("    senderUserName: ").append(toIndentedString(senderUserName)).append("\n");
		sb.append("    text: ").append(toIndentedString(text)).append("\n");
		sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o)
	{
		if (o == null)
		{
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
