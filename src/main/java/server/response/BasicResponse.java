package main.java.server.response;

import main.java.server.request.Request;

import java.io.File;
import java.io.IOException;

public class BasicResponse implements Response{

    private String headers;
    private String body;
    private Status status = new Status();
    private File requestedFile;

    @Override
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getHeaders() {
        return headers;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getMessage() {
        return headers + "\n\n" + body + "\r\n";
    }

    @Override
    public void createResponse(Request request, String directory) {
        String requestSL = getStatusLine(request.getHeaders());
        String responseSL = createResponseStatusLine(requestSL, directory);

        setHeaders(responseSL);
        setBody("file1 contents");
    }

    private String getStatusLine(String requestHeaders) {
        String[] splitHeaders = requestHeaders.split("\n");
        return splitHeaders[0];
    }

    private String createResponseStatusLine(String requestSL, String directory) {
        String[] requestStatusFields = requestSL.split(" ");
        String method = requestStatusFields[0];
        String requestURI = requestStatusFields[1];
        String httpVersion = requestStatusFields[2];

        String statusCode;
        try {
            statusCode = generateStatusCode(method, requestURI, directory);
        }
        catch (IOException e) {
            statusCode = "404";
        }
        String reasonPhrase = status.getStatusCode().get(statusCode).toString();

        String responseSL = httpVersion + " " + statusCode + " " + reasonPhrase;

        return responseSL;
    }

    private String generateStatusCode(String method, String requestURI, String directory) throws IOException {
        File file = new File(directory);
        File[] contents = file.listFiles();
        requestedFile = null;
        for (File f : contents) {
            //System.out.println("absolute path: " + f.getAbsolutePath());
            //System.out.println("name: " + f.getName());
            if (requestURI.endsWith(f.getName()) || f.getAbsolutePath().contains(requestURI)) {
                requestedFile = f;
                break;
            }
        }

        if (requestedFile == null) {
            return "404";
        }
        else {
            return "200";
        }
    }
}
