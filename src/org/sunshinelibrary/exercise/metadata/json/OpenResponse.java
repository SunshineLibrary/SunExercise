package org.sunshinelibrary.exercise.metadata.json;

/**
 * @author linuo
 * @version 1.0
 * @date 5/16/13
 */
public class OpenResponse extends JSONObject{
    public String status = "true";

    private static OpenResponse response = new OpenResponse();

    private OpenResponse setStatus(String s){
        status = s;
        return this;
    }

    public static final String SUCCESS = response.setStatus("success").toJsonString();
    public static final String NOT_PDF_READER_FOUND = response.setStatus("pdfReaderNotFound").toJsonString();
    public static final String FAILED = response.setStatus("failed").toJsonString();
    public static final String NOT_SUPPORTED = response.setStatus("NotSupported").toJsonString();
}
