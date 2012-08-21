call setenv.bat
call ant clean
call ant deploy.qa
%EXTERNAL_HOME%\tools\GAE1.5\bin\appcfg.cmd --email=admin@xavierjaipur.org --passin update %IPATSHALA_DEV_HOME%\deploy\qa\war