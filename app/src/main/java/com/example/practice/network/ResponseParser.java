package com.example.practice.network;

import com.example.practice.bean.LzyBaseResponse;
import com.example.practice.bean.PageList;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

@Parser(name = "Response", wrappers = {List.class, PageList.class})
public class ResponseParser<T> extends AbstractParser<T> {
    //注意，以下两个构造方法是必须的
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(Response response) throws IOException {
        if(response.code() != 200){
            throw new ParseException(String.valueOf(response.code()),"",response);
        }
        final Type type = ParameterizedTypeImpl.get(LzyBaseResponse.class, mType); //获取泛型类型
        LzyBaseResponse<T> data = convert(response, type);
        T t = data.getData(); //获取data字段
        if (data.getErrorCode() != 0 || t == null) {//这里假设code不等于200，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getErrorCode()), data.getErrorMsg(), response);
        }
        return t;
    }
}
