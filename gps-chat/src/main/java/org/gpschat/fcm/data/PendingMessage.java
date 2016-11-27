package org.gpschat.fcm.data;

public class PendingMessage
{
	private int						retryCount;
	private FcmDownstreamMessage	message;

	public PendingMessage(int retryCount, FcmDownstreamMessage message)
	{
		this.retryCount = retryCount;
		this.message = message;
	}

	public int getRetryCount()
	{
		return retryCount;
	}

	public FcmDownstreamMessage getMessage()
	{
		return message;
	}

	public void incrementRetryCount()
	{
		retryCount++;
	}
}
