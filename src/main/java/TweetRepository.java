import com.datastax.driver.core.*;
import java.util.List;
import java.util.ArrayList;

public class TweetRepository {

    private static final String TABLE_NAME = "tweets";
    private static final String TABLE_NAME_BY_USER = "tweetsByUser";

    private Session session;

    public TweetRepository(Session session) {
        this.session = session;
    }
    /**
     * Creates the tweet table.
     */
    public void createTable() {
        System.out.println("createTable - init");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("createdDate date,")
                .append("text text,")
                .append("source text,")
                .append("isTruncated boolean,")
                .append("latitude double,")
                .append("longitude double,")
                .append("isFavorited boolean,")
                .append("user text,")
                .append("contributors bigint);");

        final String query = sb.toString();
        System.out.println("createTable - command: " + query);
        session.execute(query);
        System.out.println("createTable end");
    }

    /**
     * Creates the tweet table by Tittle
     */
    public void createTableByUser() {
        System.out.println("createTableByUser - init");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_BY_USER)
                .append("(")
                .append("id uuid, ")
                .append("createdDate date,")
                .append("text text,")
                .append("source text,")
                .append("isTruncated boolean,")
                .append("latitude double,")
                .append("longitude double,")
                .append("isFavorited boolean,")
                .append("user text,")
                .append("contributors bigint,")
                .append("PRIMARY KEY (user, id));");

        final String query = sb.toString();
        System.out.println("createTableByUser - command: " + query);
        session.execute(query);
        System.out.println("createTableByUser end");
    }

    /**
     * Insert a row in the table tweet.
     *
     * @param tw
     */
    public void insertTweet(Tweet tw) {
        System.out.println("inserttweet init");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(id,createdDate, text, source, isTruncated, latitude,longitude, isFavorited, user, contributors) ")
                .append("VALUES ( ")
                .append(tw.getUUID()).append(", '")
                .append(tw.getDate()).append("', '")
                .append(tw.getText()).append("', '")
                .append(tw.getSource()).append("', ")
                .append(tw.getIsTruncated()).append(", ")
                .append(tw.getLatitude()).append(", ")
                .append(tw.getLongitude()).append(", ")
                .append(tw.getIsFavorited()).append(", '")
                .append(tw.getUser()).append("', ")
                .append(tw.getContributors()).append(");");
        final String query = sb.toString();
        System.out.println("inserttweet - command: " + query);
        session.execute(query);
        System.out.println("inserttweet end");
    }

    /**
     * Insert a row in the table tweet.
     *
     * @param tw
     */
    public void insertTweetByUser(Tweet tw) {
        System.out.println("insertTweetByUser init");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME_BY_USER)
                .append("(id,createdDate, text, source, isTruncated, latitude,longitude, isFavorited, user, contributors) ")
                .append("VALUES ( ")
                .append(tw.getUUID()).append(", '")
                .append(tw.getDate()).append("', '")
                .append(tw.getText()).append("', '")
                .append(tw.getSource()).append("', ")
                .append(tw.getIsTruncated()).append(", ")
                .append(tw.getLatitude()).append(", ")
                .append(tw.getLongitude()).append(", ")
                .append(tw.getIsFavorited()).append(", '")
                .append(tw.getUser()).append("', ")
                .append(tw.getContributors()).append(");");
        final String query = sb.toString();
        System.out.println("insertTweetByUser - command: " + query);
        session.execute(query);
        System.out.println("insertTweetByUser end");
    }


    /**
     * Select all tweet from tweet
     *
     * @return
     */
    public List<Tweet> selectAll() {

        System.out.println("selectAll - init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        System.out.println("selectAll - command: " + query);

        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row r : rs) {
            Tweet tw = new Tweet(r.getDate("createdDate"),r.getString("text"), r.getString("source"), r.getBool("isTruncated"),r.getDouble("latitude"),r.getDouble("longitude"),r.getBool("isFavorited"),r.getString("user"),r.getLong("contributors"));
            tweets.add(tw);
        }

        System.out.println("selectAll - end");
        return tweets;
    }

    /**
     * Select all tweet from tweet by Userrod1
     *
     * @return
     */
    public List<Tweet> selectAllByUser() {
        System.out.println("selectAllByUser - init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_USER);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);
        System.out.println("selectAllByUser - command: " + query);
        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row r : rs) {
            Tweet tw = new Tweet(r.getDate("createdDate"),r.getString("text"), r.getString("source"), r.getBool("isTruncated"),r.getDouble("latitude"),r.getDouble("longitude"),r.getBool("isFavorited"),r.getString("user"),r.getLong("contributors"));
            tweets.add(tw);
        }
        System.out.println("selectAllByUser - end");
        return tweets;
    }


    /**
     * Delete a tweet by User.
     */
    public void deleteTweetByUser(String user) {
        System.out.println("deleteTweetByUser  - init");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME_BY_USER).append(" WHERE user = '").append(user).append("';");

        final String query = sb.toString();
        System.out.println("deleteTweetByUser - command: " + query);
        session.execute(query);
        System.out.println("deleteTweetByUser end - user: "+ user);
    }

    /**
     * Delete a tweet.
     */
    public void deleteTweet(Tweet tw) {
        System.out.println("deletetweet  - init");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME).append(" WHERE id = ").append(tw.getUUID()).append(";");

        final String query = sb.toString();
        System.out.println("deletetweet - command: " + query);
        session.execute(query);
        System.out.println("deletetweet - end");
    }

    /**
     * Delete table.
     *
     * @param tableName the name of the table to delete.
     */
    public void deleteTable(String tableName) {
        System.out.println("deleteTable - init");
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);

        final String query = sb.toString();
        System.out.println("deleteTable - command: " + query);
        session.execute(query);
        System.out.println("Delete table "+ tableName + "end");
    }

    public Tweet selectByUser(String user) {
        System.out.println("selectByUser - ini user: "+ user );
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_USER).append(" WHERE user = '").append(user).append("';");

        final String query = sb.toString();

        ResultSet rs = session.execute(query);
        System.out.println("selectByUser - command: " + query);
        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row r : rs) {
            Tweet tw = new Tweet(r.getDate("createdDate"),r.getString("text"), r.getString("source"), r.getBool("isTruncated"),r.getDouble("latitude"),r.getDouble("longitude"),r.getBool("isFavorited"),r.getString("user"),r.getLong("contributors"));
            tweets.add(tw);
        }
        System.out.println("selectByUser end user: " + user );
        return tweets.get(0);
    }
}