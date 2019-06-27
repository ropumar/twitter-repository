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

            LocalDate dt = LocalDate.fromYearMonthDay(2019,3,26);
            Tweet tw = new Tweet (dt, "jair","bolsonaro",true, 12.0,13.0,true,"Rod1",14);
            br.inserttweet(tw);

            LocalDate dt1 = LocalDate.fromYearMonthDay(2019,3,25);
            Tweet tw1 = new Tweet (dt1, "2","-2",true, 2,2,true,"Rod2",2);
            br.inserttweet(tw1);

            LocalDate dt2 = LocalDate.fromYearMonthDay(2019,3,24);
            Tweet tw2 = new Tweet (dt2, "3","-3",true, 3,3,true,"Rod3",3);
            br.inserttweet(tw2);

            LocalDate dt3 = LocalDate.fromYearMonthDay(2019,3,23);
            Tweet tw3 = new Tweet (dt3, "4","-4",true, 4,4,true,"Rod4",4);
            br.inserttweet(tw3);

            LocalDate dt4 = LocalDate.fromYearMonthDay(2019,3,22);
            Tweet tw4 = new Tweet (dt4, "Jair","Bolsonaro",true, 12,34,true,"Rod5",21);
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
