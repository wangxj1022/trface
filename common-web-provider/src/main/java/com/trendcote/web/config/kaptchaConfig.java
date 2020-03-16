package com.trendcote.web.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**验证码配置
 * @author 莹
 * @date 2018/6/4
 */
@Configuration
public class kaptchaConfig {
    @Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "blue");
        properties.setProperty("kaptcha.border.thickness","2");
        properties.setProperty("kaptcha.image.width", "80");
        properties.setProperty("kaptcha.image.height", "32");
        properties.setProperty("kaptcha.producer.impl","com.google.code.kaptcha.impl.DefaultKaptcha");
        properties.setProperty("kaptcha.textproducer.impl","com.google.code.kaptcha.text.impl.DefaultTextCreator");
        properties.setProperty("kaptcha.textproducer.char.string","abcde0123456789hjkxvzgfynmnpwx");
        properties.setProperty("kaptcha.textproducer.char.length","5");
        properties.setProperty("kaptcha.textproducer.font.names","Arial");
        properties.setProperty("kaptcha.textproducer.font.size","20");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty("kaptcha.background.impl","com.google.code.kaptcha.impl.DefaultBackground");
        properties.setProperty("kaptcha.background.clear.to","white");
        properties.setProperty("kaptcha.word.impl","com.google.code.kaptcha.text.impl.DefaultWordRenderer");
        properties.setProperty("kaptcha.session.key","KAPTCHA_SESSION_KEY");
        properties.setProperty("kaptcha.session.date","KAPTCHA_SESSION_DATE");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

