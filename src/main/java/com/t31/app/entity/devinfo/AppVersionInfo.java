package com.t31.app.entity.devinfo;

import com.t31.app.entity.AppVersionDTO;

public class AppVersionInfo extends AppVersionDTO {
        private String appName;
        private String versionNo;
        private String publishStatusName;

        public String getPublishStatusName() {
                return publishStatusName;
        }

        public void setPublishStatusName(String publishStatusName) {
                this.publishStatusName = publishStatusName;
        }

        public String getAppName() {
                return appName;
        }

        public void setAppName(String appName) {
                this.appName = appName;
        }

        @Override
        public String getVersionNo() {
                return versionNo;
        }

        @Override
        public void setVersionNo(String versionNo) {
                this.versionNo = versionNo;
        }


}
