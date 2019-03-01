package tech.soulcoder.kit.logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 以后学习一下 org.slf4j.MDC
 *
 * @author yunfeng.lu
 * @create 2019/3/1.
 */
@Component // 加入 Spring Bean 容器中
@Aspect
public class LogAopImp {

    private static final Logger logger = LoggerFactory.getLogger(LogAopImp.class);

    private final static Logger MONITOR_LOGGER = LoggerFactory.getLogger("LogMonitor");

    private final static String DEF_PROCESS_SUCCESS_RESULT = "T";

    private final static String DEF_PROCESS_SLOW_RESULT = "S";

    private final static String DEF_PROCESS_ERROR_RESULT = "E";

    private final static String DEF_SUCCESS_CODE = "9999";

    private final static String DEF_ERROR_CODE = "0000";

    private static String ip_address = null;


    static {
        try {
            ip_address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            logger.error("获取本地IP信息异常 请检查..");
        }
    }

    @Pointcut("@annotation(tech.soulcoder.kit.logger.LogAop)")
    public void logAopAspect() {
        logger.info("Pointcut");
    }

    @Before("logAopAspect() &&  ")
    public void fun(){
        logger.info("Before");

    }

    @Around("@annotation(logAop)")
    public Object printLog(ProceedingJoinPoint pjp, LogAop logAop) throws Throwable {

        Object result = null;
        LogAop.ParamPrintOption printOption = logAop.outParamPrint();
        long currentTime = System.currentTimeMillis();
        String inParam = Arrays.toString(pjp.getArgs());
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String processResult = DEF_PROCESS_SUCCESS_RESULT;
        String errorCode = DEF_SUCCESS_CODE;
        try {

            result = pjp.proceed();
            // 未配置情况 系统默认不打印 集合类型，其余都打印
            if (LogAop.ParamPrintOption.UNCONFIG.equals(printOption)) {
                if (result instanceof Collection) {
                    printOption = LogAop.ParamPrintOption.IGNORE;
                } else if (result != null && result.getClass().isArray()) {
                    printOption = LogAop.ParamPrintOption.IGNORE;
                } else {
                    printOption = LogAop.ParamPrintOption.PRINT;
                }
            }

            long lastTime = System.currentTimeMillis() - currentTime;

            if (lastTime >= 2000) {
                processResult = DEF_PROCESS_SLOW_RESULT;
                logger.info("{}.{}:该方法执行较慢请检查... 入参: {} \t出参: {} 响应时间:{}毫秒", className,
                    methodName, inParam,
                    printOption.equals(LogAop.ParamPrintOption.PRINT) ? result : "", lastTime);
            } else {
                logger.info("{}.{}: 入参: {} \t出参: {} 响应时间:{}毫秒", className,
                    methodName,
                    inParam,
                    printOption.equals(LogAop.ParamPrintOption.PRINT) ? result : "参数未配置打印", lastTime);
            }

            //{请求ip}~{类名}~{方法名}~{设备ID}~{IP地址}~{错误码}~{错误消息}~{执行结果}~{执行时间}
            MONITOR_LOGGER.info("{}~{}~{}~{}~{}~{}~{}", className, methodName,
                ip_address, errorCode, "执行成功", processResult, lastTime);

        } catch (Throwable throwable) {
            errorCode = DEF_ERROR_CODE;
            processResult = DEF_PROCESS_ERROR_RESULT;

            logger.error("{}.{}:{} 执行报错,入参: {}", className,
                methodName, inParam, throwable);

            MONITOR_LOGGER.info("{}~{}~{}~{}~{}~{}~{}", className, methodName,
                ip_address, errorCode, throwable.getMessage(),
                processResult, System.currentTimeMillis() - currentTime);

            throw throwable;
        }

        return result;
    }
}
