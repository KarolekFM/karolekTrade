package net.karolek.trade.utils;

import java.io.*;

public final class IoUtil {

    private IoUtil() {
    }

    public static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createFile(final File file) {
        try {
            if (file.exists()) {
                return;
            }
            file.getAbsoluteFile().getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
