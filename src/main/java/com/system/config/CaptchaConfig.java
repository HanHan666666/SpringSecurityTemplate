package com.system.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        //是否有边框，默认为true，自己可以设置yes，no
        //If there is a border, the default is true,  can set yes, no
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        // 验证码文本字符颜色 默认为Color.BLACK
        // The default Color for CAPTCHA text characters is color.black
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,
                "blue");
        // 验证码文本字符间距 默认为2
        // The default spacing for CAPTCHA text characters is 2
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,
                "4");
        // 验证码图片高度 默认为50
        // By default, the height of the CAPTCHA image is 50
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        // 验证码图片宽度 默认为200
        // By default, the CAPTCHA image width is 200
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "120");
        // 验证码文本字符大小 默认为40
        // The default CAPTCHA text character size is 40
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,
                "30");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
