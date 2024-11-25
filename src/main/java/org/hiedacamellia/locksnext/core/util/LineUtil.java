package org.hiedacamellia.locksnext.core.util;

public class LineUtil {

    // x 为与正确角度相差的角度
    // 返回值为可旋转的角度
    // y = 0.001 * x^3 - 0.01 * x^2 + x
    public static double getAvailable(double x) {
        x = 100 - x;
        return (0.001 * x * x * x - 0.01 * x * x + x) * 0.2;
    }
}
