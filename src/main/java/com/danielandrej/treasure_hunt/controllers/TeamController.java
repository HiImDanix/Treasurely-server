package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Team;
import com.danielandrej.treasure_hunt.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Get all teams
     * @return List of teams
     */
    @GetMapping(value="teams", produces="application/json")
    public List<Team> findTeams(@RequestParam String player_session_id) {
        return teamService.getTeams(player_session_id);
    }

    /**
     * Create a new team and make the player the leader of the team
     * @param player_session_id Player session ID
     * @param name Team name
     */
    @PostMapping(value="teams", produces="application/json")
    public Team createTeam(@RequestParam String name,
                                         @RequestParam String player_session_id) {
        return teamService.createTeam(name, player_session_id);
    }

    /**
     * Invite a player to a team, or accept invitation to a team if exists
     * @param player_session_id  The team leader's Player session ID
     * @param player_id Player ID for the player to be invited to the team
     */
    @PostMapping(value="teams/invite", produces="application/json")
    public void invitePlayer(@RequestParam String player_session_id,
                             @RequestParam Long player_id) {
        teamService.invitePlayer(player_session_id, player_id);
    }

    /**
     * Ask to join a team, or accept invitation to a team if an invitation to the team exists
     * @param team_id Team ID for the team to join
     * @param player_session_id  The player's session ID
     */
    @PostMapping(value="teams/{team_id}/join", produces="application/json")
    public void joinTeam(@PathVariable Long team_id,
                         @RequestParam String player_session_id) {
        teamService.joinTeam(player_session_id, team_id);
    }




}
