package tech.soulcoder.learn.shell;

import java.io.File;

import cn.hutool.core.util.RuntimeUtil;

/**
 * java 程序去执行命令行
 *
 * @author yunfeng.lu
 * @create 2019/3/23.
 */
public class ExecShellDemo {

    public static void main(String[] args) {
        Process res = RuntimeUtil.exec(null,
            new File("/Users/lucode/Desktop/gittest"),
            "git clone https://github.com/luyunfeng/soul.git");

        System.out.println(
            RuntimeUtil.getResultLines(res));
    }
}
