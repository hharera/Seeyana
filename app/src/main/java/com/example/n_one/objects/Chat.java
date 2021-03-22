package com.example.n_one.objects;

import java.util.HashSet;
import java.util.List;

public class Chat implements Comparable<Chat>{
        private List<Message> messages;

//    public HashSet<String> getParticipants() {
//        return mParticipants;
//    }
    public HashSet<String> getParticipants() {
        if (messages == null || messages.size() == 0) {
            return null;
        }
        HashSet<String> participants = new HashSet<>();
        participants.add(getLastMessage().getSenderKey());
        participants.add(getLastMessage().getReceiverKey());

        return participants;
    }

//    public void setParticipants(HashSet<String> participants) {
//        mParticipants = participants;
//    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        if (getMessages() == null ||
        getMessages().size() == 0) {
            return null;
        } else {
            int lastIndex = getMessages().size()-1;
            return getMessages().get(lastIndex);
        }

    }


    @Override
    public int compareTo(Chat c) {
        if (getMessages() == null || c.getMessages() == null ||
        getMessages().size() == 0 || c.getMessages().size() == 0) {
            return 0;
        }

        int thisLastTimeStamp = Integer.parseInt(getMessages().get(getMessages().size()-1).getTimeStamp());
        int otherLastTimeStamp = Integer.parseInt(c.getMessages().get(c.getMessages().size()-1).getTimeStamp());

        if (thisLastTimeStamp > otherLastTimeStamp) {
            return +1;
        } else if (thisLastTimeStamp < otherLastTimeStamp) {
            return -1;
        } else {
            return 0;
        }
    }
}
