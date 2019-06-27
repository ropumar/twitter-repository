import com.datastax.driver.core.*;
import com.datastax.driver.core.utils.UUIDs;

public class HelloTweet {
    public static void main (String args[]){
        System.out.println("Hello, Cassandra");
        Cluster cluster = null;
        try{
            cluster = Cluster.builder()
                    .addContactPoint("localhost")
                    .build();
            Session session = cluster.connect();
            ResultSet rs = session.execute ("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            KeyspaceRepository sr = new KeyspaceRepository(session);
            sr.createKeyspace("tweet","SimpleStrategy", 1);
            System.out.println("create repository");

            sr.useKeyspace("tweet");
            System.out.println("Using repository tweet");

            TweetRepository br = new TweetRepository(session);
            br.createTable();
            System.out.println("Create table tweet");

            System.out.println("Create Table tweet");
            LocalDate dt = LocalDate.fromYearMonthDay(2019,05,26);
            Tweet tw = new Tweet ("Bolsonaro", "Jair", dt);
            br.inserttweet(tw);

            LocalDate dt1 = LocalDate.fromYearMonthDay(2019,05,25);
            Tweet tw1 = new Tweet ("cara", "chato", dt);
            br.inserttweet(tw1);

            LocalDate dt2 = LocalDate.fromYearMonthDay(2019,05,24);
            Tweet tw2 = new Tweet ("eita", "ferro", dt);
            br.inserttweet(tw2);

            LocalDate dt3 = LocalDate.fromYearMonthDay(2019,05,23);
            Tweet tw3 = new Tweet ("sinistro", "caramba", dt);
            br.inserttweet(tw3);

            LocalDate dt4 = LocalDate.fromYearMonthDay(2019,05,22);
            Tweet tw4 = new Tweet ("house", "amazing", dt);
            br.inserttweet(tw4);
            System.out.println("Insert tweets");

            br.selectAll();


            br.deleteTable("tweets");

            sr.deleteKeyspace("tweet");
            System.out.println("Delete keyspace tweet");

        }finally{
            if (cluster != null) cluster.close();
        }
    }
}
