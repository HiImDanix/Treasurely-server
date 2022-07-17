package com.danielandrej.treasure_hunt.controllers;

import com.danielandrej.treasure_hunt.models.Team;
import com.danielandrej.treasure_hunt.services.TeamService;
import com.danielandrej.treasure_hunt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Get all player groups
     * @return List of player groups
     */
    @GetMapping(value="player_groups", produces="application/json")
    public List<Team> findTeams(@RequestParam String player_session_id) {
        return teamService.getTeams(player_session_id);
    }

    /**
     * Create a new player group
     * @param player_session_id Player session ID
     * @param name Player group name
     */
    @PostMapping(value="player_groups", produces="application/json")
    public Team createTeam(@RequestParam String name,
                                         @RequestParam String player_session_id) {
        return teamService.createTeam(name, player_session_id);
    }


}
