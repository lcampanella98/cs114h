package exam1;

public class Main {
    public static void main(String[] args) {
        String[] strings = {
                "0010",
                "11",
                "0001000",
                "1001",
                "10a01",
                "aa",
                "10",
                "1",
                "0"
        };
        long end, start;
        int lim = 50000000;
        start = System.currentTimeMillis();
        for (int i = 0; i < lim; i++) {
            for (String s : strings) isInLang(s);
        }
        end = System.currentTimeMillis();
        System.out.println("mine took " + (end-start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < lim; i++) {
            for (String s : strings) isInLangR(s);
        }
        end = System.currentTimeMillis();
        System.out.println("rec took " + (end-start) + " ms");

    }

    public static boolean isInLangR(String s) {
        int len = s.length();
        if (len == 0) return true;
        if (len == 1) return isValidChar(s.charAt(0));
        return isValidChar(s.charAt(0))
                && s.charAt(0) == s.charAt(len - 1)
                && isInLangR(s.substring(1, len - 1));
    }

    public static boolean isInLang(String s) {
        return s.length() == 0 ? true : isInLang(s,0);
    }

    private static boolean isInLang(String s, int i) {
        int len = s.length();
        if (i >= len/2) return isValidChar(s.charAt(i));
        return isValidChar(s.charAt(i))
                && s.charAt(i) == s.charAt(len - i - 1)
                && isInLang(s, i + 1);
    }

    private static boolean isValidChar(char c) {return c=='0'||c=='1';}
}
