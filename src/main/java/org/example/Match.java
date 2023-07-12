package org.example;

import java.util.HashMap;

public class Match {
    private String homeTeam;
    private String awayTeam;
    private HashMap<String, Integer> goalsPerClub;

    public Match(String homeTeam, String awayTeam, String score){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        int homeScore = Integer.parseInt(score.substring(0,score.indexOf("-")).trim());
        int awayScore = Integer.parseInt(score.substring(score.indexOf("-")+1).trim());
        this.goalsPerClub = new HashMap<>();
        this.goalsPerClub.put(this.homeTeam,homeScore);
        this.goalsPerClub.put(this.awayTeam,awayScore);
    }

    public boolean contains(String team){
        return this.homeTeam.equals(team) || this.awayTeam.equals(team);
    }

    public void printResultForTeam(String team){
        int teamScore = this.goalsPerClub.get(team);
        String opponent = this.awayTeam;
        if(team.equals(this.awayTeam)){
            opponent = this.homeTeam;
        }
        int opponentScore = this.goalsPerClub.get(opponent);
        if(teamScore > opponentScore){
            System.out.println("Win");
        }else if(opponentScore > teamScore) {
            System.out.println("Lose");
        }else{
            System.out.println("Draw");
        }
    }

}
