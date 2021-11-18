package com.ubmarketplace.app.repository;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ImgBBRepository {
    private ImageBBData data;
    private boolean success;
    private String status;

    @Data
    public static class ImageBBData {
        @JsonAlias("display_url")
        private String displayUrl;
        private String size;
        @JsonAlias("delete_url")
        private String deleteUrl;
        private String expiration;
        private String id;
        private String time;
        private String title;
        @JsonAlias("url_viewer")
        private String urlViewer;
        private String url;
        private ImageBBImageInfo thumb;
        private ImageBBImageInfo medium;
        private ImageBBImageInfo image;
    }

    @Data
    public static class ImageBBImageInfo {
        private String filename;
        private String name;
        private String mime;
        private String extension;
        private String url;
    }

}
