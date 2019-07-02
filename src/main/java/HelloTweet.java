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
            System.out.println("Create table tweet simple table");

            LocalDate dt = LocalDate.fromYearMonthDay(2019,3,26);
            Tweet tw = new Tweet (dt, "1","-2",true, 12.0,13.0,true,"Rod1",14);
            br.insertTweet(tw);

            LocalDate dt1 = LocalDate.fromYearMonthDay(2019,3,25);
            Tweet tw1 = new Tweet (dt1, "2","-2",true, 2,2,true,"Rod2",2);
            br.insertTweet(tw1);

            LocalDate dt2 = LocalDate.fromYearMonthDay(2019,3,24);
            Tweet tw2 = new Tweet (dt2, "3","-3",true, 3,3,true,"Rod3",3);
            br.insertTweet(tw2);

            LocalDate dt3 = LocalDate.fromYearMonthDay(2019,3,23);
            Tweet tw3 = new Tweet (dt3, "4","-4",true, 4,4,true,"Rod4",4);
            br.insertTweet(tw3);

            LocalDate dt4 = LocalDate.fromYearMonthDay(2019,3,22);
            Tweet tw4 = new Tweet (dt4, "5","-5",true, 12,34,true,"Rod5",21);
            br.insertTweet(tw4);
            System.out.println("Insert tweets end simple table");
            System.out.println("Create table tweet by user table");
            br.createTableByUser();
            br.insertTweetByUser(tw);
            br.insertTweetByUser(tw1);
            br.insertTweetByUser(tw2);
            br.insertTweetByUser(tw3);
            br.insertTweetByUser(tw4);

            br.selectAll().forEach(o -> System.out.println(o.getUUID() + " "+o.getDate()+" "+o.getText()+"" +o.getSource()+" "+o.getIsTruncated()+" "+ o.getLatitude()+" "+ o.getLongitude()+" "+ o.getIsFavorited()+" "+ o.getUser()+" "+ o.getContributors()));
            System.out.println("normal table printed");

            br.selectAllByUser().forEach(o -> System.out.println(o.getUUID() + " "+o.getDate()+" "+o.getText()+"" +o.getSource()+" "+o.getIsTruncated()+" "+ o.getLatitude()+" "+ o.getLongitude()+" "+ o.getIsFavorited()+" "+ o.getUser()+" "+ o.getContributors()));
            System.out.println("by user printed");

            Tweet rod1 = br.selectByUser("Rod1");
            System.out.println("Printing Rod1");
            System.out.println(rod1.getUUID() + " "+rod1.getDate()+" "+rod1.getText()+"" +rod1.getSource()+" "+rod1.getIsTruncated()+" "+ rod1.getLatitude()+" "+ rod1.getLongitude()+" "+ rod1.getIsFavorited()+" "+ rod1.getUser()+" "+ rod1.getContributors());
            br.deleteTweetByUser("Rod1");
            br.selectAllByUser().forEach(o -> System.out.println(o.getUUID() + " "+o.getDate()+" "+o.getText()+"" +o.getSource()+" "+o.getIsTruncated()+" "+ o.getLatitude()+" "+ o.getLongitude()+" "+ o.getIsFavorited()+" "+ o.getUser()+" "+ o.getContributors()));
            System.out.println("by user printed");
            br.deleteTweet(tw4);
            br.selectAll().forEach(o -> System.out.println(o.getUUID() + " "+o.getDate()+" "+o.getText()+"" +o.getSource()+" "+o.getIsTruncated()+" "+ o.getLatitude()+" "+ o.getLongitude()+" "+ o.getIsFavorited()+" "+ o.getUser()+" "+ o.getContributors()));
            System.out.println("normal table printed");
            br.deleteTable("tweets");
            br.deleteTable("tweetsByUser");
            sr.deleteKeyspace("tweet");
            System.out.println("Delete keyspace tweet");

        }finally{
            if (cluster != null) cluster.close();
        }
    }
}
