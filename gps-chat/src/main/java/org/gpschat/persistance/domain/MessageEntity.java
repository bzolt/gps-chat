package org.gpschat.persistance.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("message")
@Document
public class MessageEntity
{
	@Id
	private String		id;

	@DBRef
	private UserEntity	sender;
	private String		chatId;
	private String		text;
}
