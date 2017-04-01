import pyodbc


def db_test():
	#cnxn = pyodbc.connect('Driver={Microsoft ODBC for Oracle};Server=//oracle.cise.ufl.edu:1521/orcl;Uid=tk1;Pwd=canada55')

	cnxn = pyodbc.connect('''  Driver={Microsoft ODBC for Oracle};(DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = oracle.cise.ufl.edu)(PORT = 1521))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = orcl)
    )
  )''')


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