package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmMessage
{
	@JsonProperty("message_type")
	private String			type;
	@JsonProperty("message_id")
	private String			id;
	@JsonProperty("from")
	private String			from;
	@JsonProperty("registration_id")
	private String			registrationId;
	@JsonProperty("error")
	private FcmErrorCode	error;
	@JsonProperty("error_description")
	private String			errorDescription;
	@JsonProperty("control_type")
	private String			controlType;
	@JsonProperty("category")
	private String			category;
	@JsonProperty("data")
	private UpChatMessage	data;
	@JsonProperty("time_to_live")
	private String			timeToLive;

	public AckResponse asAck()
	{
		return new AckResponse(id, from);
	}

	public NackResponse asNack()
	{
		return new NackResponse(id, from, error, errorDescription);
	}

	public ControlResponse asControl()
	{
		return new ControlResponse(controlType);
	}

	public FcmUpstreamMessage asUpstreamMessage()
	{
		return new FcmUpstreamMessage(id, from, category, data);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
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

	public String getRegistrationId()
	{
		return registrationId;
	}

	public void setRegistrationId(String registrationId)
	{
		this.registrationId = registrationId;
	}

	public FcmErrorCode getError()
	{
		return error;
	}

	public void setError(FcmErrorCode error)
	{
		this.error = error;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription)
	{
		this.errorDescription = errorDescription;
	}

	public String getControlType()
	{
		return controlType;
	}

	public void setControlType(String controlType)
	{
		this.controlType = controlType;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public UpChatMessage getData()
	{
		return data;
	}

	public void setData(UpChatMessage data)
	{
		this.data = data;
	}

	public String getTimeToLive()
	{
		return timeToLive;
	}

	public void setTimeToLive(String timeToLive)
	{
		this.timeToLive = timeToLive;
	}

}