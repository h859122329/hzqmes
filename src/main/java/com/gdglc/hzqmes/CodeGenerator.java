package com.gdglc.hzqmes;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者
        gc.setAuthor("gdglc");
        //是否覆蓋已有文件 默认值：false
        gc.setFileOverride(false);
        //是否打开输出目录 默认值:true
        gc.setOpen(false);
        //开启 baseColumnList 默认false
        gc.setBaseColumnList(true);
        //开启 BaseResultMap 默认false
        gc.setBaseResultMap(true);
        //mapper 命名方式 默认值：null 例如：%sDao 生成 UserDao
        gc.setMapperName("%sDao");
        //service 命名方式   默认值：null 例如：%sBusiness 生成 UserBusiness
        gc.setServiceName("%sService");
        //service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setServiceImplName("%sServiceImpl");
        //controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.1.212:3306/plasticsmes?useUnicode=true&useSSL=false&characterEncoding=utf8");
//        dsc.setUrl("jdbc:mysql://localhost:3306/plasticsmes?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("hzqmes");
        dsc.setPassword("bdqn");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //    pc.setModuleName(scanner("模块名"));
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent("com.gdglc.hzqmes");
        pc.setMapper("dao");
        pc.setEntity("po");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板.
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // templateConfig.setEntity();
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //【实体】是否为lombok模型（默认 false)
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setInclude(scanner("表名"));
        //strategy.setInclude("asm_factor_pair","asm_factor_comb");
//        strategy.setInclude("activities_serv", "activity", "activity_tag", "actv_application", "admin_users", "alipay_pay_order", "all_order_pay",
//                "area", "category", "code_static_table", "consult_reserve", "consult_schedule", "consult_schedule_tmpl", "consult_serv", "consultant",
//                "finance_charge", "finance_pay", "finance_record", "finance_withdraw_req", "goods", "goods_detail", "head_teacher", "oauth", "order_details",
//                "orders", "pic_data", "qn_paper_quest", "qn_paper_tag", "question", "question_tag", "questionnair_tag", "questionnaire", "questionnaire_paper",
//                "report", "school", "school_org", "school_org_acl", "student_care_list", "student_profile", "tag", "user_qn_answer", "user_qp_acl", "user_questionnaire",
//                "user_role", "user_score_logs", "user_service_acl", "user_withdraw_req", "users", "wechat_pay_order", "weixin_bind", "weixin_chat"
//        );
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
