package com.sp.questionnaire.utils;

import com.sp.questionnaire.entity.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-15 Saturday 08:11
 */
@Service
public class MailUtils {
    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "your@qq.com";
    public static String myEmailPassword = "pkqifoxowfikdcfg";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    // QQ邮箱的为smtp.qq.com
    public static String myEmailSMTPHost = "smtp.qq.com";


    private String senderName = "在线问卷系统";
    private String themeActivate = "在线问卷系统账号激活通知";
    private String themePaperStart = "问卷生效通知";
    private String themePaperEnd = "问卷结束通知";

    @Value("${mail.ActivatePrefix}")
    private String url;

    @Value("${mail.startPaperPrefix}")
    private String paperUrl;

    @Value("${home.url}")
    private String homeUrl;

    @Autowired
    private CommonUtils commonUtils;

    public void sendActivateMail(String receiveMailAccount, String nickName, String randomCode) throws MessagingException {
        StringBuffer content = new StringBuffer();
        content.append("<b>您已在本系统成功注册账号<br>昵称为：").append(nickName).append("<br><br>")
                .append("现在<a href='").append(url).append(randomCode).append("'>点击这里激活账号</a>即可开始使用！")
                .append("</b><br>如非本人操作，请忽略本邮件。");
        mailSettings(senderName, themeActivate, receiveMailAccount, nickName, content.toString());
    }

    public void sendPaperStartMail(String receiveMailAccount, String nickName, Paper paper) throws MessagingException {
        StringBuffer content = new StringBuffer();
        content.append("<b>尊敬的").append(nickName).append("<br><br>")
                .append("您于").append(commonUtils.getFormatDateTime(paper.getCreateTime()))
                .append("创建的标题为“").append(paper.getTitle()).append("”的问卷，已经可以正式答卷了，快点分享出去吧！<br><br>")
                .append("现在<a href='").append(paperUrl).append(paper.getId()).append("'>点击这里</a>即可查看问卷详情！</b>")
                .append("<br><br>使用提示：打开链接后可复制问卷链接或直接分享给他人，亦可<a href='").append(homeUrl).append("'>登录账号</a>查看更多信息。");
        mailSettings(senderName, themePaperStart, receiveMailAccount, nickName, content.toString());

    }

    public void sendPaperEndMail(String receiveMailAccount, String nickName, Paper paper) throws MessagingException {
        StringBuffer content = new StringBuffer();
        content.append("<b>尊敬的").append(nickName).append("<br><br>")
                .append("您于").append(commonUtils.getFormatDateTime(paper.getCreateTime()))
                .append("创建的标题为“").append(paper.getTitle()).append("”的问卷，已经结束了！<br><br>")
                .append("现在<a href='").append(homeUrl).append(paper.getId()).append("'>登录账号</a>即可查看问卷数据详情！</b>")
                .append("<br><br>使用提示：为便于显示请使用电脑查看。");
        mailSettings(senderName, themePaperEnd, receiveMailAccount, nickName, content.toString());
    }

    //(session, myEmailAccount, receiveMailAccount, "发件人名字","收件人名字","同志","标题","内容：2943<a href='http://www.baidu.com'>Baidu</a>");
    public void mailSettings(String senderName, String theme, String receiveMailAccount,
                             String nickName, String content) throws MessagingException {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。

        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);


        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件，
        // Session session, String sendMail,String receiveMail, String sender, String recipients, String theme, String content
        //session，发件人邮箱，收件人邮箱，发件人名字，收件人名字，邮件标题，邮件内容
        MimeMessage message = null;
        try {
            message = createMimeMessage(session, myEmailAccount, receiveMailAccount,
                    senderName, nickName, theme, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发第二条
        /*receiveMailAccount = "youEmail@qq.com";
        MimeMessage message2 = createMimeMessage(session, myEmailAccount, receiveMailAccount,
        		"发件人名字","收件人名字","标题","内容：134");
        */

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //
        //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        //transport.sendMessage(message2, message2.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session             和服务器交互的会话
     * @param senderMailAccount   发件人邮箱
     * @param receiverMailAccount 收件人邮箱
     * @return
     * @throws Exception
     */
    //session，发件人邮箱，收件人邮箱，发件人名字，收件人名字，称呼(例：***同学，老师等）,邮件标题，邮件内容
    public MimeMessage createMimeMessage(
            Session session, String senderMailAccount, String receiverMailAccount, String senderName, String receiverName,
            String theme, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(senderMailAccount, senderName, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverMailAccount, receiverName, "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(theme, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

}

