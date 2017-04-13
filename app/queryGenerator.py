def battingQueryGenerator(playerName, year, team, battingAvg,
                          hits, homeRuns, rbi, steals, stolenBasesRatio):
    Select = "Select distinct(p.name), b.year, t.name as Team, d.battingAvg, ba.Season_BattingAverage, b.hits, b.homeruns, b.RBI, b.strikeouts, b.stolenBases, c.stolenBasesRatio, q.playerSalary as Salary, u.TotalASGSelections"
    From = " from TeamMember p, BattingStatistics b, Team t, (Select bat.batterID, bat.year, trunc((Sum(bat.hits)/(Sum(bat.atBats) + 0.00001) + 0.00001), 3) as battingAvg from BattingStatistics bat, TeamMember mem Where mem.teamMemberID = bat.batterID group by bat.batterID, bat.year) d, (select batter.batterId, batter.year, trunc((Sum(batter.stolenBases)/(Sum(batter.caughtStealing) + Sum(batter.stolenBases) + 0.00001)), 3) * 100 as stolenBasesRatio from BattingStatistics batter, TeamMember memberr Where memberr.teamMemberId = batter.batterId group by batter.batterID, batter.year) c, (select tm.TeamMemberID, NVL(s.year, 0) as salaryYear, NVL(s.salary, 0) as playerSalary from TeamMember tm FULL OUTER JOIN Salary s ON tm.teamMemberID = s.playerID Group by tm.teamMemberID, s.year, s.salary Order by s.salary desc) q, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select distinct(batting.year), trunc((Sum(batting.hits)/(Sum(batting.atBats) + 0.00001)), 3) as Season_BattingAverage from BattingStatistics batting group by batting.year) ba"
    Where = " Where p.teamMemberID = b.batterID and b.teamID = t.teamID and d.batterId = b.batterId and d.year = b.year and c.year = b.year and c.batterId = b.batterId and q.teamMemberID = b.batterID and q.salaryYear = b.year and u.teamMemberId = b.batterId and ba.year = b.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and b.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
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
        Query = Query + " and b.stolenbases >= " + steals.split("-")[0] + " and b.stolenbases <= " + steals.split("-")[1]
    if stolenBasesRatio:
        stolenBasesRatio.split("-")
        Query = Query + " and c.stolenBasesRatio >= " + stolenBasesRatio.split("-")[0] + " and c.stolenBasesRatio <= " + stolenBasesRatio.split("-")[1]
    Query = Query + " Order by b.year asc"
    return (Query)


def battingQueryGeneratorNoSalary(playerName, year, team, battingAvg,
                          hits, homeRuns, rbi, steals, stolenBasesRatio):
    Select = "Select distinct(p.name), b.year, t.name as Team, d.battingAvg, ba.Season_BattingAverage, b.hits, b.homeruns, b.RBI, b.strikeouts, b.stolenBases, c.stolenBasesRatio, u.TotalASGSelections"
    From = " from TeamMember p, BattingStatistics b, Team t, (Select bat.batterID, bat.year, trunc((Sum(bat.hits)/(Sum(bat.atBats) + 0.00001) + 0.00001), 3) as battingAvg from BattingStatistics bat, TeamMember mem Where mem.teamMemberID = bat.batterID group by bat.batterID, bat.year) d, (select batter.batterId, batter.year, trunc((Sum(batter.stolenBases)/(Sum(batter.caughtStealing) + Sum(batter.stolenBases) + 0.00001)), 3) * 100 as stolenBasesRatio from BattingStatistics batter, TeamMember memberr Where memberr.teamMemberId = batter.batterId group by batter.batterID, batter.year) c, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select distinct(batting.year), trunc((Sum(batting.hits)/(Sum(batting.atBats) + 0.00001)), 3) as Season_BattingAverage from BattingStatistics batting group by batting.year) ba"
    Where = " Where p.teamMemberID = b.batterID and b.teamID = t.teamID and d.batterId = b.batterId and d.year = b.year and c.year = b.year and c.batterId = b.batterId and u.teamMemberId = b.batterId and ba.year = b.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and b.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
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
        Query = Query + " and b.stolenbases >= " + steals.split("-")[0] + " and b.stolenbases <= " + steals.split("-")[1]
    if stolenBasesRatio:
        stolenBasesRatio.split("-")
        Query = Query + " and c.stolenBasesRatio >= " + stolenBasesRatio.split("-")[0] + " and c.stolenBasesRatio <= " + stolenBasesRatio.split("-")[1]
    Query = Query + " Order by b.year asc"
    return (Query)

