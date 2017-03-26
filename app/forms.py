from flask_wtf import Form 
from wtforms import StringField, BooleanField
from wtforms.validators import DataRequired, ValidationError

def positionValidation(form, field):
	message = 'Position not found!'
	isValid = False
	positions = ["Catcher", "Pitcher", "First Base", "Second Base", "Third Base", "Shortstop", "Left Field", "Right Field", "Center Field", "Designated Hitter"]
	for i in range(0, len(positions)):
		if field.data == positions[i]:
			isValid = True
	if not isValid:
		raise ValidationError(message)

class SearchForm(Form):
	position = StringField('position', [positionValidation, DataRequired()])

class SQLForm(Form):
	SQL = StringField('SQL', [DataRequired()])