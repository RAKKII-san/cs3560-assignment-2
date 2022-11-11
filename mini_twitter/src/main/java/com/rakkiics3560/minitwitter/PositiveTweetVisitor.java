package com.rakkiics3560.minitwitter;

import java.util.ArrayList;
import java.util.Arrays;

/** 
 * Searches for a positive word in a tweet and returns true if 
 * visitor finds at least one.
 * @author Rakkii
 */
public class PositiveTweetVisitor implements Visitor {
    private static String[] positiveWords = {
        "adorable", "amazing", "awesome", "angelic", "acclaimed",
        "affirmative", "agreeable", "appealing", "attractive",
        "beaming", "beautiful", "beneficial", "bliss", "brave",
        "bravo", "brilliant", "calm", "celebrate", "champ", "champion",
        "charming", "cheery", "congrats", "congratulations", "cool", 
        "courageous", "creative", "cute", "dazzling", "delight",
        "delightful", "earnest", "ecstatic", "effective", "efficient",
        "effortless", "elegant", "enchanting", "encouraging",
        "energetic", "energized", "engaging", "enthusiastic",
        "excellent", "exciting", "exquisite", "fabulous", "fair",
        "fantastic", "favorable", "fine", "flourishing", "fortunate",
        "fresh", "friendly", "fun", "funny", "generous", "genius",
        "giving", "glamorous", "good", "gorgeous", "graceful", "great",
        "grin", "handsome", "happy", "happily", "harmonious",
        "healing", "healthy", "heavenly", "honest", "honorable",
        "honored", "hug", "ideal", "imaginative", "impressive",
        "innovative", "intellectual", "intelligent", "intuitive",
        "inventive", "jovial", "joy", "joyous", "joyful", "jubilant",
        "keen", "kind", "knowledgeable", "laugh", "laughing",
        "legendary", "lively", "lovely", "lucky", "marvelous",
        "masterful", "meaningful", "merit", "miraculous", "motivating",
        "nice", "nutritious", "okay", "optimistic", "paradise",
        "perfect", "perfectly", "phenomenal", "pleasant", "pleasure",
        "pleasurable", "polished", "popular", "positive", "powerful",
        "pretty", "productive", "progress", "proud", "quality",
        "reassuring", "refined", "refreshing", "rejoice", "reliable",
        "remarkable", "resounding", "respect", "respected", "reward",
        "rewarding", "safe", "satisfactory", "secure", "skilled",
        "skillful", "smile", "smiling", "soulful", "special",
        "spiritual", "stunning", "stupendous", "success", "successful",
        "superb", "surprising", "terrific", "thrilling", "thriving",
        "tranquil", "trusting", "truthful", "upbeat", "unbelievable",
        "valued", "vibrant", "victorious", "victory", "virtuous",
        "vivacious", "welcome", "wholesome", "wonderful", "yes",
        "yummy", "pog", "poggers", "lit", "lovely", "love"
    };
    
    private static ArrayList<String> positiveWordSet = 
            new ArrayList<>(Arrays.asList(positiveWords));

    @Override
    public boolean visitTweet(Tweet tweet) {
        return findPositiveWords(tweet);
    }

    /**
     * Tokenizes a tweet into words, then matches each word to
     * the positive words set.
     * @return If a positive word was found anywhere in the tweet.
     */
    private boolean findPositiveWords(Tweet tweet) {
        String tweetContent = tweet.getMessage();
        String[] tokens = tweetContent.split("[ .,!?:;'\"-]+\\s*");
        for (String token : tokens) {
            if (positiveWordSet.contains(token.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
