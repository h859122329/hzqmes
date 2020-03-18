package com.gdglc.hzqmes.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * <p>Title: 事务通知配置类</p>
 * <p>Description: 针对service的方法进行配置</p>
 * <p>Copyright: Copyright @ 2018 北大青鸟广力学院版权所有</p>
 * <p>Company: www.gdglc.com</p>
 * @author: Zhong Guoce
 * @date: 2018/09
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.gdglc.hzqmes.service.*.*(..))";

    private PlatformTransactionManager transactionManager;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     *
     * 功能描述: 配置事务通知
     *
     * @param: []
     * @return: org.springframework.transaction.interceptor.TransactionInterceptor
     * @auther: Zhong Guoce
     * @date: 2018/9
     */
    @Bean
    public TransactionInterceptor txAdvice() {
        //PROPAGATION_REQUIRED
        //如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
        DefaultTransactionAttribute txAttrRequired = new DefaultTransactionAttribute();
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        //只读
        DefaultTransactionAttribute txAttrReadonly = new DefaultTransactionAttribute();
        txAttrReadonly.setReadOnly(true);

        //PROPAGATION_REQUIRES_NEW
        //新建事务，如果当前存在事务，把当前事务挂起。
        DefaultTransactionAttribute txAttrRequiresNew = new DefaultTransactionAttribute();
        txAttrRequiresNew.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        //针对service中的方法配置事务传播行为
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        //针对查询方法配置只读
        source.addTransactionalMethod("find*", txAttrReadonly);
        source.addTransactionalMethod("select*", txAttrReadonly);
        source.addTransactionalMethod("login*", txAttrReadonly);
        source.addTransactionalMethod("get*", txAttrReadonly);
        source.addTransactionalMethod("fetch*", txAttrReadonly);
        source.addTransactionalMethod("show*", txAttrReadonly);

        //针对增删改方法配置PROPAGATION_REQUIRED
        //如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
        source.addTransactionalMethod("reply*", txAttrRequired);
        source.addTransactionalMethod("add*", txAttrRequired);
        source.addTransactionalMethod("edit*", txAttrRequired);
        source.addTransactionalMethod("save*", txAttrRequired);
        source.addTransactionalMethod("del*", txAttrRequired);
        source.addTransactionalMethod("remove*", txAttrRequired);
        source.addTransactionalMethod("update*", txAttrRequired);
        source.addTransactionalMethod("exec*", txAttrRequired);
        source.addTransactionalMethod("set*", txAttrRequired);

        //针对其他没有配置到的方法配置PROPAGATION_REQUIRED
        //如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
        source.addTransactionalMethod("*", txAttrRequired);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
