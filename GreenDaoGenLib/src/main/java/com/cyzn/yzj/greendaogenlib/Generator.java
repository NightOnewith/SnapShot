package com.cyzn.yzj.greendaogenlib;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * 生成dao
 */
public class Generator {

    private static Schema schema;

    public static void main(String[] args) throws Exception {
        //创建模式对象，指定版本号和自动生成的bean对象的包名
        schema = new Schema(Config.VERSION, Config.DEFAULT_PACKAGE);

        //添加所有实体
        addEntity();

        //调用DaoGenerator().generateAll方法自动生成代码到创建的目录下
        new DaoGenerator().generateAll(schema, Config.OUTDIR);
    }

    private static void addEntity() {
        //添加一个实体，自动生成实体Entity类
        Entity account = schema.addEntity("Person");

        /* Person */
        account.addStringProperty("id").primaryKey().getProperty();  //添加ID, 主键
        account.addStringProperty("name").notNull();  //添加String类型的name,不能为空
        account.addIntProperty("age");  //添加Int类型的age
    }
}
