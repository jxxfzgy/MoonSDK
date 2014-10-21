package sdk.moon.com.moonsdk.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by moon on 2014/10/22 0022.
 * 通讯录
 */
@DatabaseTable(tableName = "contact_zgy")
public class MContactBean  {
    @DatabaseField (generatedId = true)
    private String name ;
    @DatabaseField
    private String tel ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "MContactBean{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
