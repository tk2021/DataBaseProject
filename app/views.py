from flask import render_template, flash, redirect, url_for
from app import app
from .forms import SearchForm
from app import db_functions
from .db_functions import db_test
from app import cx_oracle_test
from .cx_oracle_test import cx_oracle_test
import cx_Oracle

@app.route('/index')
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

@app.route('/', methods=['GET', 'POST'])
@app.route('/search', methods=['GET', 'POST'])
def search():
	form = SearchForm()
	if form.validate_on_submit():
		flash('Position Entered "%s"' %
				(form.position.data))
		return redirect(url_for(index, position = form.position.data))
	return render_template('search.html',
							title='Search',
							form=form)
