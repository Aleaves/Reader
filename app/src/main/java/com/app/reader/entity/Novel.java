package com.app.reader.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by llb on 2016/3/10.
 */
public class Novel extends DataSupport{
    @Column(unique = true,defaultValue = "unknown")
    private String NovelName;

    @Column(nullable = false)
    private String NovelPath;

    private String NovelImage;

    public String getNovelName() {
        return NovelName;
    }

    public void setNovelName(String novelName) {
        NovelName = novelName;
    }

    public String getNovelPath() {
        return NovelPath;
    }

    public void setNovelPath(String novelPath) {
        NovelPath = novelPath;
    }

    public String getNovelImage() {
        return NovelImage;
    }

    public void setNovelImage(String novelImage) {
        NovelImage = novelImage;
    }
}
