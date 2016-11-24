package org.gpschat.web.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

/**
 * Error
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-22T11:02:32.119Z")

public class ErrorData
{
	/**
	 * Gets or Sets error
	 */
	public enum ErrorEnum
	{
		DUPLICATE_EMAIL("DUPLICATE_EMAIL"),

		INVALID_USER("INVALID_USER"),

		INVALID_VALUE("INVALID_VALUE"),

		USER_IS_BLOCKED("USER_IS_BLOCKED"),

		USER_NOT_PART_OF_CHAT("USER_NOT_PART_OF_CHAT"),

		USER_NOT_FOUND("USER_NOT_FOUND"),

		CHAT_NOT_FOUND("CHAT_NOT_FOUND");

		private String value;

		ErrorEnum(String value)
		{
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString()
		{
			return String.valueOf(value);
		}

		@JsonCreator
		public static ErrorEnum fromValue(String text)
		{
			for (ErrorEnum b : ErrorEnum.values())
			{
				if (String.valueOf(b.value).equals(text))
				{
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("error")
	private ErrorEnum error = null;

	@JsonProperty("message")
	private String message = null;

	public ErrorData error(ErrorEnum error)
	{
		this.error = error;
		return this;
	}

	/**
	 * Get error
	 * 
	 * @return error
	 **/
	@ApiModelProperty(value = "")
	public ErrorEnum getError()
	{
		return error;
	}

	public void setError(ErrorEnum error)
	{
		this.error = error;
	}

	public ErrorData message(String message)
	{
		this.message = message;
		return this;
	}

	/**
	 * Get message
	 * 
	 * @return message
	 **/
	@ApiModelProperty(value = "")
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
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
		ErrorData error = (ErrorData) o;
		return Objects.equals(this.error, error.error)
				&& Objects.equals(this.message, error.message);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(error, message);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class Error {\n");

		sb.append("    error: ").append(toIndentedString(error)).append("\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
