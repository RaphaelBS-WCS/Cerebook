package com.wildcodeschool.cerebook.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildcodeschool.cerebook.entity.Post;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/*
 * Sample code to demonstrate the use of the v2 User Tweet timeline endpoint
 * */
public class tweeterApi {
    // To set your environment variables in your terminal run the following line:
    // export 'BEARER_TOKEN'='<your_bearer_token>'

    public static void main(String args[]) throws IOException, URISyntaxException {

        getPostFromTweet();
/*        final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAKVHVwEAAAAA7wffjcKZtfh4RfN8PwfWWrCYKNc%3DQIB3xW4obZ25aN4bnsAx83sIbRDdt1WSCWEh3SPPzuVnr6CdkL"; // System.getenv("BEARER_TOKEN");
          if (null != bearerToken) {
            // Replace with user ID below
            String response = getTweets("948687002668584960", bearerToken);
            System.out.println(response);
        } else {
            System.out.println("There was a problem getting your bearer token. Please make sure you set the BEARER_TOKEN environment variable");
        }*/
    }

    /*
     * This method calls the v2 User Tweet timeline endpoint by user ID
     * */
    private static String getTweets(String TweetUserId, String bearerToken) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", TweetUserId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

   public static ArrayList<Object> getPostFromTweet() throws IOException, URISyntaxException {

       String[] Wolverine = new String[]{"Wolverine","948687002668584960"};
       ArrayList<String[]> tweetUserIds = new ArrayList<String[]>();
       tweetUserIds.add(Wolverine);
       ArrayList<Object> tweetPosts = new ArrayList<Object>();

       for(String[] tweetUserId : tweetUserIds) {
           final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAKVHVwEAAAAA7wffjcKZtfh4RfN8PwfWWrCYKNc%3DQIB3xW4obZ25aN4bnsAx83sIbRDdt1WSCWEh3SPPzuVnr6CdkL";
           String response = getTweets(tweetUserId[1], bearerToken);
           JsonNode jsonNode = new ObjectMapper().readValue(response, JsonNode.class);
           JsonNode data = jsonNode.get("data");
           for (int i = 0; i < data.size(); i++) {
               JsonNode object = data.get(i);
               String username = tweetUserId[0];
               String created_at = object.get("created_at").asText();
               String content = object.get("text").asText();
               String stringTweetPost = "{\"created_at\":\""+ created_at + "\",\"content\":\"" + content + "\",\"author\":{\"user\":{\"username\":\"" + username + "\"}}}" ;
               System.out.println(stringTweetPost);

               JsonNode tweetPost = new ObjectMapper().readValue(stringTweetPost, JsonNode.class);
               tweetPosts.add(tweetPost);
           }
       }
       return tweetPosts;
   }
}