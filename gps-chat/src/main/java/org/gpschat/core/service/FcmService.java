package org.gpschat.core.service;

import org.gpschat.fcm.data.AckResponse;
import org.gpschat.fcm.data.ControlResponse;
import org.gpschat.fcm.data.FcmMessage;
import org.gpschat.fcm.data.FcmUpstreamMessage;
import org.gpschat.fcm.data.NackResponse;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class FcmService
{
	@Autowired
	@Qualifier("fcmChannel")
	private MessageChannel messageChannel;

	public void send()
	{

		User user = new User();
		user.setEmail("qwe");
		user.setFullName("qweasd");

		Message<User> xmppOutboundMsg = MessageBuilder.withPayload(user)
				.setHeader(XmppHeaders.TO, "userhandle").build();
		messageChannel.send(xmppOutboundMsg);
	}

	@ServiceActivator(inputChannel = "fcmInChannel")
	public void receive(FcmMessage message)
	{
		if (message.getType() == null)
		{
			receiveUpstream(message.asUpstreamMessage());
		}
		else
		{
			if ("ack".equalsIgnoreCase(message.getType()))
			{
				receiveAck(message.asAck());
			}
			else if ("nack".equalsIgnoreCase(message.getType()))
			{
				receiveNack(message.asNack());
			}
			else if ("control".equalsIgnoreCase(message.getType()))
			{
				receiveControl(message.asControl());
			}
		}
	}

	private void receiveUpstream(FcmUpstreamMessage message)
	{

	}

	private void receiveAck(AckResponse ack)
	{

	}

	private void receiveNack(NackResponse nack)
	{

	}

	private void receiveControl(ControlResponse control)
	{

	}
}
