package tech.soulcoder.thift.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yunfeng.lu
 * @create 2019/5/12.
 */
@RestController
@RequestMapping("/price/demo")
public class RPCThriftContoller {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RPCThriftClient rpcThriftClient;

    @RequestMapping(value = "/thriftTest", method = RequestMethod.GET)
    public String thriftTest(HttpServletRequest request, HttpServletResponse response) {
        try {
            rpcThriftClient.open();
            return rpcThriftClient.getRPCThriftService().getDate("soulcoder");
        } catch (Exception e) {
            logger.error("RPC调用失败", e);
            return "error";
        } finally {
            rpcThriftClient.close();
        }
    }
}
