package com.gdglc.hzqmes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * @author:ZhongGuoce
 * @date:2019-03-21
 * @time:22:09
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        return new JedisPool(jedisPoolConfig,
                jedisConnectionFactory.getHostName(), jedisConnectionFactory.getPort(),
                jedisConnectionFactory.getTimeout(), jedisConnectionFactory.getPassword());
    }



//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${spring.redis.port}")
//    private Integer port;
//    @Value("${spring.redis.timeout}")
//    private Integer timeout;
//
//    @Bean
//    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
//        return new JedisPool(jedisPoolConfig,
//                host, port,
//                timeout, StringUtils.isBlank(password)? null: password);
//    }
//
//    /**
//     * 连接池配置信息
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        //连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
//        jedisPoolConfig.setBlockWhenExhausted(true);
//        //逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
//        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
//
//        //最大空闲连接数, 默认8个
//        jedisPoolConfig.setMaxIdle(10000);
//
//        //最大连接数, 默认8个
//        jedisPoolConfig.setMaxTotal(10000);
//
//        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
//        jedisPoolConfig.setMaxWaitMillis(-1);
//
//        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
//        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);
//
//        //最小空闲连接数, 默认0
//        jedisPoolConfig.setMinIdle(0);
//
//        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
//        jedisPoolConfig.setNumTestsPerEvictionRun(3);
//
//        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
//        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);
//
//
//        //对拿到的connection进行validateObject校验
//        jedisPoolConfig.setTestOnBorrow(true);
//
//        //在进行returnObject对返回的connection进行validateObject校验
//        jedisPoolConfig.setTestOnReturn(true);
//
//        //定时对线程池中空闲的链接进行validateObject校验
//        jedisPoolConfig.setTestWhileIdle(true);
//        return jedisPoolConfig;
//    }
//
//    /**
//     * jedis连接工厂
//     * @param jedisPoolConfig
//     * @return
//     */
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        //设置redis服务器的host或者ip地址
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        //获得默认的连接池构造
//        //这里需要注意的是，redisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
//        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        //修改我们的连接池配置
//        jpcf.poolConfig(jedisPoolConfig);
//        //通过构造器来构造jedis客户端配置
//        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
//    }

    @Bean(name = "paramsKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
