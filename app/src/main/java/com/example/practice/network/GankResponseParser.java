package com.example.practice.network;

import com.example.practice.bean.GankLzyBaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

@Parser(name = "GankResponse", wrappers = {List.class})
public class GankResponseParser<T> extends AbstractParser<GankLzyBaseResponse<T>>{

    //干货集中营（https://gank.io/api）统一解析的和WanAndroid的不一样上下拉刷新都包含在data,而干货的API上下拉刷新都在最外层，因此新建一个
    //注意，以下两个构造方法是必须的
    protected GankResponseParser(){
        super();
    }

    public GankResponseParser(Type type){
        super(type);
    }

    @Override
    public GankLzyBaseResponse<T> onParse(Response response) throws IOException{
        if(response.code() != 200){
            throw new ParseException(String.valueOf(response.code()), "", response);
        }
        final Type type = ParameterizedTypeImpl.get(GankLzyBaseResponse.class, mType); //获取泛型类型
        GankLzyBaseResponse<T> data = convert(response, type);
        int status  = data.getStatus(); //获取data字段
        T mdata=data.getData();
        if(status != 100 || mdata == null){//这里假设code不等于200，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getStatus()), /*data.getErrorMsg()*/"错误", response);
        }
        return data;
    }
}
