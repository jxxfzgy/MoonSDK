package com.to8to.clickstream.network;

import com.to8to.clickstream.entity.IFormData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class ClickDataRequest<T> extends ClickRequest<T> {
    private List<IFormData> formDatas;
    private String BOUNDARY = "-----------------------------7d92221b604bc"; //数据分隔线

    public ClickDataRequest(List<IFormData> formDatas, int method, Type type, ClickResponse clickResponse) {
        super(null, method, type, clickResponse);
        this.formDatas = formDatas;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (formDatas == null || formDatas.size() == 0) {
            return super.getBody();
        }
        try {
            for (IFormData formData : formDatas) {
                StringBuilder sb = new StringBuilder();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;");
                sb.append("name=\"");
                sb.append(formData.getName());
                sb.append("\"");
                sb.append("\r\n");
                sb.append("Content-Type:");
                sb.append(formData.getMimeType());
                sb.append("\r\n");
                sb.append("\r\n");
                bos.write(sb.toString().getBytes("UTF-8"));
                bos.write(formData.getData());
                bos.write("\r\n".getBytes("UTF-8"));
            }
            bos.write(( BOUNDARY+"--").getBytes("UTF-8"));
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data ; boundary=" + BOUNDARY;
    }
}
