from flask import render_template, flash, redirect, url_for
from app import app
from .forms import SearchForm
from app import db_functions
from .db_functions import db_test

@app.route('/index')
def index(position):
		print(position)
		return render_template('index.html')

@app.route('/db_test')
def test():
	db_test()
	return

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