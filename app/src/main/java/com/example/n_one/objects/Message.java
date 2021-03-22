package com.example.n_one.objects;

public class Message implements Comparable<Message> {
    private String senderKey;
    private String receiverKey;
    private String text;
    private String timeStamp;
    private boolean seen;

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getReceiverKey() {
        return receiverKey;
    }

    public void setReceiverKey(String receiverKey) {
        this.receiverKey = receiverKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }


    @Override
    public int compareTo(Message m) {
        if (getTimeStamp() == null || m.getTimeStamp() == null) {
            return 0;
        }
        int thisTimeStamp = Integer.parseInt(getTimeStamp());
        int otherTimeStamp = Integer.parseInt(m.getTimeStamp());
        if (thisTimeStamp > otherTimeStamp) {
            return 1;
        } else if (thisTimeStamp < otherTimeStamp) {
            return -1;
        } else {
            return 0;
        }
    }
}
