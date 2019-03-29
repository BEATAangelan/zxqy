package com.example.dell.zxqy.goods.bean;

import java.util.List;

public class TalkBean {

    /**
     * result : [{"commodityId":10,"content":"","createTime":1551750153000,"headPic":"http://mobile.bwstudent.com/images/small/head_pic/2019-03-13/20190313150511.png","image":"http://mobile.bwstudent.com/images/small/comment_pic/2019-03-04/2064120190304194233.png","nickName":"哈哈","userId":79},{"commodityId":10,"content":"bug无止境","createTime":1551407274000,"headPic":"http://mobile.bwstudent.com/images/small/head_pic/2019-02-27/20190227164149.png","image":"","nickName":"哈哈哈","userId":169},{"commodityId":10,"content":"继续测","createTime":1551387455000,"headPic":"http://mobile.bwstudent.com/images/small/head_pic/2019-02-27/20190227164149.png","image":"http://mobile.bwstudent.com/images/small/comment_pic/2019-02-28/4395120190228145735.jpeg","nickName":"哈哈哈","userId":169}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 10
         * content :
         * createTime : 1551750153000
         * headPic : http://mobile.bwstudent.com/images/small/head_pic/2019-03-13/20190313150511.png
         * image : http://mobile.bwstudent.com/images/small/comment_pic/2019-03-04/2064120190304194233.png
         * nickName : 哈哈
         * userId : 79
         */

        private int commodityId;
        private String content;
        private long createTime;
        private String headPic;
        private String image;
        private String nickName;
        private int userId;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
