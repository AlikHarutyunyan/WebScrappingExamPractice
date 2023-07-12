package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.rsssf.org/tablesi/isra2022.html").get();
        Elements allElements = doc.getElementsByTag("pre");
        Elements children =  allElements.get(0).getAllElements();

        String text = children.get(0).text();
        text = text.substring(text.indexOf("Round 1"));
        String[] matchDays = text.split("Round");

        for (int i = 0; i < matchDays.length; i++) {
            if(matchDays[i].contains("Table")) {
                matchDays[i] = matchDays[i].substring(0, matchDays[i].indexOf("Table"));
            }
            if(matchDays[i].contains("awd")){
                int numIndex = indexOfAwarded(matchDays[i]);
                String score = matchDays[i].substring(numIndex,numIndex+3);
                matchDays[i] = matchDays[i].replaceFirst("awd",score);
            }
        }

        List<String> allMatchesLines = new ArrayList<>();

        for (int i = 0; i < matchDays.length; i++) {
            allMatchesLines.addAll(Arrays.asList(matchDays[i].split("\n")));
        }

        allMatchesLines = allMatchesLines.stream().filter(m -> m.contains("-")).toList();

        Set<String> allTeams = new HashSet<>();
        List<Match> allMatches = new ArrayList<>();

        allMatchesLines.forEach(m->{
            String score = m.substring(indexOfNumber(m)).trim();
            score = score.substring(0, score.indexOf(" ")).trim();
            String homeTeam = m.substring(0,m.indexOf(score)).trim();
            String awayTeam = m.substring(m.indexOf(score)+score.length()).trim();
            if(awayTeam.contains("   ")){
                awayTeam = awayTeam.substring(0,awayTeam.indexOf("   "));
            }
            allTeams.add(homeTeam);
            allTeams.add(awayTeam);
            allMatches.add(new Match(homeTeam,awayTeam,score));
        });

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String userInput = null;
            do {
                System.out.println("Write the name of the team: ");
                 userInput = scanner.nextLine();
            }while(!allTeams.contains(userInput));

            for (Match match : allMatches) {
                if(match.contains(userInput)){
                    match.printResultForTeam(userInput);
                }
            }

        }
    }

        private static int indexOfNumber(String match){
        int result = -1;
            outer: for (int i = 0; i < match.length(); i++) {
                for (int j = 0; j <=9; j++) {
                    if((match.charAt(i)+"").equals(j+"")){
                        result = i;
                        break outer;
                    }
                }
            }
            return result;
        }

        private static int indexOfAwarded(String match){
        int index = match.indexOf("awarded") + "awarded".length();
        return index+1;
        }

    }