def pitchingQueryGenerator(playerName, year, team, games, gamesStarted, wins,
                            losses, completedGames, saves, hitsAllowed, strikeOuts,
                            era, walks):
    Select = "Select distinct(p.name), s.year, t.name as Team, s.games, s.gamesStarted, s.wins, s.losses, s.completeGames, s.saves, s.hits as HitsAllowed, s.strikeouts,  k.strikeoutPercentage, s.walks, s.era, m.averageERAinSeason as average_ERA_forSeason, r.WinPercentage, q.playerSalary as Salary, u.TotalASGSelections"
    From = " From teamMember p, pitcher s, team t, (select tm.TeamMemberID, NVL(s.year, 0) as salaryYear, NVL(s.salary, 0) as playerSalary from TeamMember tm FULL OUTER JOIN Salary s ON tm.teamMemberID = s.playerID Group by tm.teamMemberID, s.year, s.salary Order by s.salary desc) q, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select pit.PitcherID, pit.year, trunc((Sum(pit.Wins)/(Sum(pit.Games) + 0.00001)), 3) * 100 as WinPercentage from Pitcher pit, TeamMember memberr where memberr.teamMemberID = pit.pitcherID group by pit.pitcherID, pit.year) r, (select distinct(pitt.year), trunc(AVG(pitt.era), 3) as AverageERAinSeason from Pitcher pitt group by pitt.year) m, (select pi.pitcherID, pi.year, trunc((Sum(pi.strikeOuts)/(Sum(pi.outsPitched) + 0.00001)), 3) * 100 as strikeoutPercentage from Pitcher pi, TeamMember tm where tm.teamMemberID = pi.pitcherID group by pi.pitcherID, pi.year) k"
    Where = " Where s.pitcherID = p.teamMemberID and s.teamID = t.teamID and q.teamMemberID = s.pitcherID and q.salaryYear = s.year and u.teamMemberId = s.pitcherID and r.pitcherId = s.pitcherId and r.year = s.year and s.year = m.year and k.pitcherID = s.pitcherID and k.year = s.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and s.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
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
        Query = Query + " and completeGames >= " + completedGames.split("-")[0] + " and completeGames <= " + completedGames.split("-")[1]
    if saves:
        saves.split("-")
        Query = Query + " and saves >= " + saves.split("-")[0] + " and saves <= " + saves.split("-")[1]
    if hitsAllowed:
        hitsAllowed.split("-")
        Query = Query + " and hits >= " + hitsAllowed.split("-")[0] + " and hits <= " + hitsAllowed.split("-")[1]
    if strikeOuts:
        strikeOuts.split("-")
        Query = Query + " and strikeOuts >= " + strikeOuts.split("-")[0] + " and strikeOuts <= " + strikeOuts.split("-")[1]
    if era:
        era.split("-")
        Query = Query + " and era >= " + era.split("-")[0] + " and era <= " + era.split("-")[1]
    if walks:
        walks.split("-")
        Query = Query + " and walks >= " + walks.split("-")[0] + " and walks <= " + walks.split("-")[1]
    Query = Query + " Order by s.year asc"
    return (Query)

