package tech.soulcoder.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author as
 * @create 2019-03-19 21:01
 * @desc 登录过滤器
 */
@Component
public class LoginFilter  extends ZuulFilter {

    /**
     * 过滤器类型，前置过滤器
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }
    /**
     * 过滤器顺序，越小越先执行
     */
    @Override
    public int filterOrder() {
        return 4;
    }
    /**
     * 过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //System.out.println(request.getRequestURI()); ///apigateway/product/api/v1/product/list
        //System.out.println(request.getRequestURL()); //http://localhost:9000/apigateway/product/api/v1/product/list

        //ACL

        if ("/apigateway/order/api/v1/product/order/save".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }else if ("/apigateway/order/api/v1/product/order/list".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }else if ("/apigateway/order/api/v1/product/order/find".equalsIgnoreCase(request.getRequestURI())){
            return true;
        }

        return false;
    }
    /**
     * 业务逻辑
     */
    @Override
    public Object run() throws ZuulException {
        //JWT
        RequestContext requestContext =  RequestContext.getCurrentContext();
        HttpServletRequest  request = requestContext.getRequest();
        System.out.println("拦截了此路径："+request.getRequestURI());
        //token对象
        String token = request.getHeader("token");

        if(StringUtils.isBlank((token))){
            token  = request.getParameter("token");
        }


        //登录校验逻辑  根据公司情况自定义 JWT
        if (StringUtils.isBlank(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
