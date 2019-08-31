package com.github.anricx.security.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by dengt on 2019/8/30
 */
public class PBPasswordEncoder implements PasswordEncoder {

    private static final int IVS[][] = new int[][] {
            {-1,1,-2,2,-3,3,-4,4,-5,5,-6,6,-7,7,-8,8},
            {1,-1,2,-2,3,-3,4,-4,5,-5,6,-6,7,-7,8,-8},
            {8,-8,7,-7,6,-6,5,-5,4,-4,3,-3,2,-2,1,-1},
            {-8,8,-7,7,-6,6,-5,5,-4,4,-3,3,-2,2,-1,1},
            {1,-1,3,-3,5,-5,7,-7,2,-2,4,-4,6,-6,8,-8},
            {-1,1,-3,3,-5,5,-7,7,-2,2,-4,4,-6,6,-8,8},
            {2,-2,4,-4,6,-6,8,-8,1,-1,3,-3,5,-5,7,-7},
            {-2,2,-4,4,-6,6,-8,8,-1,1,-3,3,-5,5,-7,7},
            {1,3,5,7,2,4,6,8,-1,-3,-5,-7,-2,-4,-6,-8},
            {-1,-3,-5,-7,-2,-4,-6,-8,1,3,5,7,2,4,6,8}
    };

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String encode(CharSequence charSequence) {
        if ("userNotFoundPassword".equals(charSequence.toString())) {
            return charSequence.toString();
        }
        PBPassWrapper wrapper = PBPassWrapper.unwrap(charSequence.toString());
        return encode(wrapper.getPlain(), wrapper.getSeed());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        PBPassWrapper wrapper = PBPassWrapper.unwrap(charSequence.toString());
        String expect = encode(wrapper.getPlain(), wrapper.getSeed());
        return expect.equals(s);
    }

    protected static String encode(String plain, String seed) {
        return encode(plain, seed, 0);
    }

    /**
     * 加密函数
     * @param plain 密码明文
     * @param seed 用户编号
     * @param mode 默认0
     * @return
     */
    protected static String encode(String plain, String seed, int mode) {
        int ys = (1 * seed.charAt(0) + 2 * seed.charAt(1) + 3 * seed.charAt(2) + 4 * seed.charAt(3)) % 10;

        StringBuffer stringBuffer = new StringBuffer();

        if (mode == 0) {
            char[] chs = plain.toCharArray();
            int len = chs.length;
            int[] iv = IVS[ys];
            for (int i = 0; i < len; i++) {
                char ch = (char) (plain.charAt(i) + iv[i]);
                stringBuffer.append(ch);
            }
        } else {
            char[] chs = plain.toCharArray();
            int len = chs.length;
            int[] iv = IVS[ys];
            for (int i = 0; i < len; i++) {
                char ch = (char) (plain.charAt(i) - iv[i]);
                stringBuffer.append(ch);
            }
        }
        return stringBuffer.toString();
    }

}
