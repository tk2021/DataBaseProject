def battingQueryGenerator(playerName, year, team, position, battingAvg,
                            hits, homeRuns,rbi, steals, stolenBasesRatio):
    Select = "Select p.name, b.year, t.name, d.battingAvg, b.hits, b.homeruns, b.RBI, b.steals, b.strikeouts, c.stolenBasesRatio"
    From = " From TeamMember p, BattingStatistics b, AllStar a, Team t, (Select bat.batterID, bat.year, trunc((Sum(bat.hits)/Sum(bat.atBats) + 0.00001)), 3) as battingAvg from BattingStatistics bat, TeamMember mem Where mem.teamMemberID = bat.batterID group by bat.batterID, bat.year) d," + 
    + " (select batter.batterId, batter.year, trunc((Sum(batter.stolenBases)/(Sum(batter.caughtStealing) + Sum(batter.stolenBases) + 0.00001)), 3) * 100 as stolenBasesRatio from BattingStatistics batter, TeamMember memberr Where memberr.teamMemberId = batter.batterId group by batter.batterID, batter.year) c"
    Where = " Where p.teamMemberID = b.batterID and p.teamID = t.teamID and d.batterId = b.batterId and d.year = b.year and c.year = b.year and c.batterId = b.batterId"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and b.year = " + year
    if team:
        Query = Query + " and t.name like \'%" + team + "%\'"
    if battingAvg:
        battingAvg.split("-")
        Query = Query + " and d.battingAvg >= " + battingAvg[0] + " and d.battingAvg <= " + battingAvg[2]
    if hits:
        hits.split("-")
        Query = Query + " and b.hits >= " + hits[0] + " and b.hits <= " + hits[2]
    if homeRuns:
        homeRuns.split("-")
        Query = Query + " and b.homeRuns >= " + homeRuns[0] + " and b.homeRuns <= " + homeRuns[2]
    if rbi:
        rbi.split("-")
        Query = Query + " and b.RBI >= " + rbi[0] + " and b.RBI <= " + rbi[2]
    if steals:
        steals.split("-")
        Query = Query + " and b.steals >= " + steals[0] + " and b.steals <= " + steals[2]
    if strikeOuts:
        steals.split("-")
        Query = Query + " and b.strikeOuts >= " + steals[0] + " and b.strikeOuts <= " + steals[2]
    if stolenBasesRatio:
        stolenBasesRatio.split("-")
        Query = Query + " and c.stolenBasesRatio >= " + stolenBasesRatio[0] + " and c.stolenBasesRatio <= " + stolenBasesRatio[2]
    return (Query)

def pitchingQueryGenerator(playerName, year, team, games, gamesStarted, wins,
                            losses, completedGames, saves, hitsAllowed, strikeOuts,
                            era, walks):
    Select = "Select p.name, s.year, t.name, s.games, s.gamesStarted, s.wins, s.losses, s.completeGames, s.saves, s.hitsAllowed, s.strikeouts, s.era, s.walks"
    From = " From teamMember p, pitching s, team t, allstars a"
    Where = "Where s.pitcherID = p.teamMemberID and p.teamID = t.teamID"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and TeamMember.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and BattingStatistics.year = " + year
    if team:
        Query = Query + " and Team.name like \'%" + team + "%\'"
    if games:
        games.split("-")
        Query = Query + " and games >= " + games[0] + " and games <= " + games[2]
    if gamesStarted:
        gamesStarted.split("-")
        Query = Query + " and gamesStarted >= " + gamesStarted[0] + " and gamesStarted <= " + gamesStarted[2]
    if wins:
        wins.split("-")
        Query = Query + " and wins >= " + wins[0] + " and wins <= " + wins[2]
    if losses:
        losses.split("-")
        Query = Query + " and losses >= " + losses[0] + " and losses <= " + losses[2]
    if completedGames:
        completedGames.split("-")
        Query = Query + " and completedGames >= " + completedGames[0] + " and completedGames <= " + completedGames[2]
    if saves:
        saves.split("-")
        Query = Query + " and saves >= " + saves[0] + " and saves <= " + saves[2]
    if hitsAllowed:
        hitsAllowed.split("-")
        Query = Query + " and hitsAllowed >= " + hitsAllowed[0] + " and hitsAllowed <= " + hitsAllowed[2]
    if strikeOuts:
        strikeOuts.split("-")
        Query = Query + " and strikeOuts >= " + strikeOuts[0] + " and strikeOuts <= " + strikeOuts[2]
    if era:
        era.split("-")
        Query = Query + " and era >= " + era[0] + " and era <= " + era[2]
    if walks:
        walks.split("-")
        Query = Query + " and walks >= " + walks[0] + " and walks <= " + walks[2]
    return (Query)

def fieldingQueryGenerator(playerName, year, team, position, games, assists,
                            caughtStealing, stolenBasesAllowed, doublePlaysCaused,
                            wildPitches):
    Select = "Select p.name, s.year, t.name, f.position, f.games, f.assists, f.caughtStealing, f.stolenBasesAllowed, f.doublePlaysCaused, f.wildPitches"
    From = " From teamMember p, Fielding f, Team t"
    Where = " Where p.teamMemberID = f.defenderID and p.teamID = t.teamID"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and TeamMember.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and BattingStatistics.year = " + year
    if team:
        Query = Query + " and Team.name like \'%" + team + "%\'"
    if position:
        Query = Query + " and BattingStatistics.position = " + position
    if games:
        games.split("-")
        Query = Query + " and games >= " + games[0] + " and games <= " + games[2]
    if assists:
        assists.split("-")
        Query = Query + " and assists >= " + assists[0] + " and assists <= " + assists[2]
    if caughtStealing:
        caughtStealing.split("-")
        Query = Query + " and caughtStealing >= " + caughtStealing[0] + " and caughtStealing <= " + caughtStealing[2]
    if stolenBasesAllowed:
        stolenBasesAllowed.split("-")
        Query = Query + " and stolenBasesAllowed >= " + stolenBasesAllowed[0] + " and stolenBasesAllowed <= " + stolenBasesAllowed[2]
    if doublePlaysCaused:
        doublePlaysCaused.split("-")
        Query = Query + " and doublePlaysCaused >= " + doublePlaysCaused[0] + " and doublePlaysCaused <= " + doublePlaysCaused[2]
    if wildPitches:
        wildPitches.split("-")
        Query = Query + " and wildPitches >= " + wildPitches[0] + " and wildPitches <= " + wildPitches[2]
    return (Query)