package com.mytv.rtzhdj.app.data.entity;

/**
 * MyMsgEntity   私信实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-2
 * @update
 */
public class MyMsgEntity {

    private int MessageId;
    private String MessageTopic;
    private String MessageTime;

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public String getMessageTopic() {
        return MessageTopic;
    }

    public void setMessageTopic(String messageTopic) {
        MessageTopic = messageTopic;
    }

    public String getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(String messageTime) {
        MessageTime = messageTime;
    }
}
