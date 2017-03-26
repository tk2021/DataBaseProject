from flask import render_template, flash, redirect, url_for
from app import app
from app.forms import SearchForm, SQLForm
from app import db_functions
from .db_functions import db_test
from.tupleObject import tupleObject
from app import cx_oracle_test
from .cx_oracle_test import cx_oracle_test, getQueryCursor, getAttributeDescriptions
import cx_Oracle

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

@app.route('/table_test/<sqlQuery>')
def displayTable(sqlQuery):
	return render_template('base.html')

#app.add_url_rule('/table_test', displayTable)

@app.route('/sql_submission', methods=['GET', 'POST'])
def queryDB():
	queryCursor = getQueryCursor("select * from team")
	attributeNames = getAttributeDescriptions(queryCursor)
	tuples = queryCursor.fetchall()
	form = SQLForm()
	if form.validate_on_submit():
		flash('Query Entered "%s"' %
		(form.SQL.data))
		return redirect(url_for('displayTable', sqlQuery = form.SQL.data))
	return render_template('sql.html',
							title = 'SQL',
							form = form,
							attributeNames = attributeNames,
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
