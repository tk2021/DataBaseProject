from flask import render_template, flash, redirect, url_for
from app import app
from app.forms import SearchForm, SQLForm, IDForm, Fielding
from app import db_functions
from .db_functions import db_test
from.tupleObject import tupleObject
from app import cx_oracle_test
from .queryGenerator import battingQueryGenerator, pitchingQueryGenerator, fieldingQueryGenerator
from .cx_oracle_test import cx_oracle_test, getQueryCursor, getAttributeDescriptions
import cx_Oracle
from flask import request

@app.route('/index/<position>')
def index(position):
        print(position)
        return render_template('index.html')

@app.route('/db_test')
def test():
    db_test()
    return

@app.route('/cx_oracle_test')
def cx_test():
    data = cx_oracle_test()
    queryString = ""
    for currentAtt in range(1, len(data)):
        queryString = queryString +  str(data[currentAtt]) + "  "
    flash('Query Result : "%s"' % queryString)
    return render_template('index.html')

@app.route('/table_test/<sqlQuery>', methods=['GET', 'POST'])
def displayTable(sqlQuery):
    queryCursor = getQueryCursor("select " + sqlQuery + " from team where Name = 'Christian Yelich'")
    attributeNames = getAttributeDescriptions(queryCursor)
    tuples = queryCursor.fetchall()
    #form = SQLForm()
    if form.validate_on_submit():
        flash('Query Entered "%s"' %
        (form.SQL.data))
        return redirect(url_for('displayTable', sqlQuery = form.SQL.data))
    return render_template('sql.html',
                            title = 'SQL',
                            #form = form,
                            attributeNames = attributeNames,
                            tuples = tuples
                            )

#app.add_url_rule('/table_test', displayTable)

@app.route('/sql_submission', methods=['GET', 'POST'])
def queryDB():
    form = IDForm()
    if form.validate_on_submit():
        queryCursor = getQueryCursor("select " + request.form['teammemberID'] + " from teammember where Name = 'Christian Yelich'")
        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        return redirect(url_for('sql.html', teammemberID = form.teammemberID.data, attributeNames = attributeNames,
                            tuples = tuples))
    return render_template('attribute.html', form = form
                            )

@app.route('/sql_test', methods=['GET', 'POST'])
def queryDB2():
    form = IDForm()
    if form.validate_on_submit():
        flash('Query Entered "%s"' %
        (form.teammemberID.data))
        team = form.teammemberID.data
        queryCursor = getQueryCursor("select '" + team + "' from teammember where Name = 'Christian Yelich'")        
        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        #team = form.SQL.data
        return redirect(url_for('queryDB2', teammemberID = form.SQL.data))
    return render_template('sql.html',
                            title = 'SQL',
                            form = form,
                            attributeNames = attributeNames,
                            tuples = tuples
                            )

@app.route('/team_test/<teammemberID>', methods=['GET', 'POST'])
def displayTables(teammemberID):
    #query = "select '" + teammemberID + "' from teammember where Name = 'Christian Yelich'"
    #queryCursor = getQueryCursor(query)
    #attributeNames = getAttributeDescriptions(queryCursor)
    #tuples = queryCursor.fetchall()
    form = SQLForm()
    team = form.SQL.data
    queryCursor = getQueryCursor("select '" + team + "' from teammember where Name = 'Christian Yelich'")
    attributeNames = getAttributeDescriptions(queryCursor)
    tuples = queryCursor.fetchall()
    if form.validate_on_submit():
        flash('Query Entered "%s"' %
        (form.SQL.data))
        #team = form.SQL.data
        return redirect(url_for('displayTables', teammemberID = form.SQL.data))
    return render_template('sql.html',
                            title = 'SQL',
                            form = form,
                            attributeNames = attributeNames,
                            tuples = tuples
                            )



@app.route('/hubPage', methods=['GET', 'POST'])
def hubPage():
    return render_template('DBHomePage.html'
                            )

@app.route('/batting', methods=['GET', 'POST'])
def batting():
    return render_template('BattingPage.html'
                            )

@app.route('/batting/battingQ', methods=['GET', 'POST'])
def battingQ():
    if request.method == 'POST':
        pName = request.form.get('pName')
        year = request.form.get('year')
        team = request.form.get('team')
        battingAvg = request.form.get('BAvg');
        HR = request.form.get('HR');
        hits = request.form.get('hits');
        rbi = request.form.get('rbi');
        steals = request.form.get('steals');
        
        stolenBasesRatio = None;

        queryCursor = getQueryCursor(battingQueryGenerator(pName, year, team, battingAvg,
                            hits, HR,rbi, steals, stolenBasesRatio))
        
        #queryCursor = getQueryCursor("select * from teammember where name = '" + pName + "'")
        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        return render_template('BattingPage.html', attributeNames = attributeNames,
                            tuples = tuples
                            )

@app.route('/pitching', methods=['GET', 'POST'])
def pitching():
    return render_template('PitchingPage.html'
                            ) 


@app.route('/pitching/pitchingQ', methods=['GET', 'POST'])
def pitchingQ():
    if request.method == 'POST':

        pName = request.form.get('pName')
        year = request.form.get('year')
        team = request.form.get('team')
        games = request.form.get('games');
        gamesStarted = request.form.get('gameSt');
        wins = request.form.get('wins');
        losses = request.form.get('loss');
        completedGames = request.form.get('completegames');
        saves = request.form.get('saves');
        hitsAllowed = request.form.get('hitsAllowed');
        strikeOuts = request.form.get('strikeouts');
        era = request.form.get('earnedRunAvg');
        walks = request.form.get('walks'); 

        queryCursor = getQueryCursor(pitchingQueryGenerator(pName, year, team, games, gamesStarted, wins,
                            losses, completedGames, saves, hitsAllowed, strikeOuts,
                            era, walks))

        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        return render_template('PitchingPage.html', attributeNames = attributeNames,
                            tuples = tuples
                            )

@app.route('/fielding', methods=['GET', 'POST'])
def fielding():
    return render_template('FieldingPage.html'
                            )

@app.route('/fielding/fieldingQ', methods=['GET', 'POST'])
def fieldingQ():
    if request.method == 'POST':

        pName = request.form.get('pName')
        year = request.form.get('year')
        team = request.form.get('team')
        positions = request.form.get('positions');
        games = request.form.get('games');
        assists = request.form.get('assists');
        caughtStealing = request.form.get('caughtSteal');
        stolenBA = request.form.get('stolenBA');
        doublePC = request.form.get('doublePC');
        wildP = request.form.get('wildP'); 

        queryCursor = getQueryCursor(fieldingQueryGenerator(pName, year, team, positions, games, assists,
                            caughtStealing, stolenBA, doublePC, wildP))

        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        return render_template('FieldingPage.html', attributeNames = attributeNames,
                            tuples = tuples
                            )

@app.route('/', methods=['GET', 'POST'])
@app.route('/search', methods=['GET', 'POST'])
def search():
    form = SearchForm()
    if form.validate_on_submit():
        flash('Position Entered "%s"' %
                (form.position.data))
        return redirect(url_for('index', position = form.position.data))
    return render_template('search.html',
                            title='Search',
                            form=form)