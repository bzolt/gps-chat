package org.gpschat.web.config;

import javax.net.ssl.SSLSocketFactory;

import org.gpschat.fcm.data.FcmMessage;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.sasl.javax.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.gcm.provider.GcmExtensionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.xmpp.config.XmppConnectionFactoryBean;
import org.springframework.integration.xmpp.inbound.ChatMessageListeningEndpoint;
import org.springframework.integration.xmpp.outbound.ChatMessageSendingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class FcmConfig
{
	private String senderId = "896132321102";

	private String token = "AAAA0KWmD04:APA91bF4xlWtBxOMji-UeiBJ21l9Use77PscnmEwyN7v33j6v_5jPbs8MHN2cMSU4Vno36f_yGSGkqIPbORR5lJPBdYJKvVdMv3fzMZ1udhUn9pkVOa3IZzfOnR8lsxy2UFP4yFkU5_BXe1Ny7OkKRV3CjaTiRrePQ";

	private String host = "fcm-xmpp.googleapis.com";

	private int port = 5235;

	@Bean
	public XMPPTCPConnectionConfiguration connectionConfiguration()
	{

		XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
		builder.setHost(host).setPort(port).setSendPresence(false)
				.setSocketFactory(SSLSocketFactory.getDefault()).setServiceName("FCM")
				.setUsernameAndPassword(senderId + "@gcm.googleapis.com", token)
				.setDebuggerEnabled(true);

		// connectionConfig.setReconnectionAllowed(true);
		return builder.build();
	}

	@Bean(name = "xmppConnection")
	public XmppConnectionFactoryBean xmppConnectionFactoryBean()
	{
		SASLAuthentication.registerSASLMechanism(new SASLPlainMechanism());

		Roster.setRosterLoadedAtLoginDefault(false);

		XmppConnectionFactoryBean connectionFactoryBean = new XmppConnectionFactoryBean();
		connectionFactoryBean.setConnectionConfiguration(connectionConfiguration());
		connectionFactoryBean.setSubscriptionMode(null);
		return connectionFactoryBean;
	}

	@Bean(name = "fcmChannel")
	public MessageChannel messageChannel()
	{
		return new DirectChannel();
	}

	@Bean(name = "fcmInJsonChannel")
	public MessageChannel messageInChannel()
	{
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "fcmJsonChannel")
	public MessageHandler messageHandler()
	{
		ChatMessageSendingMessageHandler handler = new ChatMessageSendingMessageHandler();
		handler.setLoggingEnabled(true);
		handler.setExtensionProvider(new GcmExtensionProvider());
		return handler;
	}

	@Bean
	@Transformer(inputChannel = "fcmChannel", outputChannel = "fcmJsonChannel")
	public ObjectToJsonTransformer objectToJsonTransformer()
	{
		return new ObjectToJsonTransformer();
	}

	@Bean
	public ChatMessageListeningEndpoint chatMessageListeningEndpoint()
	{
		ChatMessageListeningEndpoint endpoint = new ChatMessageListeningEndpoint();
		ExpressionParser parser = new SpelExpressionParser();
		endpoint.setPayloadExpression(
				parser.parseExpression("getExtension('google:mobile:data').json"));
		endpoint.setOutputChannel(messageInChannel());

		return endpoint;
	}

	@Bean
	@Transformer(inputChannel = "fcmInJsonChannel", outputChannel = "fcmInChannel")
	public JsonToObjectTransformer jsonToObjectTransformer()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
		Jackson2JsonObjectMapper mapper = new Jackson2JsonObjectMapper(objectMapper);
		return new JsonToObjectTransformer(FcmMessage.class, mapper);
	}
}
