package security31.service;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;

public class MyJdbcAuthorizationCodeServices extends JdbcAuthorizationCodeServices {

    public MyJdbcAuthorizationCodeServices(DataSource dataSource) {
        super(dataSource);
    }



    private RandomValueStringGenerator generator = new RandomValueStringGenerator(12);


    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        store(code, authentication);
        return code;
    }
}
