package com.funeral.upload.listener;

import com.funeral.upload.entity.UploadState;
import org.apache.commons.fileupload.ProgressListener;

import java.math.BigDecimal;

/**
 * @author FuneralObjects
 * CreateTime 2018/12/13 9:09 PM
 */
public class UploadProgressListener implements ProgressListener {
    public static final String SESSION_KEY = "upload-state";

    private UploadState state;
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        state.setPercent(BigDecimal.valueOf(pBytesRead/pContentLength));
    }

    public void setState(UploadState state) {
        this.state = state;
    }
}
