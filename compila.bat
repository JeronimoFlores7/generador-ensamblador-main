@Echo off
cls
del %1.sal 2> null
java AnaLex %1
if errorlevel 1 goto Fallo
java SLR1 %1
:Fallo