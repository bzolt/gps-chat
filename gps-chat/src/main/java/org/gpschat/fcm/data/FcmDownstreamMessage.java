package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmDownstreamMessage
{
	@JsonProperty("message_id")
	private String			id;
	@JsonProperty("to")
	private String			to;
	@JsonProperty("condition")
	private String			condition;
	@JsonProperty("collapse_key")
	private String			collapseKey;
	@JsonProperty("priority")
	private String			priority;
	@JsonProperty("time_to_live")
	private Integer			timeToLive;
	@JsonProperty("delivery_receipt_requested")
	private Boolean			deliveryReceiptRequested;
	@JsonProperty("dry_run")
	private Boolean			dryRun;
	@JsonProperty("notification")
	private Notification	notification;
	@JsonProperty("data")
	private Object			data;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	public String getCollapseKey()
	{
		return collapseKey;
	}

	public void setCollapseKey(String collapseKey)
	{
		this.collapseKey = collapseKey;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public Integer getTimeToLive()
	{
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive)
	{
		this.timeToLive = timeToLive;
	}

	public Boolean getDeliveryReceiptRequested()
	{
		return deliveryReceiptRequested;
	}

	public void setDeliveryReceiptRequested(Boolean deliveryReceiptRequested)
	{
		this.deliveryReceiptRequested = deliveryReceiptRequested;
	}

	public Boolean getDryRun()
	{
		return dryRun;
	}

	public void setDryRun(Boolean dryRun)
	{
		this.dryRun = dryRun;
	}

	public Notification getNotification()
	{
		return notification;
	}

	public void setNotification(Notification notification)
	{
		this.notification = notification;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