def pitchingQueryGeneratorNoSalary(playerName, year, team, games, gamesStarted, wins,
                                   losses, completedGames, saves, hitsAllowed, strikeOuts,
                                   era, walks):
    Select = "Select distinct(p.name), s.year, t.name as Team, s.games, s.gamesStarted, s.wins, s.losses, s.completeGames, s.saves, s.hits as HitsAllowed, s.strikeouts,  k.strikeoutPercentage, s.walks, s.era, m.averageERAinSeason as average_ERA_forSeason, r.WinPercentage, u.TotalASGSelections"
    From = " From teamMember p, pitcher s, team t, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select pit.PitcherID, pit.year, trunc((Sum(pit.Wins)/(Sum(pit.Games) + 0.00001)), 3) * 100 as WinPercentage from Pitcher pit, TeamMember memberr where memberr.teamMemberID = pit.pitcherID group by pit.pitcherID, pit.year) r, (select distinct(pitt.year), trunc(AVG(pitt.era), 3) as AverageERAinSeason from Pitcher pitt group by pitt.year) m, (select pi.pitcherID, pi.year, trunc((Sum(pi.strikeOuts)/(Sum(pi.outsPitched) + 0.00001)), 3) * 100 as strikeoutPercentage from Pitcher pi, TeamMember tm where tm.teamMemberID = pi.pitcherID group by pi.pitcherID, pi.year) k"
    Where = " Where s.pitcherID = p.teamMemberID and s.teamID = t.teamID and u.teamMemberId = s.pitcherID and r.pitcherId = s.pitcherId and r.year = s.year and s.year = m.year and k.pitcherID = s.pitcherID and k.year = s.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and s.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
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
        Query = Query + " and completeGames >= " + completedGames.split("-")[0] + " and completeGames <= " + completedGames.split("-")[1]
    if saves:
        saves.split("-")
        Query = Query + " and saves >= " + saves.split("-")[0] + " and saves <= " + saves.split("-")[1]
    if hitsAllowed:
        hitsAllowed.split("-")
        Query = Query + " and hits >= " + hitsAllowed.split("-")[0] + " and hits <= " + hitsAllowed.split("-")[1]
    if strikeOuts:
        strikeOuts.split("-")
        Query = Query + " and strikeOuts >= " + strikeOuts.split("-")[0] + " and strikeOuts <= " + strikeOuts.split("-")[1]
    if era:
        era.split("-")
        Query = Query + " and era >= " + era.split("-")[0] + " and era <= " + era.split("-")[1]
    if walks:
        walks.split("-")
        Query = Query + " and walks >= " + walks.split("-")[0] + " and walks <= " + walks.split("-")[1]
    Query = Query + " Order by s.year asc"
    return (Query)

def fieldingQueryGenerator(playerName, year, team, position, games, assists,
                            caughtStealing, stolenBasesAllowed, doublePlaysCaused,
                            wildPitches):
    Select = "Select distinct p.name, f.year, t.name as Team, f.position, f.games, y.gameStartedPercentage, f.assists, f.caughtStealing, f.stolenBasesAllowed, z.stealsPreventedPercentage, f.doublePlays, f.wildPitches, u.TotalASGSelections, f.errors, q.playerSalary as Salary"
    From = " From teamMember p, defensiveStatistics f, Team t, (select tm.TeamMemberID, NVL(s.year, 0) as salaryYear, NVL(s.salary, 0) as playerSalary from TeamMember tm FULL OUTER JOIN Salary s ON tm.teamMemberID = s.playerID Group by tm.teamMemberID, s.year, s.salary Order by s.salary desc) q, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select def.defenderID, def.year, trunc((Sum(def.caughtStealing)/(Sum(def.caughtStealing) + Sum(def.stolenBasesAllowed) + 0.00001)), 3) * 100 as stealsPreventedPercentage from DefensiveStatistics def, TeamMember memberr where memberr.teamMemberID = def.defenderID group by def.defenderID, def.year) z, (select x.defenderID, x.year, trunc((Sum(x.GAMESSTARTED)/(Sum(x.games) + 0.00001)), 3) * 100 as gameStartedPercentage from DefensiveStatistics x, TeamMember mem Where mem.teamMemberID = x.defenderID group by x.defenderID, x.year) y"
    Where = " Where p.teamMemberID = f.defenderID and f.teamID = t.teamID and q.teamMemberID = f.defenderID and q.salaryYear = f.year and u.teamMemberId = f.defenderID and z.defenderID = f.defenderID and z.year = f.year and y.defenderID = f.defenderID and y.year = f.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and f.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
    if position:
        Query = Query + " and f.position = '" + position + "'"
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
        Query = Query + " and doublePlays >= " + doublePlaysCaused.split("-")[0] + " and doublePlays <= " + doublePlaysCaused.split("-")[1]
    if wildPitches:
        wildPitches.split("-")
        Query = Query + " and wildPitches >= " + wildPitches.split("-")[0] + " and wildPitches <= " + wildPitches.split("-")[1]
    Query = Query + " Order by f.year asc"
    return (Query)

