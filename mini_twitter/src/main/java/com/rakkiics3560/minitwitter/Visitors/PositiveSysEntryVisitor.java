package com.rakkiics3560.minitwitter.visitors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import com.rakkiics3560.minitwitter.User;

public class PositiveSysEntryVisitor implements Visitor {
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
        "yummy", "pog", "poggers"
    };
    private static Set<String> positiveWordSet = 
            new HashSet<>(Arrays.asList(positiveWords));

    @Override
    public void visit(User user) {
        // TODO Auto-generated method stub
        
    }
    
}
