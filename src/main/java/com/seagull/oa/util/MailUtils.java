package com.seagull.oa.util;

import org.springframework.stereotype.Component;

@Component
public class MailUtils {

    /**
     * 邮件发送
     *
     * @param host 邮件服务器
     * @param port 端口
     * @param username 邮箱地址
     * @param password 授权码
     * @param subject 主题
     * @param from 发件人
     * @param tos 收件人
     * @param html 是否html
     * @param content 内容
     * @param attachments 附件(name:文件名;url:链接;)
     *
     */
//	@Async
//	public void send(String host, int port, String username, String password, 
//			String subject, String from, String[] tos, boolean html, String content, List<Map<String,String>> attachments) {
//		try {	
//			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	        mailSender.setHost(host);
//	        mailSender.setPort(port);
//	       	mailSender.setUsername(username);
//	        mailSender.setPassword(password);
//	        mailSender.setDefaultEncoding("UTF-8");
//			// 创建一个MINE消息
//			MimeMessage message = mailSender.createMimeMessage();
//			MimeMessageHelper minehelper = new MimeMessageHelper(message, true);
//			minehelper.setFrom(from);
//			minehelper.setTo(tos);
//			minehelper.setSubject(subject);
//			// 邮件内容 true 表示带有附件或html
//			minehelper.setText(content, html);
//			if(attachments != null) {
//				for(int i=0;i<attachments.size();i++) {
//					InputStream in = new URL(attachments.get(i).get("url").toString()).openStream();
//					minehelper.addAttachment(attachments.get(i).get("name").toString(), 
//							new ByteArrayResource(IOUtils.toByteArray(in)));
//					in.close();
//				}
//			}
//			mailSender.send(message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
