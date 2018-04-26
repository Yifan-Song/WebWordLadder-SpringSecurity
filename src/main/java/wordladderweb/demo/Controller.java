package wordladderweb.demo;

import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value ="user/main")

public class Controller {
    protected static Logger logger=LoggerFactory.getLogger(Controller.class);

    public static String ToStandardString(String str) {
        str = str.toLowerCase();
        str = str.replaceAll(" ", "");
        return str;
    }

    public static boolean IsWord(String s, Set<String> dictionary) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((int) ch > 122 || (int) ch < 97) return false;
        }
        if (dictionary.contains(s)) return true;
        return false;
    }

    public static String Search(String start, String end, Set<String> dictionary) {
        boolean same = false;
        Set<String> used = new HashSet<String>();
        String newWord = "";
        Stack<String> s1 = new Stack<String>();
        Queue<Stack<String>> q = new LinkedList<Stack<String>>();
        String result;
        s1.push(start);
        q.add(s1);
        while (!q.isEmpty()) {
            Stack<String> s = q.peek();
            q.remove();
            String top = s.peek();
            for (int i = 0; i < top.length() + 1; i++) {
                for (int j = 97; j < 149; j++) {
                    if (j >= 123) {
                        char ch = (char) (j - 26);
                        if (top.length() < end.length()) {
                            newWord = top.substring(0, i) + ch + top.substring(i, top.length());
                        }
                        if (top.length() > end.length()) {
                            if (i == top.length()) continue;
                            newWord = top.substring(0, i) + top.substring(i + 1, top.length());
                        }
                    } else {
                        if (i == top.length()) continue;
                        char ch = (char) (j);
                        newWord = top.substring(0, i) + ch + top.substring(i + 1, top.length());
                    }
                    if (used.contains(newWord)) same = true;
                    if (same) {
                        same = false;
                        continue;
                    }
                    if (!newWord.equals(end) && !IsWord(newWord, dictionary)) continue;
                    if (newWord.equals(end)) {
                        s.push(newWord);
                        int size = s.size();
                        result = "A ladder from " + end + " back to " + start + ":\n";
                        //System.out.println("A ladder from " + end + " back to " + start + ":\n");
                        for (int k = 0; k < size; k++) {
                            result += s.peek();
                            //System.out.println(s.peek());
                            s.pop();
                        }
                        //System.out.println("\n\n");
                        logger.debug("result={}",result);
                        return result;
                    } else {
                        used.add(newWord);
                        Stack<String> newStack = (Stack<String>) s.clone();
                        newStack.push(newWord);
                        q.add(newStack);
                    }
                }
            }
        }
        result = "No word ladder found from " + end + " back to " + start + ":\n\n";
        //System.out.println("No word ladder found from " + end + " back to " + start + ":\n\n");
        logger.debug("result={}",result);
        return result;
    }

    @RequestMapping(value = "/word1={word1}&word2={word2}",method = RequestMethod.GET)
    public static String main(@PathVariable String word1, @PathVariable String word2){
        Set<String> dictionary = new HashSet<String>();
        String filename;
        filename = "res\\dictionary.txt";
        File file = new File(filename);
        try {
            FileInputStream out = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String s1 = null;
            while ((s1 = br.readLine()) != null) {
                dictionary.add(s1);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return Search(word1, word2, dictionary);
    }
}
