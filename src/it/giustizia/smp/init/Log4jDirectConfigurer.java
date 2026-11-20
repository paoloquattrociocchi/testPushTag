package it.giustizia.smp.init;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Log4jConfigurer;

public class Log4jDirectConfigurer implements InitializingBean {

    private static final Log logger    = LogFactory.getLog(Log4jDirectConfigurer.class);
    private String           location;
    private String           logdirKey = "webapp.logDir";
    private long             refreshInterval;

    public void afterPropertiesSet() {

        if (location == null) {
            return;
        }
        try {
            if (refreshInterval == 0) {
                Log4jConfigurer.initLogging(location);
            } else {
                Log4jConfigurer.initLogging(location, refreshInterval);
            }
        } catch (FileNotFoundException e) {
            Log4jDirectConfigurer.logger.error(e);
        }
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void setLogdirKey(String logdirKey) {

        this.logdirKey = logdirKey;
    }

    public void setLogdirValue(String logdir) {

        String value = System.getProperty(logdirKey);
        if ((value != null) && !value.equals(logdir)) {
            throw new IllegalStateException("Logdir system property already set to different value: '" + logdirKey + "' = \"" + value + "\" instead of \"" + logdir + "\" - " + "Choose unique values for 'logdirKey' for every webapp!");
        }
        System.setProperty(logdirKey, logdir);
    }

    public void setRefreshInterval(long refreshInterval) {

        this.refreshInterval = refreshInterval;
    }
}
