package com.shuai.okhttp.dormain;

public class CommentItem {
    private int articleId;
    private String commentContent;

    public CommentItem(int id,String content){
        articleId = id;
        commentContent = content;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
