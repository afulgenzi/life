package com.fulg.life.webmvc.controller.json.data;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 16/07/16.
 */
public class Messages {
    final List<String> messages = Lists.newArrayList();

    public Messages(String... messages)
    {
        for (String msg : messages)
        {
            this.messages.add(msg);
        }
    }

    public List<String> getMessages()
    {
        return messages;
    }
}
