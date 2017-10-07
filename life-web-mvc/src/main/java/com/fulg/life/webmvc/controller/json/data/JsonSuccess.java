package com.fulg.life.webmvc.controller.json.data;

/**
 * Created by alessandro.fulgenzi on 16/07/16.
 */
public class JsonSuccess<T> extends JsonResult<T> {
    public JsonSuccess(final T data, final String... messages)
    {
        super(true, new Messages(messages), data);
    }

    public JsonSuccess(final T data)
    {
        super(true, null, data);
    }
}
