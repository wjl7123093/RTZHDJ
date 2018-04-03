package com.mytv.rtzhdj.app.data;

import com.mytv.rtzhdj.mvp.model.api.Api;

import java.io.Serializable;


/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by jess on 26/09/2016 15:19
 * Contact with jess.yan.effort@gmail.com
 */

public class BaseJson<T> implements Serializable{
    private T data;
    private int status;
    private String info;

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        if (status == Api.RequestSuccess) {
            return true;
        } else {
            return false;
        }
    }
}
