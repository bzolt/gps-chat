package org.gpschat.web.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserIdList
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-22T11:02:32.119Z")

public class StringList
{
	@JsonProperty("items")
	private List<String> items = new ArrayList<String>();

	public StringList items(List<String> items)
	{
		this.items = items;
		return this;
	}

	public StringList addItemsItem(String itemsItem)
	{
		this.items.add(itemsItem);
		return this;
	}

	/**
	 * Get items
	 * 
	 * @return items
	 **/
	@ApiModelProperty(value = "")
	public List<String> getItems()
	{
		return items;
	}

	public void setItems(List<String> items)
	{
		this.items = items;
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
		StringList userIdList = (StringList) o;
		return Objects.equals(this.items, userIdList.items);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(items);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("class UserIdList {\n");

		sb.append("    ids: ").append(toIndentedString(items)).append("\n");
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
