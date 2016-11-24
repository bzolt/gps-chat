package org.gpschat.core.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateService
{
	public Date toDate(OffsetDateTime from)
	{
		return Date.from(from.atZoneSameInstant(ZoneId.systemDefault()).toInstant());
	}

	public OffsetDateTime toOffsetDateTime(Date from)
	{
		return OffsetDateTime.ofInstant(from.toInstant(), ZoneId.systemDefault());
	}
}
