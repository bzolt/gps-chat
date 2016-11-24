package org.gpschat.fcm.data;

public class ControlResponse
{
	final private String controlType;

	public ControlResponse(String controlType)
	{
		this.controlType = controlType;
	}

	public String getControlType()
	{
		return controlType;
	}
}
