package com.cz.txl.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cz.txl.task.SendMailTask;

import junit.framework.TestCase;

public class TestSendMailTask extends TestCase{
	public void testSendMain() throws AddressException, IOException, MessagingException{
		SendMailTask task = new SendMailTask();
		task.send_email("424080919@qq.com", "你好", "我是你哥");;
	}
}
