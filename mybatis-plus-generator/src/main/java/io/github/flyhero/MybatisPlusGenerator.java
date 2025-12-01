package io.github.flyhero;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;
import java.util.Scanner;

public class MybatisPlusGenerator {
    
    public static void main(String[] args) {
        String model = input("请输入模块名称：");
        String packageName = input("请输入父包名称：");
        String path = Paths.get(System.getProperty("user.dir")) + "/" + model;
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/program?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC&remarks=true&useInformationSchema=true", "root", "12345678")
                .globalConfig(builder -> builder
                        .author("flyhero")
                        .outputDir(path+ "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> builder
                        .parent("io.github.flyhero")
                        .moduleName(packageName)
                        .pathInfo(Collections.singletonMap(OutputFile.xml, path + "/src/main/resources/xml")) // 设置路径配置信息
                )
                .strategyConfig(builder -> builder
                        .controllerBuilder()
                        .enableRestStyle() //Rest风格
                        .superClass("io.github.flyhero.common.controller.BaseController")
                        .entityBuilder()
                        .superClass("io.github.flyhero.common.mybatis.plus.BaseEntity")
                        .addSuperEntityColumns("id","tenant_id","create_time","update_time","is_deleted")
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    public static String input(String msg){
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.next();
    }

}
