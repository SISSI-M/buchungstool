package buchungstool.boundary;

import buchungstool.business.importer.ComponentEventConverter;
import buchungstool.business.importer.IcsFileReadException;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.io.File.createTempFile;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * Created by Alexander Bischof on 24.07.15.
 */
//@RolesAllowed("ADMIN")
@Path("/upload")
public class FileuploadEndpoint {

    static Logger LOGGER = Logger.getLogger(FileuploadEndpoint.class.getName());

    @Inject
    ComponentEventConverter componentEventConverter;

    @POST
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input) throws URISyntaxException {
        LOGGER.info(">>>> sit back - starting file upload...");

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileName = getFileName(inputPart.getHeaders());
                File file = createTempFile("buchungstool", ".ics");
                writeFile(bytes, file);
                file.deleteOnExit();

                componentEventConverter.convert(file).setFileName(fileName);
            } catch (IOException | IcsFileReadException e) {
                return Response.status(INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        }

        Response response = Response.seeOther(new URI("/../index.html")).build();
        return response;
    }

    private void writeFile(byte[] content, File file) throws IOException {
        String filename = file.getName();
        LOGGER.info(">>> writing " + content.length + " bytes to: " + filename);

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

        LOGGER.info(">>> writing complete: " + filename);
    }

    private String getFileName(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = sanitizeFilename(name[1]);
                return finalFileName;
            }
        }
        return "unknown";
    }

    private String sanitizeFilename(String s) {
        return s.trim().replaceAll("\"", "");
    }
}
