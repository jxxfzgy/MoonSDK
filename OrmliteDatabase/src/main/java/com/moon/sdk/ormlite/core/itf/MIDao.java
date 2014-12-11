package com.moon.sdk.ormlite.core.itf;

import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/10/17.
 * 数据库操作接口定义
 */
public interface MIDao<T> {
    /*插入一条数据*/
    public int insert(T dao) ;
    /*插入一组数据*/
    public int insert(List<T> daos) ;
    /*删除一条数据*/
    public int delete(T dao) ;
    /*删除所有*/
    public int deleteAll() ;
    /*根据条件删除*/
    public int delete(Map<String,Object> map) ;
    /*更新一条数据*/
    public int update(T dao) ;
    /*根据条件来更新数据*/
//    public int update(Map<String,Object> map,T dao) ;
    /*通过Id查找到相应的数据*/
    public T queryById(T dao);
    /*查询所有的数据*/
    public List<T> queryAll() ;
    /*通过最少一个条件查询相应的数据*/
    public List<T> query(Map<String,Object> map) ;
    /*查询数据表中有多少条数据*/
    public long queryCount() ;

}
