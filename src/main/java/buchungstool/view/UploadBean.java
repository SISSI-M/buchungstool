package buchungstool.view;

import buchungstool.business.importer.ComponentEventConverter;
import buchungstool.business.importer.IcsFileReadException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean
@SessionScoped
public class UploadBean {

    private Part part;

    @Inject
    ComponentEventConverter componentEventConverter;

    public String upload() throws IOException {
        InputStream inputStream = part.getInputStream();
        String filename = getFilename(part);
        FileOutputStream outputStream = new FileOutputStream(filename);

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        System.out.println("file uploaded: " + filename);

        try {
            componentEventConverter.convert(new File(filename)).setFileName(filename);
            System.out.println("Converter finished");
        } catch (IcsFileReadException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                               .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}  