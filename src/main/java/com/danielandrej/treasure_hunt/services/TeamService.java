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
        if (player.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        return teamRepository.findAll();
    }

    public Team findTeamByID(Long teamID) {
        Optional<Team> team = teamRepository.findById(teamID);
        if (team.isEmpty()) {
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

        // check if team name is already taken
        Optional<Team> teamWithSameName = teamRepository.findByName(name);
        if (teamWithSameName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team name is already taken");
        }

        Team team = new Team(name, player.get());
        return teamRepository.save(team);
    }

    public void invitePlayer(String player_session_id, Long player_id) {

        Optional<Player> player = playerService.findPlayerBySessionID(player_session_id);
        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        // The inviter must be in a team and the leader of it
        if (player.get().getTeam() == null || !player.get().getTeam().getLeader().equals(player.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You must be the leader of a team to invite players");
        }

        // Get player to invite
        Optional<Player> playerToInvite = playerService.findPlayerByID(player_id);
        if (!playerToInvite.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The player you are trying to invite does not exist");
        }

        // Same game
        if (!player.get().getGame().equals(playerToInvite.get().getGame())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You can only invite players from the same game");
        }

        // Check if the player is already in a team
        if (playerToInvite.get().getTeam() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The player you are trying to invite is already in a team");
        }

        // Add player to team IF requested to join,
        // else invite the player to join the team
        Team team = player.get().getTeam();
        if (team.getPendingPlayers().contains(playerToInvite.get())) {
            team.getPendingPlayers().remove(playerToInvite.get());
            team.addPlayer(playerToInvite.get());
        } else {
            team.addInvitedPlayer(playerToInvite.get());
        }
        // persist changes
        teamRepository.save(player.get().getTeam());
    }

    // Ask to join a team, or accept invitation to a team if an invitation to the team exists
    public void joinTeam(String player_session_id, Long team_id) {
        Optional<Player> player = playerService.findPlayerBySessionID(player_session_id);
        if (!player.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not in a game");
        }

        // Get team to join
        Optional<Team> team = teamRepository.findById(team_id);
        if (!team.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The team you are trying to join does not exist");
        }

        // Team must be in the same game as the player
        if (!player.get().getGame().equals(team.get().getLeader().getGame())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You can only join teams from the same game");
        }

        // Check if the player is already in a team
        if (player.get().getTeam() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are already in a team");
        }

        // Check if the player is already invited to the team
        if (team.get().getInvitedPlayers().contains(player.get())) {
            team.get().getInvitedPlayers().remove(player.get());
            team.get().addPlayer(player.get());
        } else {
            team.get().addPlayer(player.get());
        }
        // persist changes
        teamRepository.save(team.get());
    }
}
