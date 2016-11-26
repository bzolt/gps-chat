package org.gpschat.fcm.data;

public class NackResponse
{
	final private String		id;
	final private String		from;
	final private FcmErrorCode	error;
	final private String		errorDescription;

	public NackResponse(String id, String from, FcmErrorCode error, String errorDescription)
	{
		this.id = id;
		this.from = from;
		this.error = error;
		this.errorDescription = errorDescription;
	}

	public String getId()
	{
		return id;
	}

	public String getFrom()
	{
		return from;
	}

	public FcmErrorCode getError()
	{
		return error;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}
}
