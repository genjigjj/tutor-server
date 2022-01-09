package com.wrc.tutor.system.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {



//    protected InjectionConfig injectionConfig; 注入配置,不需要
//    private DataSourceConfig dataSource; 数据源配置  搞定
//    private StrategyConfig strategy; 策略配置 x
//    private PackageConfig packageInfo; 包配置 x
//    private TemplateConfig template; 模版配置 默认即可
//    private GlobalConfig globalConfig; 全局配置 x
//    private AbstractTemplateEngine templateEngine; 模版引擎配置 ,默认即可

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("wrc");
        gc.setOutputDir("d://tutor");
        gc.setEntityName("%s");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sIService");
        gc.setServiceImplName("%sService");
        gc.setControllerName("%sController");
        gc.setIdType(IdType.ID_WORKER);
        gc.setFileOverride(true);

        gc.setBaseResultMap(true);//生成基本的resultMap
        gc.setBaseColumnList(true);//生成基本的SQL片段

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/tutor_system?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wrc.tutor.system");
        pc.setEntity("common.entity.po");
        pc.setService("front.Iservice");
        pc.setServiceImpl("front.service");
        pc.setMapper("common.mapper");
        pc.setXml("mapper.xml");
//        pc.setPathInfo("");
        mpg.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();

    }
}
