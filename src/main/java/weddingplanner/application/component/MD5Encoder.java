package weddingplanner.application.component;

import org.slf4j.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Create by Daniel Drzazga on 20.10.2020
 **/

@Component
public class MD5Encoder {

    private static final Logger LOG = LoggerFactory.getLogger(MD5Encoder.class);
    private static final String ALGORITHM = "MD5";
    private static final int HEX_BASE = 16;

    public String getMD5Hash(String password){
        String md5Hash = null;

        if(password != null){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                messageDigest.update(password.getBytes(),0,password.length());
                md5Hash = new BigInteger(1,messageDigest.digest()).toString(HEX_BASE);
            } catch (Exception e){
                LOG.error(e.getMessage(), e);
            }
        }

        return md5Hash;
    }

}
