package com.danielandrej.treasure_hunt.services;

import com.danielandrej.treasure_hunt.models.Player;
import com.danielandrej.treasure_hunt.models.Team;
import com.danielandrej.treasure_hunt.repositories.MissionRepository;
import com.danielandrej.treasure_hunt.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    TeamRepository teamRepository;
    MissionRepository missionRepository;
    PlayerService playerService;
    GameService gameService;


    @Autowired
    public TeamService(TeamRepository teamRepository, MissionRepository missionRepository, PlayerService playerService, GameService gameService) {
        this.teamRepository = teamRepository;
        this.missionRepository = missionRepository;
        this.playerService = playerService;
        this.gameService = gameService;

    }

    public List<Team> getTeams(String playerSessionID) {
        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);
        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        return teamRepository.findAll();
    }

    public Team findTeamByID(Long teamID) {
        Optional<Team> team = teamRepository.findById(teamID);
        if (!team.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player group not found");
        }
        return team.get();
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    // create player group
    public Team createTeam(String name, String playerSessionID) {
        Optional<Player> player = playerService.findPlayerBySessionID(playerSessionID);
        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }
        Team team = new Team(name, player.get());
        return teamRepository.save(team);
    }

}
