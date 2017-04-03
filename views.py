from flask import render_template, flash, redirect, url_for
from app import app
from app.forms import SearchForm, SQLForm, IDForm, Fielding
from app import db_functions
from .db_functions import db_test
from.tupleObject import tupleObject
from app import cx_oracle_test
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

@app.route('/form_test', methods=['GET', 'POST'])
def form_test():
    form = Fielding()
    if form.validate_on_submit():
        return redirect(url_for('form_tester', form = form.pName.data))
    return render_template('FieldTester.html', form = form
                            )

@app.route('/formT', methods=['GET', 'POST'])
def formT():
    return render_template('BattingPage.html'
                            )

@app.route('/formT2', methods=['GET', 'POST'])
def formT2():
    if request.method == 'POST':
        pName = request.form.get('pName')
        year = request.form.get('year')
        team = request.form.get('team')
        print (team)
        queryCursor = getQueryCursor("select * from teammember where name = '" + pName + "'")
        attributeNames = getAttributeDescriptions(queryCursor)
        tuples = queryCursor.fetchall()
        return render_template('BattingPage.html', attributeNames = attributeNames,
                            tuples = tuples
                            )

@app.route('/form_tester/<form>', methods=['GET', 'POST'])
def form_tester(form):
    queryCursor = getQueryCursor("select * from teammember where Name = '" + form + "'")
    attributeNames = getAttributeDescriptions(queryCursor)
    tuples = queryCursor.fetchall()
    return render_template('FieldTest2.html', attributeNames = attributeNames,
                            tuples = tuples
                            )
#sqlExample.html


@app.route('/fielding_test', methods=['GET', 'POST'])
def fielding_test():
    return render_template('FieldTester.html'
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