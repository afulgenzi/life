package com.fulg.life.webmvc.controller.json.data;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 16/07/16.
 */
public abstract class JsonResult<T>
{
    private boolean success = false;
    private Messages messages;
    private T data;

    public JsonResult(final boolean success, final Messages messages, final T data)
    {
        this.success = success;
        this.messages = messages;
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(final boolean success)
    {
        this.success = success;
    }

    public Messages getMessages()
    {
        return messages;
    }

    public void setMessages(final Messages messages)
    {
        this.messages = messages;
    }

    public T getData()
    {
        return data;
    }

    public void setData(final T data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "JsonResult{" +
                "success=" + success +
                '}';
    }
}
