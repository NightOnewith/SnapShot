package com.cyzn.yzj.snapshot.model.bean;

import java.util.List;

/**
 * @author YZJ
 * @description 相机实体类
 * @date 2018/10/31 0031
 */
public class CameraBean {

    /**
     * meta : {"msg":"操作成功","code":200,"success":true,"timestamp":"2018-10-31T01:26:56.315+0000"}
     * data : {"cameraPageList":{"total":698,"size":10,"current":1,"records":[{"id":502,"name":"test502","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":503,"name":"test503","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":504,"name":"test504","cameraNo":"2","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":505,"name":"test505","cameraNo":"3","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":506,"name":"test506","cameraNo":"4","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":507,"name":"test507","cameraNo":"5","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":508,"name":"test508","cameraNo":"6","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":509,"name":"test509","cameraNo":"7","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":510,"name":"test510","cameraNo":"8","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":511,"name":"test511","cameraNo":"9","connectionType":1,"rtspUrl":"www.baidu.com"}],"pages":70}}
     */

    private MetaBean meta;
    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * msg : 操作成功
         * code : 200
         * success : true
         * timestamp : 2018-10-31T01:26:56.315+0000
         */

        private String msg;
        private int code;
        private boolean success;
        private String timestamp;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "MetaBean{" +
                    "msg='" + msg + '\'' +
                    ", code=" + code +
                    ", success=" + success +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }

    public static class DataBean {
        /**
         * cameraPageList : {"total":698,"size":10,"current":1,"records":[{"id":502,"name":"test502","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":503,"name":"test503","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":504,"name":"test504","cameraNo":"2","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":505,"name":"test505","cameraNo":"3","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":506,"name":"test506","cameraNo":"4","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":507,"name":"test507","cameraNo":"5","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":508,"name":"test508","cameraNo":"6","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":509,"name":"test509","cameraNo":"7","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":510,"name":"test510","cameraNo":"8","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":511,"name":"test511","cameraNo":"9","connectionType":1,"rtspUrl":"www.baidu.com"}],"pages":70}
         */

        private CameraPageListBean cameraPageList;

        public CameraPageListBean getCameraPageList() {
            return cameraPageList;
        }

        public void setCameraPageList(CameraPageListBean cameraPageList) {
            this.cameraPageList = cameraPageList;
        }

        public static class CameraPageListBean {
            /**
             * total : 698
             * size : 10
             * current : 1
             * records : [{"id":502,"name":"test502","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":503,"name":"test503","cameraNo":"1","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":504,"name":"test504","cameraNo":"2","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":505,"name":"test505","cameraNo":"3","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":506,"name":"test506","cameraNo":"4","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":507,"name":"test507","cameraNo":"5","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":508,"name":"test508","cameraNo":"6","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":509,"name":"test509","cameraNo":"7","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":510,"name":"test510","cameraNo":"8","connectionType":1,"rtspUrl":"www.baidu.com"},{"id":511,"name":"test511","cameraNo":"9","connectionType":1,"rtspUrl":"www.baidu.com"}]
             * pages : 70
             */

            private int total;
            private int size;
            private int current;
            private int pages;
            private List<RecordsBean> records;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public List<RecordsBean> getRecords() {
                return records;
            }

            public void setRecords(List<RecordsBean> records) {
                this.records = records;
            }

            public static class RecordsBean {
                /**
                 * id : 502
                 * name : test502
                 * cameraNo : 1
                 * connectionType : 1
                 * rtspUrl : www.baidu.com
                 */

                private int id;
                private String name;
                private String cameraNo;
                private int connectionType;
                private String rtspUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCameraNo() {
                    return cameraNo;
                }

                public void setCameraNo(String cameraNo) {
                    this.cameraNo = cameraNo;
                }

                public int getConnectionType() {
                    return connectionType;
                }

                public void setConnectionType(int connectionType) {
                    this.connectionType = connectionType;
                }

                public String getRtspUrl() {
                    return rtspUrl;
                }

                public void setRtspUrl(String rtspUrl) {
                    this.rtspUrl = rtspUrl;
                }

                @Override
                public String toString() {
                    return "RecordsBean{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", cameraNo='" + cameraNo + '\'' +
                            ", connectionType=" + connectionType +
                            ", rtspUrl='" + rtspUrl + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "CameraPageListBean{" +
                        "total=" + total +
                        ", size=" + size +
                        ", current=" + current +
                        ", pages=" + pages +
                        ", records=" + records +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cameraPageList=" + cameraPageList.toString() +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CameraBean{" +
                "meta=" + meta.toString() +
                ", data=" + data.toString() +
                '}';
    }
}
