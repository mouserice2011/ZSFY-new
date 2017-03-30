package cn.czfy.zsfy.db.test;

import android.test.AndroidTestCase;

import cn.czfy.zsfy.db.dao.ArticleDao;
import cn.czfy.zsfy.db.entity.Article;
import cn.czfy.zsfy.db.entity.Student;
import cn.czfy.zsfy.db.entity.User;
import com.j256.ormlite.dao.Dao;
import cn.czfy.zsfy.db.DatabaseHelper;
import cn.czfy.zsfy.db.dao.UserDao;

import java.sql.SQLException;
import java.util.List;

public class OrmLiteDbTest extends AndroidTestCase {
    public void testAddArticle() {
        User u = new User();
        u.setName("xxx");
        new UserDao(getContext()).add(u);
        Article article = new Article();
        article.setTitle("ORMLite");
        article.setUser(u);
        new ArticleDao(getContext()).add(article);

    }

    public void testGetArticleById() {
        Article article = new ArticleDao(getContext()).get(1);
    }

    public void testGetArticleWithUser() {

        Article article = new ArticleDao(getContext()).getArticleWithUser(1);
//        L.e(article.getUser() + " , " + article.getTitle());
    }

    public void testListArticlesByUserId() {

        List<Article> articles = new ArticleDao(getContext()).listByUserId(1);
//        L.e(articles.toString());
    }

    public void testGetUserById() {
        User user = new UserDao(getContext()).get(1);
//        L.e(user.getName());
        if (user.getArticles() != null)
            for (Article article : user.getArticles()) {
//                L.e(article.toString());
            }
    }

    public void testAddStudent() throws SQLException {
        Dao dao = DatabaseHelper.getHelper(getContext()).getDao(Student.class);
        Student student = new Student();
        student.setDao(dao);
        student.setName("xxx");
        student.create();
    }


}
