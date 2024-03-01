package testJava.jd_core;

import org.jd.core.v1.api.loader.LoaderException;
import testJava.testBot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Loader implements org.jd.core.v1.api.loader.Loader {

    @Override
    public byte[] load(String internalName) throws LoaderException {

        InputStream is = this.getClass().getResourceAsStream("/"+internalName + ".class");

        if (is == null) {
            return null;
        } else {
            try (InputStream in = is; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int read = in.read(buffer);

                while (read > 0) {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }

                return out.toByteArray();
            } catch (IOException e) {
                throw new LoaderException(e);
            }
        }
    }

    @Override
    public boolean canLoad(String internalName) {
        return this.getClass().getResource("/"+internalName + ".class") != null;
    }

}
