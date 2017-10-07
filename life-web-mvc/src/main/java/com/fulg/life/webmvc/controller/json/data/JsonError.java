package com.fulg.life.webmvc.controller.json.data;

/**
 * Created by alessandro.fulgenzi on 16/07/16.
 */
public class JsonError<T> extends JsonResult<T>
{
    public JsonError(final String... messages)
    {
        super(false, new Messages(messages), null);
    }
}
