
def battingQueryGenerator(playerName, year, team, position, battingAvg,
                          hits, homeRuns, rbi, steals, stolenBasesRatio):
    Select = "Select distinct p.name, b.year, t.name as Team, d.battingAvg, b.hits, b.homeruns, b.RBI, b.stolenBases, b.strikeouts, c.stolenBasesRatio"
    From = " from TeamMember p, BattingStatistics b, AllStar a, Team t, (Select bat.batterID, bat.year, trunc((Sum(bat.hits)/Sum(bat.atBats) + 0.00001), 3) as battingAvg from BattingStatistics bat, TeamMember mem Where mem.teamMemberID = bat.batterID group by bat.batterID, bat.year) d, (select batter.batterId, batter.year, trunc((Sum(batter.stolenBases)/(Sum(batter.caughtStealing) + Sum(batter.stolenBases) + 0.00001)), 3) * 100 as stolenBasesRatio from BattingStatistics batter, TeamMember memberr Where memberr.teamMemberId = batter.batterId group by batter.batterID, batter.year) c"
    Where = " Where p.teamMemberID = b.batterID and b.teamID = t.teamID and d.batterId = b.batterId and d.year = b.year and c.year = b.year and c.batterId = b.batterId"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and b.year = " + year
    if team:
        Query = Query + " and t.name like \'%" + team + "%\'"
    if battingAvg:
        battingAvg.split("-")
        Query = Query + " and d.battingAvg >= " + battingAvg.split("-")[0] + " and d.battingAvg <= " + battingAvg.split("-")[1]
    if hits:
        hits.split("-")
        Query = Query + " and b.hits >= " + hits.split("-")[0] + " and b.hits <= " + hits.split("-")[1]
    if homeRuns:
        homeRuns.split("-")
        Query = Query + " and b.homeRuns >= " + homeRuns.split("-")[0] + " and b.homeRuns <= " + homeRuns.split("-")[1]
    if rbi:
        rbi.split("-")
        Query = Query + " and b.RBI >= " + rbi.split("-")[0] + " and b.RBI <= " + rbi.split("-")[1]
    if steals:
        steals.split("-")
        Query = Query + " and b.steals >= " + steals.split("-")[0] + " and b.steals <= " + steals.split("-")[1]
    if stolenBasesRatio:
        stolenBasesRatio.split("-")
        Query = Query + " and c.stolenBasesRatio >= " + stolenBasesRatio.split("-")[0] + " and c.stolenBasesRatio <= " + stolenBasesRatio.split("-")[1]
    Query = Query + " Order by b.year asc;"
    return (Query)

def pitchingQueryGenerator(playerName, year, team, games, gamesStarted, wins,
                            losses, completedGames, saves, hitsAllowed, strikeOuts,
                            era, walks):
    Select = "Select distinct p.name, s.year, t.name as Team, s.games, s.gamesStarted, s.wins, s.losses, s.completeGames, s.saves, s.hits, s.strikeouts, s.era, s.walks"
    From = " From teamMember p, pitcher s, team t, allstars a"
    Where = " Where s.pitcherID = p.teamMemberID and s.teamID = t.teamID"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and s.year = " + year
    if team:
        Query = Query + " and t.name like \'%" + team + "%\'"
    if games:
        games.split("-")
        Query = Query + " and games >= " + games.split("-")[0] + " and games <= " + games.split("-")[1]
    if gamesStarted:
        gamesStarted.split("-")
        Query = Query + " and gamesStarted >= " + gamesStarted.split("-")[0] + " and gamesStarted <= " + gamesStarted.split("-")[1]
    if wins:
        wins.split("-")
        Query = Query + " and wins >= " + wins.split("-")[0] + " and wins <= " + wins.split("-")[1]
    if losses:
        losses.split("-")
        Query = Query + " and losses >= " + losses.split("-")[0] + " and losses <= " + losses.split("-")[1]
    if completedGames:
        completedGames.split("-")
        Query = Query + " and completedGames >= " + completedGames.split("-")[0] + " and completedGames <= " + completedGames.split("-")[1]
    if saves:
        saves.split("-")
        Query = Query + " and saves >= " + saves.split("-")[0] + " and saves <= " + saves.split("-")[1]
    if hitsAllowed:
        hitsAllowed.split("-")
        Query = Query + " and hitsAllowed >= " + hitsAllowed.split("-")[0] + " and hitsAllowed <= " + hitsAllowed.split("-")[1]
    if strikeOuts:
        strikeOuts.split("-")
        Query = Query + " and strikeOuts >= " + strikeOuts.split("-")[0] + " and strikeOuts <= " + strikeOuts.split("-")[1]
    if era:
        era.split("-")
        Query = Query + " and era >= " + era.split("-")[0] + " and era <= " + era.split("-")[1]
    if walks:
        walks.split("-")
        Query = Query + " and walks >= " + walks.split("-")[0] + " and walks <= " + walks.split("-")[1]
    Query = Query + "Order by s.year asc;"
    return (Query)

def fieldingQueryGenerator(playerName, year, team, position, games, assists,
                            caughtStealing, stolenBasesAllowed, doublePlaysCaused,
                            wildPitches):
    Select = "Select distinct p.name, f.year, t.name as Team, f.position, f.games, f.assists, f.caughtStealing, f.stolenBasesAllowed, f.doublePlays, f.wildPitches"
    From = " From teamMember p, defensiveStatistics f, Team t"
    Where = " Where p.teamMemberID = f.defenderID and f.teamID = t.teamID"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like \'%" + playerName + "%\'"
    if year:
        Query = Query + " and f.year = " + year
    if team:
        Query = Query + " and t.name like \'%" + team + "%\'"
    if position:
        Query = Query + " and f.position = " + position
    if games:
        games.split("-")
        Query = Query + " and games >= " + games.split("-")[0] + " and games <= " + games.split("-")[1]
    if assists:
        assists.split("-")
        Query = Query + " and assists >= " + assists.split("-")[0] + " and assists <= " + assists.split("-")[1]
    if caughtStealing:
        caughtStealing.split("-")
        Query = Query + " and caughtStealing >= " + caughtStealing.split("-")[0] + " and caughtStealing <= " + caughtStealing.split("-")[1]
    if stolenBasesAllowed:
        stolenBasesAllowed.split("-")
        Query = Query + " and stolenBasesAllowed >= " + stolenBasesAllowed.split("-")[0] + " and stolenBasesAllowed <= " + stolenBasesAllowed.split("-")[1]
    if doublePlaysCaused:
        doublePlaysCaused.split("-")
        Query = Query + " and doublePlaysCaused >= " + doublePlaysCaused.split("-")[0] + " and doublePlaysCaused <= " + doublePlaysCaused.split("-")[1]
    if wildPitches:
        wildPitches.split("-")
        Query = Query + " and wildPitches >= " + wildPitches.split("-")[0] + " and wildPitches <= " + wildPitches.split("-")[1]
    Query = Query + " Order by f.year asc;"
    return (Query)

print(battingQueryGenerator(None, "1997", "Marlins", None, None, None, "10-40", None, None, None))
