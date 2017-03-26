import pyodbc
import cx_Oracle


def cx_oracle_test():

	host = 'oracle.cise.ufl.edu'
	port = 1521
	sid = 'orcl'
	dsn_tns = cx_Oracle.makedsn(host, port, sid)

	db = cx_Oracle.connect('tk1', 'canada55', dsn_tns)

	playerID = 'adamste01'

	SQL = "select * from TEAMMEMBER where playerID = '" + playerID + "'"

	cursor = db.cursor()

	queryCursor = cursor.execute(SQL)

	data = queryCursor.fetchall()

	for i in data:
		print (data)
	queryCursor.close()
	db.close()



	del queryCursor
	del cursor
	del db

	return data

