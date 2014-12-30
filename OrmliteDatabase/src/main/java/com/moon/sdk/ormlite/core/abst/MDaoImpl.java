package com.moon.sdk.ormlite.core.abst;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.moon.sdk.ormlite.core.itf.MIDao;
import com.moon.sdk.ormlite.core.MOrmliteHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/10/17.
 * 数据库操作实现类
 */
public abstract class MDaoImpl<T> implements MIDao<T> {
    /*传入类的class*/
    private Class<T> tClass;
    /*上下文,传入的目的就是为了获取数据库保存的路径*/
    private Context context ;

    /*通过父类获取传入实例的类型*/
    public MDaoImpl(Context context) {
        Type mType = getClass().getGenericSuperclass();
        Type[] mTypes = ((ParameterizedType) mType).getActualTypeArguments();
        tClass = (Class)mTypes[0] ;
        this.context = context ;
    }

    @Override
    public int insert(T dao) {
        int code = 0 ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context);
//        openWrDb(mOrmliteHelper) ;
        try {
            code = mBaseOrmliteHelper.getDao(tClass).create(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return code;
    }

    @Override
    public int insert(List<T> daos) {
        int code = 0 ;
        int N = daos.size() ;
        for(int i = 0 ;i < N ;i++ ){
            code = insert(daos.get(i)) ;
        }
        return code;
    }

    @Override
    public int delete(T dao) {
        int code = 0 ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context);
        openWrDb(mBaseOrmliteHelper) ;
        try {
            code = mBaseOrmliteHelper.getDao(tClass).delete(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return code;
    }

    @Override
    public int deleteAll() {
        int code = 0 ;
        List<T> list = queryAll() ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context) ;
//        openWrDb(mOrmliteHelper) ;
        try {
            code = mBaseOrmliteHelper.getDao(tClass).delete(list) ;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
//        Iterator<T> iterator = list.iterator();
//        while (iterator.hasNext()){
//            code = delete(iterator.next());
//        }
        return code;
    }

    @Override
    public int delete(Map<String, Object> map) {
        int code = 0 ;
        List<T> list = query(map) ;
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            code = delete(iterator.next());
        }
        return code;
    }

    @Override
    public int update(T dao) {
        int code = 0 ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context);
        openWrDb(mBaseOrmliteHelper) ;
        try {
            Dao.CreateOrUpdateStatus status = mBaseOrmliteHelper.getDao(tClass).createOrUpdate(dao);
            if(status.isUpdated()){
                code = 1 ;
            }else {
                code = 2 ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return code;
    }

    @Override
    public T queryById(T dao) {
        T queryDao = null ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context);
        openWrDb(mBaseOrmliteHelper) ;
        try {
            queryDao = mBaseOrmliteHelper.getDao(tClass).queryForSameId(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return queryDao;
    }

    @Override
    public List<T> queryAll() {
        List<T> tList = new ArrayList<T>() ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context) ;
//        openWrDb(mOrmliteHelper) ;
        try {
            tList.addAll(mBaseOrmliteHelper.getDao(tClass).queryForAll()) ;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return tList;
    }

    @Override
    public List<T> query(Map<String, Object> map) {
        List<T> tList = new ArrayList<T>() ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context) ;
        openWrDb(mBaseOrmliteHelper) ;
        try {
            tList.addAll(mBaseOrmliteHelper.getDao(tClass).queryForFieldValues(map)) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return tList;
    }

    @Override
    public long queryCount() {
        long count = 0 ;
        MBaseOrmliteHelper mBaseOrmliteHelper = new MOrmliteHelper(context);
        openWrDb(mBaseOrmliteHelper) ;
        try {
            count = mBaseOrmliteHelper.getDao(tClass).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mBaseOrmliteHelper != null)
                mBaseOrmliteHelper.close();
        }
        return count;
    }

    /**
     * 判断数据库是否已经关闭
     * @param db
     */
    private void openWrDb(MBaseOrmliteHelper db) {
        if (!db.isOpen()) {
              db.onOpen(db.getWritableDatabase());
        }

    }
}