def fieldingQueryGeneratorNoSalary(playerName, year, team, position, games, assists,
                            caughtStealing, stolenBasesAllowed, doublePlaysCaused,
                            wildPitches):
    Select = "Select distinct p.name, f.year, t.name as Team, f.position, f.games, y.gameStartedPercentage, f.assists, f.caughtStealing, f.stolenBasesAllowed, z.stealsPreventedPercentage, f.doublePlays, f.wildPitches, u.TotalASGSelections, f.errors"
    From = " From teamMember p, defensiveStatistics f, Team t, (select tmMem.teamMemberID, count(allS.gamesPlayed) as TotalASGSelections from Teammember tmMem FULL OUTER JOIN AllStar allS ON tmMem.teamMemberID = allS.AllStarID Group by tmMem.TeamMemberID Order By TotalASGSelections ASC) u, (select def.defenderID, def.year, trunc((Sum(def.caughtStealing)/(Sum(def.caughtStealing) + Sum(def.stolenBasesAllowed) + 0.00001)), 3) * 100 as stealsPreventedPercentage from DefensiveStatistics def, TeamMember memberr where memberr.teamMemberID = def.defenderID group by def.defenderID, def.year) z, (select x.defenderID, x.year, trunc((Sum(x.GAMESSTARTED)/(Sum(x.games) + 0.00001)), 3) * 100 as gameStartedPercentage from DefensiveStatistics x, TeamMember mem Where mem.teamMemberID = x.defenderID group by x.defenderID, x.year) y"
    Where = " Where p.teamMemberID = f.defenderID and f.teamID = t.teamID and u.teamMemberId = f.defenderID and z.defenderID = f.defenderID and z.year = f.year and y.defenderID = f.defenderID and y.year = f.year"
    Query = Select + From + Where
    if playerName:
        Query = Query + " and p.name like '%" + playerName + "%'"
    if year:
        Query = Query + " and f.year = " + year
    if team:
        Query = Query + " and t.name like '%" + team + "%'"
    if position:
        Query = Query + " and f.position = '" + position + "'"
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
        Query = Query + " and doublePlays >= " + doublePlaysCaused.split("-")[0] + " and doublePlays <= " + doublePlaysCaused.split("-")[1]
    if wildPitches:
        wildPitches.split("-")
        Query = Query + " and wildPitches >= " + wildPitches.split("-")[0] + " and wildPitches <= " + wildPitches.split("-")[1]
    Query = Query + " Order by f.year asc"
    return (Query)

#print(battingQueryGenerator(None, None, None, None, None, None, None, None, None, None))
#print(fieldingQueryGenerator('Christian Yelich', None, None, None, None, None,None, None, None,None))
#print(pitchingQueryGenerator(None, None, None, None, None, None, None, None, None, None, None, None, None))