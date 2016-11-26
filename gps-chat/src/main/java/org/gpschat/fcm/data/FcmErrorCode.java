package org.gpschat.fcm.data;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum FcmErrorCode
{
	INVALID_JSON,
	BAD_REGISTRATION,
	DEVICE_UNREGISTERED,
	BAD_ACK,
	SERVICE_UNAVAILABLE,
	DEVICE_MESSAGE_RATE_EXCEEDED,
	TOPICS_MESSAGE_RATE_EXCEEDED,
	CONNECTION_DRAINING,
	@JsonEnumDefaultValue
	INTERNAL_SERVER_ERROR;
}
