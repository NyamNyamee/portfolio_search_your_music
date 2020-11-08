package kr.inwoo.music.service;


public interface EMailService 
{
	boolean sendMail(String email, String subject, String content);
	boolean sendMail(String email, String subject, String content,String[] fileNames, boolean isFile);
}
