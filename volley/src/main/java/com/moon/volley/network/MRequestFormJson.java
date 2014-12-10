package com.moon.volley.network;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.moon.volley.MConstant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MRequestFormJson<T> extends MRequestJson<T> {

    private String BOUNDARY = "---------7d4a6d158c9"; //数据分隔线
    private List<MFormItem> formItems;

    public MRequestFormJson(List<MFormItem> items, String url, Map<String, String> params, Type type, MResponse<T> response) {
        super(Method.POST, url, params, type, response);
        formItems = items;
    }

    public MRequestFormJson(List<MFormItem> items, Type type, MResponse<T> response) {
        this(items, MConstant.URL, null, type, response);
    }

/*1.
          第一行是“-----------------------------7d92221b604bc”作为分隔符，然后是“/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
2.        第二行
（1）     首先是HTTP中的扩展头部分“Content-Disposition: form-data;”，表示上传的是表单数据。
（2）    “name="name1"”参数的名称。
（3）    “/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
3.       第三行：“/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
4.       第四行：参数的值，最后是“/r/n”（即16进制编辑器显示的0D 0A）回车换行符。*/


/*1.     第一行是“-----------------------------7d92221b604bc”作为分隔符，然后是“/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
2.       第二行：
a)       首先是HTTP中的扩展头部分“Content-Disposition: form-data;”，表示上传的是表单数据。
b)       “name="file2";”参数的名称。
c)       “filename="C:/2.txt"”参数的值。
d)       “/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
3.       第三行：HTTP中的实体头部分“Content-Type: text/plain”：表示所接收到得实体内容的文件格式。计算机的应用中有多种多种通用的文件格式，人们为每种通用格式都定义了一个名称，称为MIME，MIME的英文全称是"Multipurpose Internet Mail Extensions" （多功能Internet 邮件扩充服务）
4.       第四行：“/r/n”（即16进制编辑器显示的0D 0A）回车换行符。
5.       第五行开始：上传的内容的二进制数。
6.       最后是结束标志“-----------------------------7d92221b604bc--”，注意：这个结束标志和分隔符的区别是最后多了“--”部分。*/
    @Override
    public byte[] getBody() throws AuthFailureError {
        if (formItems == null || formItems.isEmpty()) {
            return super.getBody();
        }
        int N = formItems.size();
        MFormItem formItem;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < N; i++) {
                formItem = formItems.get(i);
                StringBuilder sb = new StringBuilder();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;");
                sb.append("name=\"");
                sb.append(formItem.getName());
                sb.append("\"");
                if(!TextUtils.isEmpty(formItem.getFilePath()))
                    sb.append("filename=").append("\""+formItem.getFilePath()+"\"") ;
                sb.append("\r\n") ;
                sb.append("Content-Type: ") ;
                sb.append(formItem.getMimeType()) ;
                sb.append("\r\n");
                sb.append("\r\n");
                bos.write(sb.toString().getBytes(getParamsEncoding()));
                bos.write(formItem.getBody());
                bos.write("\r\n".getBytes(getParamsEncoding()));
            }
            bos.write((BOUNDARY + "--").getBytes(getParamsEncoding()));
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("zgy", "=======" + bos.toString()) ;
        return bos.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        /*conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY); */
        return "multipart/form-data ; boundary=" + BOUNDARY;
    }
}
