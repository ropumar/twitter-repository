import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.List;
import java.util.ArrayList;

public class TweetRepository {

    private static final String TABLE_NAME = "tweets";

    private Session session;

    public TweetRepository(Session session) {
        this.session = session;
    }

    /**
     * Creates the tweet table.
     */
    public void createTable() {
        System.out.println("createTable init");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id UUID PRIMARY KEY, ")
                .append("name text,")
                .append("message text,")
                .append("date date);");

        final String query = sb.toString();
        session.execute(query);
        System.out.println("createTable end");
    }

    /**
     * Insert a row in the table tweet.
     *
     * @param tw
     */
    public void inserttweet(Tweet tw) {
        System.out.println("inserttweet init");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(id,name, message, date) ").append("VALUES (")
                .append(tw.getUUID()).append(", '")
                .append(tw.getName()).append("', '")
                .append(tw.getMessage()).append("', '")
                .append(tw.getDate()).append("');");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("inserttweet end");
    }


    /**
     * Select all tweet from tweet
     *
     * @return
     */
    public List<Tweet> selectAll() {
        System.out.println("selectAll init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row r : rs) {
            Tweet tw = new Tweet(r.getString("name"), r.getString("message"), r.getDate("date"));
            tweets.add(tw);
        }
        System.out.println("selectAll end");
        return tweets;
    }


    /**
     * Delete a tweet by title.
     */
    public void deletetweet(Tweet tw) {
        System.out.println("deletetweet init");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME).append(" WHERE id = '").append(tw.getUUID()).append("';");

        final String query = sb.toString();
        session.execute(query);
        System.out.println("deletetweet end");
    }

    /**
     * Delete table.
     *
     * @param tableName the name of the table to delete.
     */
    public void deleteTable(String tableName) {
        System.out.println("deleteTable init");
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);

        final String query = sb.toString();
        session.execute(query);
        System.out.println("Delete table "+ tableName + "end");
    }
}