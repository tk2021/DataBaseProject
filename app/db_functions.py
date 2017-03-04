import pyodbc


def db_test():
	cnxn = pyodbc.connect('DRIVER={Oracle in instantclient_12_1};SERVER=//oracle.cise.ufl.edu:1521/orcl;UID=tk1;PWD=canada55')

	SQL = "select * from TEAMMEMBER where playerID = 'adamste01';"

	cursor = cnxn.cursor()
	cursor.execute(SQL);

	data = cursor.fetchall();

	for i in data:
		print (data)
	cursor.close()
	cnxn.close()
	del cursor
	del cnxn