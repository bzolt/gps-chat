package org.gpschat.fcm.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Notification
{
	@JsonProperty("title")
	private String			title;
	@JsonProperty("body")
	private String			body;
	@JsonProperty("icon")
	private String			icon;
	@JsonProperty("sound")
	private String			sound;
	@JsonProperty("group")
	private String			tag;
	@JsonProperty("color")
	private String			color;
	@JsonProperty("click_action")
	private String			clickAction;
	@JsonProperty("title_loc_key")
	private String			titleLocKey;
	@JsonProperty("title_loc_args")
	private List<String>	titleLocArgs;
	@JsonProperty("body_loc_key")
	private String			bodyLocKey;
	@JsonProperty("body_loc_args")
	private List<String>	bodyLocArgs;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getSound()
	{
		return sound;
	}

	public void setSound(String sound)
	{
		this.sound = sound;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getClickAction()
	{
		return clickAction;
	}

	public void setClickAction(String clickAction)
	{
		this.clickAction = clickAction;
	}

	public String getTitleLocKey()
	{
		return titleLocKey;
	}

	public void setTitleLocKey(String titleLocKey)
	{
		this.titleLocKey = titleLocKey;
	}

	public List<String> getTitleLocArgs()
	{
		return titleLocArgs;
	}

	public void setTitleLocArgs(List<String> titleLocArgs)
	{
		this.titleLocArgs = titleLocArgs;
	}

	public String getBodyLocKey()
	{
		return bodyLocKey;
	}

	public void setBodyLocKey(String bodyLocKey)
	{
		this.bodyLocKey = bodyLocKey;
	}

	public List<String> getBodyLocArgs()
	{
		return bodyLocArgs;
	}

	public void setBodyLocArgs(List<String> bodyLocArgs)
	{
		this.bodyLocArgs = bodyLocArgs;
	}

}
