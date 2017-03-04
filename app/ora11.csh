setenv ORACLE_BASE /usr/local/libexec/oracle11g-client
setenv ORACLE_HOME $ORACLE_BASE

setenv UNAME `which uname`
setenv MACHTYPE `$UNAME -m`

if ($MACHTYPE == 'x86_64') then
  if ($?LD_LIBRARY_PATH) then
    setenv LD_LIBRARY_PATH $ORACLE_BASE/lib:$ORACLE_BASE/lib32:$LD_LIBRARY_PATH
  else
    setenv LD_LIBRARY_PATH $ORACLE_BASE/lib:$ORACLE_BASE/lib32
  endif
  setenv PATH ${ORACLE_HOME}/bin:${ORACLE_HOME}/bin32:${PATH}
else
  if ($?LD_LIBRARY_PATH) then
    setenv LD_LIBRARY_PATH $ORACLE_BASE/lib32:$ORACLE_BASE/lib:$LD_LIBRARY_PATH
  else
    setenv LD_LIBRARY_PATH $ORACLE_BASE/lib32:$ORACLE_BASE/lib
  endif
  setenv PATH ${ORACLE_HOME}/bin32:${ORACLE_HOME}/bin${PATH}
endif

