package com.funeral.upload.resolver;

import com.funeral.upload.entity.UploadState;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 记录上传进度的resolver
 * @author FuneralObjects
 * CreateTime 2018/12/13 9:02 PM
 */
public class UploadStateMultipartResolver extends CommonsMultipartResolver {
    public static final String SESSION_KEY = "upload-state";

    public UploadStateMultipartResolver(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        //只要访问Multipart上传操作，就在Session中赋予一个上传状态
        UploadState state = new UploadState();
        request.getSession().setAttribute(UploadStateMultipartResolver.SESSION_KEY,state);
        fileUpload.setProgressListener((pBytesRead, pContentLength, pItems) -> {
            state.setPercent(BigDecimal.valueOf(pBytesRead).divide(BigDecimal.valueOf(pContentLength),5,BigDecimal.ROUND_HALF_UP));
        });
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        }
        catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        }
        catch (FileUploadBase.FileSizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getFileSizeMax(), ex);
        }
        catch (FileUploadException ex) {
            throw new MultipartException("Failed to parse multipart servlet request", ex);
        }
    }
}
