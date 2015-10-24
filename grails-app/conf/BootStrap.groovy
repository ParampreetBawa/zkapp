import com.zkapp.util.CuratorUtil
import com.zkapp.util.Util
import groovy.util.logging.Log4j

@Log4j
class BootStrap {


    def init = { servletContext ->
        Util.init()
    }
    def destroy = {
        CuratorUtil.client.close()
    }
}
