package org.gpschat.web.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserIdList
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-21T16:39:32.587Z")

public class UserIdList
{
	@JsonProperty("ids")
	private List<String> ids = new ArrayList<String>();

	public UserIdList ids(List<String> ids)
	{
		this.ids = ids;
		return this;
	}

	public UserIdList addIdsItem(String idsItem)
	{
		this.ids.add(idsItem);
		return this;
	}

	/**
	 * Get ids
	 * 
	 * @return ids
	 **/
	@ApiModelProperty(value = "")
	public List<String> getIds()
	{
		return ids;
	}

	public void setIds(List<String> ids)
	{
		this.ids = ids;
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
		UserIdList userIdList = (UserIdList) o;
		return Objects.equals(this.ids, userIdList.ids);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(ids);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class UserIdList {\n");

		sb.append("    ids: ").append(toIndentedString(ids)).append("\n");
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
